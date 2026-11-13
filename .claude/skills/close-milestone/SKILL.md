---
name: close-milestone
description: Cierra el hito actual del proyecto: comprobaciones, PR de develop a main y etiqueta de versión. Solo se lanza a mano con /close-milestone.
disable-model-invocation: true
---

# Cerrar el hito

Cada paso espera la confirmación del autor antes de pasar al siguiente.

1. **Issues.** Pide al autor que confirme en GitHub que todos los issues del milestone
   están cerrados.
2. **"Hecho cuando…".** Lee la descripción del milestone en GitHub y repasa con el autor
   cada punto, uno a uno.
3. **Tests.** Lanza `./mvnw test` y enseña el resultado. Si algo falla, se para aquí.
4. **Versión.** Busca la última etiqueta con `git describe --tags --abbrev=0` y propón la
   siguiente según este criterio:
   - Antes de `1.0.0`: sube la segunda cifra (`0.1.0 → 0.2.0`) si el hito añade o
     cambia endpoints, y la tercera (`0.2.0 → 0.2.1`) si no cambia la API.
   - `1.0.0` al cerrar el primer hito con la app desplegada; después, SemVer normal.

   El autor la confirma o la cambia.
5. **PR `develop → main`.** Indica el comando para abrirlo, con el título
   `Release vX.Y.Z – <hito>`. Lo fusiona el autor, con *merge commit*.
6. **Etiqueta.** Tras la fusión, da los comandos para crear la etiqueta anotada sobre
   `main` actualizada:
   ```
   git checkout main
   git pull
   git tag -a vX.Y.Z -m "<hito>"
   git push origin vX.Y.Z
   ```
   El push de la etiqueta solo se hace con OK explícito.
7. **`CLAUDE.md`.** Actualiza "Hito actual" con el siguiente hito y enseña el cambio.

Nunca fusiona, hace commit ni push sin OK explícito.
