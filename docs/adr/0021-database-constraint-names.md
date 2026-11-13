# ADR-0021 — Nombres de las restricciones de la base de datos

Estado: Aceptada

Fecha: 2026-11-13

Contexto: si una restricción no tiene nombre, MySQL le pone uno automático (el de la
columna para un `UNIQUE`, `<tabla>_ibfk_<n>` para una clave foránea). Esos nombres no
dicen qué regla ha fallado y cambian según el orden en que se crearon.

Opciones: nombres automáticos de MySQL · nombres explícitos con una convención.

Decisión: todas las restricciones con nombre explícito: `pk_<tabla>`,
`uk_<tabla>_<columna>`, `fk_<tabla>_<tabla_referenciada>` e `idx_<tabla>_<columnas>`.

Por qué: los errores de MySQL dicen qué restricción ha fallado (`uk_courts_name`), lo que
ayuda a depurar y, más adelante, a distinguir errores en el handler. Una migración
posterior puede referirse a la restricción por su nombre (`DROP INDEX`). Todas las
migraciones siguen el mismo patrón.

Consecuencias: cada migración lleva algo más de SQL. En MySQL el nombre de la clave
primaria se ignora (siempre es `PRIMARY`); `pk_<tabla>` se mantiene por coherencia. Los
nombres de MySQL tienen un máximo de 64 caracteres, que limita las tablas y columnas muy
largas.
