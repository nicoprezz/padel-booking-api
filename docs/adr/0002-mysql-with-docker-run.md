# ADR-0002 — MySQL en Docker con `docker run` (Compose más adelante)

Estado: Aceptada

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
