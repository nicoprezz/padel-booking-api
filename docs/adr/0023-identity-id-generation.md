# ADR-0023 — Ids con `IDENTITY` (porqué corregido)

Estado: Aceptada

Sustituye a: [ADR-0007](0007-id-generation-strategy.md)

Fecha: 2026-11-13

Contexto: la ADR-0007 justificaba `IDENTITY` diciendo que, con `AUTO`, Hibernate crea
una tabla `hibernate_sequence`. Eso era así en Hibernate 5; el proyecto usa Spring
Boot 4, con una versión de Hibernate en la que `AUTO` crea una tabla de secuencia por
entidad (`<entidad>_seq`), porque MySQL no tiene secuencias. La decisión sigue siendo
válida, pero su porqué estaba desfasado.

Opciones: `GenerationType.IDENTITY` · `GenerationType.AUTO` · `GenerationType.SEQUENCE`
(MySQL no tiene secuencias, así que Hibernate las simula con una tabla).

Decisión: `IDENTITY` (columna `AUTO_INCREMENT` de MySQL), igual que en la ADR-0007.

Por qué: usa la funcionalidad nativa de MySQL, que la migración V1 ya define
(`id BIGINT NOT NULL AUTO_INCREMENT`). Con `AUTO` o `SEQUENCE` habría que crear en las
migraciones una tabla de secuencia por entidad, porque con `ddl-auto=validate` (ADR-0017)
Hibernate ya no la crea y la app no arrancaría sin ella.

Consecuencias: como en la ADR-0007, Hibernate hace el `INSERT` en cuanto se llama a
`save()` para conocer el id, y no puede agrupar inserciones en lote. Irrelevante a esta
escala.
