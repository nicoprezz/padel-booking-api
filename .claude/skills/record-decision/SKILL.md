---
name: record-decision
description: Registra una decisión técnica como ADR nuevo en docs/adr/, con su número, la plantilla del proyecto y el índice. Úsala cuando el autor acabe de elegir entre varias opciones técnicas (diseño, librería, formato, convención) o pida registrar una decisión o escribir un ADR, también si la nueva sustituye o amplía a una anterior.
---

# Registrar una decisión

La decisión es del autor: Claude ordena lo que el autor ha decidido, señala huecos
(opciones no valoradas, consecuencias que faltan) y redacta, pero no elige ni guarda
nada sin OK. El formato, los estados y la plantilla están en `docs/adr/README.md`.

Si no está claro que la elección merezca un ADR (un detalle que se revierte en minutos),
pregunta al autor antes de empezar.

## Pasos

1. **Número.** Lista `docs/adr/`, toma el `NNNN` más alto y usa el siguiente. Propón un
   título corto en español y un slug en inglés (`NNNN-<slug>.md`). Espera el OK.
2. **Relación con otras.** Revisa el índice de `docs/adr/README.md`: ¿la nueva decisión
   sustituye a una vigente (la anula) o la amplía (la completa sin anularla)? Si dudas,
   pregunta.
3. **Contenido con el autor.** Pregunta, uno a uno si no los ha dado ya:
   - **Contexto:** qué problema había.
   - **Opciones:** cuáles se valoraron. Si falta alguna razonable, menciónala.
   - **Decisión:** cuál eligió.
   - **Por qué:** sus razones (2-3), con sus palabras.
   - **Consecuencias:** qué se gana y qué se pierde. Si falta un inconveniente
     evidente, señálalo.
4. **Borrador.** Enseña el ADR completo con la plantilla (Estado `Aceptada`, Fecha de
   hoy; si sustituye o amplía a otra, una línea `Sustituye a:` o `Amplía a:` con enlace
   relativo). Espera el OK; corrige lo que pida el autor y vuelve a enseñarlo.
5. **Guardar.** Con OK:
   - Crea `docs/adr/NNNN-<slug>.md`.
   - Si sustituye o amplía a otra: cambia **solo el Estado** de la antigua
     (`Sustituida por [ADR-NNNN](...)` o `Aceptada · Ampliada por [ADR-NNNN](...)`);
     su texto no se toca.
   - Añade la fila al índice de `docs/adr/README.md` y actualiza el estado de la antigua
     en el índice.
6. **Citas en el código.** Si sustituye o amplía a otra, busca sus citas
   (`git grep "ADR-NNNN"` en `src/`). Enseña las líneas que deberían apuntar a la nueva
   y cámbialas solo con OK; en esas líneas cambia únicamente la cita.

Nunca hace commit ni push sin OK explícito.
