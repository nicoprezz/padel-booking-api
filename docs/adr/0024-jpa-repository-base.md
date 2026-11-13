# ADR-0024 — `JpaRepository` como interfaz base (porqué corregido)

Estado: Aceptada

Sustituye a: [ADR-0008](0008-repository-base-interface.md)

Fecha: 2026-11-13

Contexto: la ADR-0008 eligió `JpaRepository` porque incluye paginación y ordenación y
porque `findAll()` devuelve `List`. Desde Spring Data 3 eso ya no la distingue: la
paginación está en `PagingAndSortingRepository` y `ListCrudRepository` también devuelve
`List`. La decisión sigue siendo válida, pero su porqué estaba desfasado.

Opciones: `CrudRepository` · `ListCrudRepository` (+ `ListPagingAndSortingRepository`) ·
`JpaRepository`.

Decisión: `JpaRepository`, igual que en la ADR-0008.

Por qué: reúne en una sola interfaz lo que darían las otras dos (`List`, paginación y
ordenación) y añade los métodos propios de JPA: `flush()`, `saveAndFlush()`,
`getReferenceById()` y los borrados en lote. Es la interfaz habitual en proyectos Spring
Boot con JPA.

Consecuencias: como en la ADR-0008, el repositorio queda acoplado a JPA (no se podría
cambiar a otro almacén sin tocarlo); asumible porque el proyecto es JPA + MySQL.
