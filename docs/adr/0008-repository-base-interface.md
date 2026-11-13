# ADR-0008 — Interfaz base de los repositorios

Estado: Aceptada

Fecha: 2026-11-10

Contexto: Spring Data ofrece varias interfaces de las que heredar.

Opciones: `CrudRepository` · `JpaRepository`

Decisión: `JpaRepository`.

Por qué: incluye paginación y ordenación (las necesitaré si el proyecto escala) y
`findAll()` devuelve `List` en lugar de `Iterable`, que es más cómodo en los servicios.

Consecuencias: acopla el repositorio a JPA (no podría cambiar a otro almacén sin tocarlo);
asumible porque el proyecto es JPA + MySQL.
