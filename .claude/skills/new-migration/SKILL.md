---
name: new-migration
description: Guía para cambiar el esquema de la base de datos con una migración nueva de Flyway y ajustar la entidad JPA. Úsala cuando haya que crear una tabla, añadir, cambiar o quitar una columna o una restricción, o cuando falle la validación del esquema al arrancar.
---

# Nueva migración

Modo "guíame y revisa": el autor escribe el SQL y la entidad; Claude los revisa contra
`.claude/rules/database.md`, propone correcciones y espera el OK antes de aplicarlas.

## Pasos

1. **Número de versión.** Lista `src/main/resources/db/migration/`, toma el `V<n>` más
   alto y propone `V<n+1>` con un nombre según `database.md`. Espera el OK del nombre.
2. **SQL.** Explica qué tiene que contener la migración (tablas, columnas, tipos,
   restricciones) y qué decisiones hay que tomar. Si hay varias opciones razonables,
   preséntalas con pros y contras, sin elegir. Espera a que el autor escriba el SQL.
3. **Revisión del SQL.** Revísalo contra `database.md`. Si algo no encaja, explica el
   porqué y propone la corrección; aplícala solo con OK.
4. **Entidad.** Explica qué cambia en la entidad para que encaje con la tabla (`@Column`,
   `@Enumerated`, relaciones). Espera a que el autor la escriba, revísala igual que el SQL
   y lanza `./mvnw compile`.
5. **Validación.** Pide al autor que arranque la app (`./mvnw spring-boot:run`) y que
   pase el resultado. Explica que Flyway aplica la migración y después Hibernate valida
   el esquema contra las entidades.
6. **Si falla.** Lee el error con el autor y localiza el desajuste. Se corrige la
   entidad o se crea otra migración (vuelve al paso 1); la migración ya aplicada nunca
   se edita.

Nunca hace commit ni push sin OK explícito.
