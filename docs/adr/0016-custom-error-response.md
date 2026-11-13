# ADR-0016 — Formato del JSON de error: `record ErrorResponse` propio

Estado: Aceptada

Fecha: 2026-11-10

Contexto: las excepciones (`NotFoundException`, `ConflictException`, fallos de `@Valid`,
JSON mal formado) acaban en un 500 genérico; hay que traducirlas en un
`@RestControllerAdvice` central y decidir qué JSON recibe el cliente.

Opciones: `record ErrorResponse` propio (`{status, error, message}`) · `ProblemDetail` de
Spring (estándar RFC 9457: `{type, title, status, detail, instance}`)

Decisión: `record ErrorResponse` propio.

Por qué: cada campo se elige y se ve en el código, sin comportamiento implícito del
framework; para aprender cómo funciona el advice es lo más transparente.

Consecuencias: el formato no es un estándar, así que los clientes tienen que conocerlo.
Se valorará pasar a `ProblemDetail` más adelante; el cambio queda limitado al handler
(los `@ExceptionHandler` siguen igual, solo cambia el objeto devuelto).
