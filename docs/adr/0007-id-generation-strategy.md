# ADR-0007 — Estrategia de generación de ids

Estado: Sustituida por [ADR-0023](0023-identity-id-generation.md)

Fecha: 2026-11-10

Contexto: las entidades necesitan un id generado automáticamente.

Opciones: `GenerationType.IDENTITY` · `GenerationType.AUTO`

Decisión: `IDENTITY` (columna `AUTO_INCREMENT` de MySQL).

Por qué: aprovecha una funcionalidad nativa de MySQL; con `AUTO`, Hibernate puede crear
una tabla `hibernate_sequence` extra que no necesito.

Consecuencias: Hibernate tiene que hacer el `INSERT` en cuanto se llama a `save()` para
conocer el id (no puede agrupar inserciones en lote). Irrelevante a esta escala.
