---
paths:
  - "src/main/resources/db/migration/**"
  - "src/main/java/**/model/**"
  - "src/main/java/**/repository/**"
---

# Base de datos

## Migraciones
- Todo cambio de esquema va en una migración nueva de Flyway:
  `V<n>__<descripcion_en_snake_case>.sql`.
- Una migración ya aplicada no se modifica nunca, porque Flyway guarda su checksum y la
  app dejaría de arrancar; si algo está mal, se corrige con otra migración.
- Hibernate no crea tablas (`ddl-auto=validate`): si la entidad y la tabla no encajan,
  la app no arranca.

## Nombres
- Tablas en plural y `snake_case`: `courts`, `bookings`.
- Restricciones siempre con nombre explícito, para que los errores de MySQL digan cuál
  ha fallado:
  - `pk_<tabla>`
  - `uk_<tabla>_<columna>`
  - `fk_<tabla>_<tabla_referenciada>`
  - `idx_<tabla>_<columnas>`

## Entidades
- Las anotaciones de `@Column` (`nullable`, `unique`, `length`) reflejan la migración,
  aunque `validate` no las compruebe.
- Los enums se guardan como texto (`@Enumerated(EnumType.STRING)`), para que reordenar
  el enum no cambie el significado de los datos.
- La unicidad se garantiza en la base de datos (restricción `UNIQUE`), no solo con una
  comprobación en el service: dos peticiones simultáneas pueden pasar las dos la
  comprobación.
