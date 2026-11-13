# ADR-0025 — Nombres duplicados en peticiones simultáneas

Estado: Aceptada

Amplía a: [ADR-0009](0009-duplicate-court-name-conflict.md)

Fecha: 2026-11-13

Contexto: la ADR-0009 decidió que un nombre de pista repetido responde 409, y en sus
consecuencias añadió cómo se garantiza cuando llegan dos peticiones a la vez. Es una
decisión distinta (cómo se garantiza la unicidad, no qué código se devuelve) y se separa
aquí. Con dos peticiones simultáneas con el mismo nombre, las dos pasan la comprobación
previa del service (`existsByName` / `existsByNameAndIdNot`).

Opciones: solo la comprobación previa en el service · solo la restricción `UNIQUE` de la
tabla con un handler · las dos · bloqueos o transacciones serializables.

Decisión: las dos. La comprobación previa da un mensaje claro en el caso normal; la
restricción `UNIQUE` (`uk_courts_name`) es la garantía real, y un handler de
`DataIntegrityViolationException` la traduce a 409 con un mensaje genérico, sin
`ex.getMessage()`.

Por qué: solo la base de datos ve las dos peticiones a la vez, así que es el único sitio
que puede frenar la segunda. `ex.getMessage()` contiene la sentencia SQL y los nombres de
columnas y restricciones, que revelan el esquema. Los bloqueos resolverían lo mismo con
más complejidad y peor rendimiento.

Consecuencias: el handler responde igual a cualquier violación de integridad (`UNIQUE`,
clave foránea, `NOT NULL`), con un mensaje poco preciso. Cuando haya claves foráneas
(hito 2), se puede distinguir el caso por el nombre de la restricción (ADR-0021). La
unicidad depende de que la migración tenga el `UNIQUE`.
