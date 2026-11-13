# ADR-0005 — Dónde va la contraseña de la base de datos

Estado: Aceptada

Fecha: 2026-11-05

Contexto: la app necesita la contraseña de MySQL, pero el repo es público.

Opciones: variable de entorno (`${DB_PASSWORD}`) · `application-local.properties` ignorado

Decisión: `application-local.properties` en `src/main/resources`, en `.gitignore`,
cargado con el perfil `local` (`spring.profiles.active=local`). Se versiona una plantilla,
`application-local.properties.example`.

Por qué: se escribe una vez y no hay que definir variables en cada terminal/IDE; nunca
sale de mi máquina.

Consecuencias: quien clone el repo tiene que crear su propio archivo local a partir de la
plantilla; al desplegar habrá que pasar a variables de entorno. Al estar en
`src/main/resources`, Maven copia el archivo dentro del `.jar` (con la contraseña): se
acepta mientras el `.jar` no salga de mi máquina; se revisa en el hito 5 (Docker Compose),
con la contraseña en variables de entorno.
