# ADR-0006 — Gestión del esquema de la base de datos (`ddl-auto`)

Estado: Sustituida por [ADR-0017](0017-flyway-with-ddl-validate.md)

Fecha: 2026-11-05

Contexto: Hibernate puede crear/modificar las tablas a partir de las entidades al arrancar.

Opciones: `none` · `validate` (+ `schema.sql` o Flyway) · `update` · `create` · `create-drop`

Decisión: `update`, con `spring.jpa.show-sql=true` en el perfil local para ver el SQL
que genera.

Por qué: al principio las entidades cambiarán mucho; `update` no borra datos de prueba;
con `show-sql` veo el SQL real que se ejecuta.

Consecuencias: no borra columnas ni tablas viejas ni modifica columnas existentes (al
cambiar una restricción hay que borrar la tabla a mano); no sirve para producción.

Revisar más adelante: pasar a `validate` + Flyway.

Sustituida por #017.
