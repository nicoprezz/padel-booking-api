# Decisiones técnicas

Registro de las decisiones de diseño e implementación del proyecto: qué opciones había,
cuál se eligió y por qué.

## Plantilla
```
## NNN — Título corto
Fecha:
Contexto: qué problema había
Opciones: A, B, (C)
Decisión: cuál se eligió
Por qué: 2-3 razones
Consecuencias: qué se gana y qué se pierde
```

---

## 001 — Arquitectura por capas
Fecha: nov 2026
Contexto: necesito una estructura para la API que pueda entender y explicar.
Opciones: por capas (controller/service/repository) · por funcionalidad (court/, booking/)
Decisión: por capas.
Por qué: es la más común en ofertas de Spring Boot y la más fácil de aprender primero.
Consecuencias: si el proyecto crece mucho, los paquetes se llenan; valorar migrar a
paquetes por funcionalidad más adelante.

## 002 — MySQL en Docker con `docker run` (Compose más adelante)
Fecha: 2026-11-05
Contexto: la app debe arrancar conectada a MySQL desde el principio, y no quiero instalar
MySQL directamente en Windows.
Opciones: MySQL instalado en Windows · contenedor con `docker run` · `docker-compose.yml`
Decisión: `docker run` con la imagen `mysql:8.4`, un volumen con nombre (`padel-data`) y
un usuario de aplicación (`padel`) distinto de `root`.
Por qué: no ensucia el sistema y se borra sin dejar rastro; escribir el comando a mano
me obliga a entender cada pieza (imagen, variables de entorno, puerto, volumen); Compose
tiene más sentido cuando haya dos servicios (app + BD).
Consecuencias: el comando es largo y hay que recordarlo (`docker start padel-mysql` para
los siguientes arranques); más adelante lo traduciré a `docker-compose.yml`.

## 003 — Group y paquete Java
Fecha: 2026-11-05
Contexto: Maven y Java piden un nombre de paquete único, normalmente un dominio al revés.
No tengo dominio propio.
Opciones: `dev.<nombre>` (exige tener ese dominio) · `io.github.<usuario>` · `com.example`
Decisión: Group `io.github.nicoprezz`, paquete `io.github.nicoprezz.padel`.
Por qué: GitHub me da gratis el dominio `nicoprezz.github.io`, así que el nombre es mío
y es único de verdad; `com.example` queda poco profesional.
Consecuencias: si cambio de usuario de GitHub, el paquete quedaría desfasado (se puede
renombrar, pero toca todos los archivos).

## 004 — Formato de configuración de Spring
Fecha: 2026-11-05
Contexto: Spring Initializr pide elegir el formato de `application.*`.
Opciones: `application.properties` · `application.yaml`
Decisión: Properties.
Por qué: una línea por propiedad, sin errores de sangría; es lo que usan las guías
oficiales de spring.io, así que puedo compararlas directamente.
Consecuencias: con muchas propiedades se repiten prefijos (`spring.datasource.*`);
si crece, puedo migrar a YAML (Spring lee los dos).

## 005 — Dónde va la contraseña de la base de datos
Fecha: 2026-11-05
Contexto: la app necesita la contraseña de MySQL, pero el repo es público.
Opciones: variable de entorno (`${DB_PASSWORD}`) · `application-local.properties` ignorado
Decisión: `application-local.properties` en `src/main/resources`, en `.gitignore`,
cargado con el perfil `local` (`spring.profiles.active=local`). Se versiona una plantilla,
`application-local.properties.example`.
Por qué: se escribe una vez y no hay que definir variables en cada terminal/IDE; nunca
sale de mi máquina.
Consecuencias: quien clone el repo tiene que crear su propio archivo local a partir de la
plantilla; al desplegar habrá que pasar a variables de entorno.

## 006 — Gestión del esquema de la base de datos (`ddl-auto`)
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

