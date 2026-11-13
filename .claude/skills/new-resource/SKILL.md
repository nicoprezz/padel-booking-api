---
name: new-resource
description: Guía paso a paso para crear un recurso nuevo de la API de principio a fin (tabla, entidad, repositorio, DTOs, service, controller, errores y peticiones de Bruno). Úsala cuando el autor quiera empezar un recurso o una funcionalidad nueva, p. ej. "empecemos el recurso de reservas".
---

# Nuevo recurso

Modo "guíame y revisa": Claude explica cada paso, el autor escribe el código y Claude lo
revisa contra `.claude/rules/`. Claude no escribe la lógica de negocio; lo repetitivo
(DTOs, boilerplate) solo si el autor lo pide.

## En cada paso

1. Explica qué papel cumple la pieza en la arquitectura y qué decisiones hay que tomar.
2. Si hay varias opciones razonables, preséntalas con pros y contras, sin elegir.
3. Espera a que el autor lo escriba.
4. Revísalo contra las reglas del proyecto; explica el porqué de cada corrección y
   aplícala solo con OK.
5. Pasa al siguiente paso solo cuando el autor lo confirme.

## Pasos

1. **Migración**: usa la skill `new-migration`.
2. **Entidad** (`model/`): `new-migration` ya la deja encajando con la tabla; aquí se
   completa el resto (constructores, getters y setters).
3. **Repositorio** (`repository/`).
4. **DTOs** (`dto/`): request y response.
5. **Service** (`service/`): casos de uso y reglas del club.
6. **Controller** (`controller/`).
7. **Errores**: excepciones nuevas y sus handlers en el `GlobalExceptionHandler`.
8. **Peticiones de Bruno** (`bruno/`).

## Al terminar

1. Lanza `./mvnw compile` y enseña el resultado.
2. Pide al autor que arranque la app (`./mvnw spring-boot:run`) y pruebe las peticiones
   de Bruno; espera a que pase el resultado.

Nunca hace commit ni push sin OK explícito.
