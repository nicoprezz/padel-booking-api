# ADR-0017 — Esquema con Flyway + `ddl-auto=validate` (sustituye a #006)

Estado: Aceptada

Sustituye a: [ADR-0006](0006-ddl-auto-schema-management.md)

Fecha: 2026-11-12

Contexto: con `update` el esquema real puede desviarse de las entidades sin avisar (no
modifica columnas existentes) y no hay historial de cambios. La tabla `courts` ya existía,
creada por Hibernate.

Opciones (migraciones): Flyway · Liquibase · `schema.sql` de Spring.

Opciones (tabla existente): borrarla y que Flyway la cree desde V1 · *baseline*
(`baseline-on-migrate`, marca V1 como aplicada sin ejecutarla).

Decisión: Flyway (`spring-boot-starter-flyway` + `flyway-mysql`) con scripts en
`src/main/resources/db/migration` y `ddl-auto=validate`. La tabla `courts` se borra y la
crea V1.

Por qué: el esquema pasa a ser SQL versionado escrito a mano; `validate` hace que la app no
arranque si las entidades y las tablas no encajan. Borrar la tabla (solo había datos de
prueba) hace que V1 se ejecute de verdad, como pasará al clonar el repo o con Docker.

Consecuencias: cada cambio de esquema necesita un script nuevo (`V2__...sql`); los
aplicados no se tocan (Flyway guarda su checksum y falla si cambian). `validate` comprueba
tablas, columnas y tipos, pero no `NOT NULL` ni `UNIQUE`: esas dependen solo del script.
Los `nullable`/`unique` de los `@Column` se mantienen como documentación (y por si algo
vuelve a generar el esquema desde las entidades, p. ej. en tests); deben coincidir con las
migraciones: al cambiar una restricción en un `V<n>`, se actualiza también la entidad.
