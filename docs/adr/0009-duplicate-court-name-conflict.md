# ADR-0009 — Nombre de pista duplicado → 409 Conflict

Estado: Aceptada

Fecha: 2026-11-10

Contexto: el nombre de una pista es único; hay que decidir qué responde la API si se repite.

Opciones: 400 Bad Request · 409 Conflict

Decisión: 409 Conflict.

Por qué: es el estándar; los datos tienen un formato válido (no es un 400), pero chocan
con el estado actual del servidor.

Consecuencias: el `GlobalExceptionHandler` necesitará una excepción propia para conflictos.
La comprobación previa (`existsByName` / `existsByNameAndIdNot`) no basta: con dos peticiones
simultáneas ambas la pasan. La garantía real es el `UNIQUE` de la tabla más un handler de
`DataIntegrityViolationException` → 409 con mensaje genérico (sin `ex.getMessage()`, que
contiene SQL). La comprobación previa se mantiene para dar un mensaje claro en el caso normal.
