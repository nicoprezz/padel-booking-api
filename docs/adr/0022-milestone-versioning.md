# ADR-0022 — Versionado de los hitos

Estado: Aceptada

Fecha: 2026-11-13

Contexto: cada hito se cierra con un PR `develop → main` y una etiqueta anotada (el hito 1
quedó como `v0.1.0`). Hacía falta un criterio para el número de cada versión.

Opciones: SemVer `0.y.z` según cambie o no la API · una versión menor por hito, sin
mirar qué cambia · versiones por fecha (CalVer).

Decisión: SemVer `0.y.z` hasta el primer despliegue: sube `y` si el hito añade o cambia
endpoints, y `z` si la API no cambia. `1.0.0` al cerrar el primer hito con la app
desplegada; después, SemVer normal.

Por qué: SemVer es el estándar y el número dice algo útil: quien use la API sabe si ha
cambiado. El `0.` indica que la API todavía no es estable, como corresponde antes del
despliegue. El hito 1.5 (solo documentación y configuración) queda como `v0.1.1`.

Consecuencias: los números de versión no coinciden con los de los hitos (el hito 1.5 es
`0.1.1` y el hito 4, solo tests, sería un patch). Hay que decidir en cada cierre si el
hito cambia la API. La skill `close-milestone` propone la versión con este criterio.