## 007 — Estrategia de generación de ids
Fecha: 2026-11-10
Contexto: las entidades necesitan un id generado automáticamente.
Opciones: `GenerationType.IDENTITY` · `GenerationType.AUTO`
Decisión: `IDENTITY` (columna `AUTO_INCREMENT` de MySQL).
Por qué: aprovecha una funcionalidad nativa de MySQL; con `AUTO`, Hibernate puede crear
una tabla `hibernate_sequence` extra que no necesito.
Consecuencias: Hibernate tiene que hacer el `INSERT` en cuanto se llama a `save()` para
conocer el id (no puede agrupar inserciones en lote). Irrelevante a esta escala.

## 008 — Interfaz base de los repositorios
Fecha: 2026-11-10
Contexto: Spring Data ofrece varias interfaces de las que heredar.
Opciones: `CrudRepository` · `JpaRepository`
Decisión: `JpaRepository`.
Por qué: incluye paginación y ordenación (las necesitaré si el proyecto escala) y
`findAll()` devuelve `List` en lugar de `Iterable`, que es más cómodo en los servicios.
Consecuencias: acopla el repositorio a JPA (no podría cambiar a otro almacén sin tocarlo);
asumible porque el proyecto es JPA + MySQL.

## 009 — Nombre de pista duplicado → 409 Conflict
Fecha: 2026-11-10
Contexto: el nombre de una pista es único; hay que decidir qué responde la API si se repite.
Opciones: 400 Bad Request · 409 Conflict
Decisión: 409 Conflict.
Por qué: es el estándar; los datos tienen un formato válido (no es un 400), pero chocan
con el estado actual del servidor.
Consecuencias: el `GlobalExceptionHandler` necesitará una excepción propia para conflictos.
La comprobación previa (`existsByName` / `existsByNameAndIdNot`) no basta: con dos peticiones
simultáneas ambas la pasan. La garantía real es el `UNIQUE` de la tabla más un handler de
`DataIntegrityViolationException` → 409 con mensaje genérico (sin `ex.getMessage()`, que
contiene SQL). La comprobación previa se mantiene para dar un mensaje claro en el caso normal.

## 010 — Edición de pistas con PUT (sin PATCH)
Fecha: 2026-11-10
Contexto: hay que elegir cómo se edita una pista.
Opciones: `PUT` (reemplazo completo) · `PATCH` (actualización parcial) · ambos
Decisión: solo `PUT`; el cliente envía todos los campos.
Por qué: reutiliza el mismo DTO y las mismas validaciones que el `POST`; es idempotente;
`PATCH` obliga a distinguir "campo ausente" de "campo null", y es complejidad que no hace falta.
Consecuencias: para cambiar un campo hay que reenviar la pista entera. Si se necesita,
se puede añadir `PATCH` más adelante.

## 011 — Sin borrado de pistas (se desactivan)
Fecha: 2026-11-10
Contexto: `DELETE /api/courts/{id}` podía borrar físicamente o desactivar.
Opciones: borrado físico · `DELETE` que desactiva (soft delete) · sin `DELETE`, desactivar con `PUT`
Decisión: no hay endpoint `DELETE`; una pista se desactiva con `PUT` y `"active": false`.
Por qué: conserva el historial (las reservas apuntarán a la pista); un `DELETE` que no
borra de verdad confundiría a quien use la API; encaja con la regla "una pista inactiva
no admite reservas nuevas".
Consecuencias: las pistas no desaparecen nunca; a revisar en el futuro (p. ej., un
borrado físico solo para pistas sin reservas).

