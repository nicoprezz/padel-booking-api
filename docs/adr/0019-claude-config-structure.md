# ADR-0019 — Estructura de la configuración de Claude Code

Estado: Aceptada

Fecha: 2026-11-13

Contexto: toda la configuración de Claude Code estaba en un único `CLAUDE.md` (más un
archivo personal importado desde él): normas del proyecto, preferencias personales y
flujos de sesión. Se cargaba entero en cada sesión y mezclaba lo que vale para
cualquiera que clone el repositorio con lo que solo vale para el autor.

Opciones: un único `CLAUDE.md` · `CLAUDE.md` con imports (`@archivo`) de otros archivos ·
`CLAUDE.md` central + `.claude/rules/` + `.claude/skills/` + `CLAUDE.local.md`.

Decisión: `CLAUDE.md` central (resumen, comandos, mapa e hito actual) + `.claude/rules/`
(normas; unas siempre cargadas y otras solo al tocar sus carpetas, con `paths:`) +
`.claude/skills/` (pasos de tareas que se repiten) + `CLAUDE.local.md` para lo personal,
ignorado por Git.

Por qué: las reglas con `paths:` y las skills solo entran en contexto cuando hacen falta,
así que cada sesión carga menos. Cada pieza tiene un único sitio: las normas en las
reglas y los pasos en las skills, sin repetirse. Lo personal sale del repositorio, y
quien lo clone recibe solo las normas del proyecto.

Consecuencias: hay más archivos que mantener y hay que vigilar que una norma no se
repita en dos sitios. Lo personal no está versionado: si se pierde el equipo, se pierde.
Claude Code solo encuentra las skills en `.claude/skills/<nombre>/SKILL.md` (no en
subcarpetas), así que las skills personales se ignoran una a una en el `.gitignore`.
