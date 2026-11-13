# ADR-0018 — Estrategia de ramas: GitFlow simplificado

Estado: Aceptada

Fecha: 2026-11-13

Contexto: se trabajaba directamente en `main`, sin ramas, y se quería practicar un flujo
de trabajo profesional. La estrategia ya se aplicaba desde el PR #6 (recogida en
`.claude/rules/git-workflow.md`); este ADR registra su porqué.

Opciones: GitHub Flow (`main` + `feature/*`) · GitFlow completo (`main`, `develop`,
`feature/*`, `release/*`, `hotfix/*`) · GitFlow simplificado (`main`, `develop`,
`feature/*`).

Decisión: GitFlow simplificado. `main` guarda las versiones publicadas y `develop` la
integración; una rama `feature/<n>-<slug>` por issue, que se fusiona en `develop` con
*squash*. Al cerrar un hito, `develop → main` con *merge commit* y etiqueta anotada
`vX.Y.Z`. Sin ramas `release/*` ni `hotfix/*`.

Por qué: `develop` separa lo integrado de las versiones publicadas: `main` solo cambia al
cerrar un hito, con su etiqueta. Sin `release/*` porque se trabaja en solitario y no hace
falta estabilizar una versión mientras otros integran. Sin `hotfix/*` porque todavía no
hay nada desplegado que arreglar con urgencia.

Consecuencias: `main` es siempre una versión cerrada y `develop` tiene un commit por
issue. Se pierde agilidad: cada issue necesita rama y PR, y cada hito un PR más y su
etiqueta. Con *squash* los commits intermedios no quedan en `develop`, aunque siguen en el
PR. Las ramas locales hay que borrarlas con `git branch -D`. En el hito 5, con el
despliegue, habrá que añadir `hotfix/*`.
