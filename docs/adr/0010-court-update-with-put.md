# ADR-0010 — Edición de pistas con PUT (sin PATCH)

Estado: Aceptada

Fecha: 2026-11-10

Contexto: hay que elegir cómo se edita una pista.

Opciones: `PUT` (reemplazo completo) · `PATCH` (actualización parcial) · ambos

Decisión: solo `PUT`; el cliente envía todos los campos.

Por qué: reutiliza el mismo DTO y las mismas validaciones que el `POST`; es idempotente;
`PATCH` obliga a distinguir "campo ausente" de "campo null", y es complejidad que no hace falta.

Consecuencias: para cambiar un campo hay que reenviar la pista entera. Si se necesita,
se puede añadir `PATCH` más adelante.