## 012 — `active` obligatorio en `CourtRequest`
Fecha: 2026-11-10
Contexto: si el cliente no envía `active` al crear o editar una pista, hay que decidir qué pasa.
Opciones: `boolean` primitivo (Jackson pone `false` sin avisar) · `Boolean` sin validar y valor
por defecto en el service · `@NotNull Boolean` (obligatorio)
Decisión: `@NotNull Boolean active`; si falta, 400.
Por qué: con `boolean` un PUT que olvide el campo desactivaría la pista sin que nadie lo pida;
ser obligatorio encaja con `PUT` como reemplazo completo (#010) y hace que POST y PUT se
comporten igual.
Consecuencias: el cliente siempre tiene que enviar `active`, también al crear una pista.

## 013 — Conversión DTO ↔ entidad en los propios DTOs
Fecha: 2026-11-10
Contexto: el service necesita convertir `CourtRequest` → `Court` y `Court` → `CourtResponse`.
Opciones: métodos privados en el service · clase `CourtMapper` aparte · métodos en el
propio DTO · librería MapStruct
Decisión: métodos en el DTO (`CourtResponse.from(Court)`, `CourtRequest.toEntity()`).
Por qué: con tres campos es lo más legible y no añade archivos; el service se queda
con las reglas del club. MapStruct se descarta por ser una dependencia que oculta
lo que se quiere aprender.
Consecuencias: los DTOs importan la entidad (la entidad sigue sin conocer la API). La
actualización del PUT se hace con setters en el service, no en el DTO. Si el proyecto
crece (más entidades o conversiones complejas), pasar a una clase mapper.

## 014 — El service recibe y devuelve DTOs
Fecha: 2026-11-10
Contexto: hay que decidir en qué capa se convierte DTO ↔ entidad, es decir, quién llama a
`from()` y `toEntity()`.
Opciones: el service devuelve DTOs (la entidad no sale del service) · el service devuelve
entidades y el controller convierte
Decisión: el service recibe `CourtRequest` y devuelve `CourtResponse`.
Por qué: el controller queda sin lógica (solo recibe, valida y delega) y la entidad JPA
vive en una sola capa, así que no se puede exponer por accidente.
Consecuencias: los métodos del service no se pueden reutilizar desde otro service para
obtener entidades; en el hito 2, `BookingService` usará `CourtRepository` directamente
para conseguir el `Court` de una reserva.

## 015 — POST responde 201 con cabecera `Location`
Fecha: 2026-11-10
Contexto: al crear una pista hay que decidir qué respuesta HTTP recibe el cliente.
Opciones: 200 con la pista · 201 con la pista (`ResponseEntity.status(CREATED)` o
`@ResponseStatus`) · 201 con la pista + cabecera `Location` con la URL del recurso
Decisión: 201 + `Location`, construida con `ServletUriComponentsBuilder.fromCurrentRequest()`.
Por qué: es lo que recomienda HTTP (RFC 9110) para un recurso creado; el cliente sabe
dónde está la pista sin montar la URL a mano. Partir de la petición actual evita repetir
`/api/courts` y da una URL completa que sigue siendo válida al desplegar (hito 5).
Consecuencias: el método `create` devuelve `ResponseEntity` y no el DTO directamente.
Los futuros POST (reservas en el hito 2) seguirán el mismo patrón.

## 016 — Formato del JSON de error: `record ErrorResponse` propio
Fecha: 2026-11-10
Contexto: las excepciones (`NotFoundException`, `ConflictException`, fallos de `@Valid`,
JSON mal formado) acaban en un 500 genérico; hay que traducirlas en un
`@RestControllerAdvice` central y decidir qué JSON recibe el cliente.
Opciones: `record ErrorResponse` propio (`{status, error, message}`) · `ProblemDetail` de
Spring (estándar RFC 9457: `{type, title, status, detail, instance}`)
Decisión: `record ErrorResponse` propio.
Por qué: cada campo se elige y se ve en el código, sin comportamiento implícito del
framework; para aprender cómo funciona el advice es lo más transparente.
Consecuencias: el formato no es un estándar, así que los clientes tienen que conocerlo.
Se valorará pasar a `ProblemDetail` más adelante; el cambio queda limitado al handler
(los `@ExceptionHandler` siguen igual, solo cambia el objeto devuelto).
