# ADR-0020 — Registro de decisiones en formato ADR

Estado: Aceptada

Fecha: 2026-11-13

Contexto: las decisiones estaban en un único archivo con 17 secciones. Ninguna tenía un
estado propio (que la 006 estaba sustituida por la 017 solo se leía en el texto) y las
referencias desde el código apuntaban a un número dentro de ese archivo.

Opciones (formato): seguir con un archivo único · un ADR por decisión con el formato de
Nygard (Estado, Contexto, Decisión, Consecuencias) · un ADR por decisión con ese formato
ampliado con Fecha, Opciones y Por qué.
Opciones (organización): carpeta plana numerada · subcarpetas por área · campo de área
en cada ADR.

Decisión: un ADR por decisión en `docs/adr/NNNN-<slug-en-inglés>.md`, con el formato de
Nygard ampliado y tres estados (`Aceptada`, `Aceptada · Ampliada por`, `Sustituida
por`). Carpeta plana con numeración global, conservando los números anteriores, e índice
en `docs/adr/README.md`. En el código se cita como `ADR-NNNN`.

Por qué: cada decisión tiene su estado y sus enlaces, así que una cita antigua nunca se
rompe: lleva a la decisión y, si está sustituida, a la vigente. Las Opciones y el Por qué
conservan las alternativas descartadas, que es lo que más se olvida. La carpeta plana es
lo habitual con un solo componente, y la numeración global mantiene el orden en que se
tomaron las decisiones.

Consecuencias: hay un archivo por decisión y un índice que mantener (lo hace la skill
`record-decision`). Las decisiones no se reescriben: las correcciones van como ADR nuevos
(ADR-0023 a ADR-0025). Las ADR 0001–0017 conservan en su texto las referencias `#NNN`
del registro anterior; su historial sigue en Git (lo indica el commit de la migración).
