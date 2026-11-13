# ADR-0003 — Group y paquete Java

Estado: Aceptada

Fecha: 2026-11-05

Contexto: Maven y Java piden un nombre de paquete único, normalmente un dominio al revés.
No tengo dominio propio.

Opciones: `dev.<nombre>` (exige tener ese dominio) · `io.github.<usuario>` · `com.example`

Decisión: Group `io.github.nicoprezz`, paquete `io.github.nicoprezz.padel`.

Por qué: GitHub me da gratis el dominio `nicoprezz.github.io`, así que el nombre es mío
y es único de verdad; `com.example` queda poco profesional.

Consecuencias: si cambio de usuario de GitHub, el paquete quedaría desfasado (se puede
renombrar, pero toca todos los archivos).
