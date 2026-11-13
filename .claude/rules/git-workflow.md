# Flujo de Git

## Ramas
- GitFlow simplificado: `main` (versiones publicadas) y `develop` (integración). No hay
  ramas `release/*`.
- No hay ramas `hotfix/*` mientras no haya despliegue; se añadirán cuando exista.
- Una rama por issue, creada desde `develop` actualizada: `feature/<n>-<slug-en-inglés>`.

## Trabajo en un issue
- Al empezar: leerlo con `gh issue view <n>` y trabajar contra sus tareas, su "Fuera de
  alcance" y su "Hecho cuando".
- Antes de abrir el PR: repasar el issue y comprobar que no falta ninguna tarea ni
  criterio. Las casillas del issue las marca el autor, no Claude.

## Commits
- Conventional Commits en inglés, en minúscula, en imperativo y cortos: `feat:`, `fix:`,
  `docs:`, `chore:`, `refactor:`, `test:`.
- Commits pequeños. Claude solo hace commit y push cuando se le pide.
- Nunca `Co-Authored-By` ni ninguna otra línea de atribución en commits o PR.

## Pull requests
- PR a `develop` con `Closes #n` en la descripción.
- El título del PR a `develop` sigue Conventional Commits, porque pasa a ser el mensaje
  del commit en `develop`.
- Fusión: `feature/* → develop` con *squash*; `develop → main` con *merge commit*. Solo
  fusiona el autor, nunca Claude.
- Tras el *squash*, la rama `feature/*` se borra y no se reutiliza (en local hace falta
  `git branch -D`, porque Git no ve sus commits en `develop`).

## Cierre de hito
- PR `develop → main` y etiqueta anotada `vX.Y.Z` sobre el commit de `main`.
