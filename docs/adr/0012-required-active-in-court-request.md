# ADR-0012 — `active` obligatorio en `CourtRequest`

Estado: Aceptada

Fecha: 2026-11-10

Contexto: si el cliente no envía `active` al crear o editar una pista, hay que decidir qué pasa.

Opciones: `boolean` primitivo (Jackson pone `false` sin avisar) · `Boolean` sin validar y valor
por defecto en el service · `@NotNull Boolean` (obligatorio)

Decisión: `@NotNull Boolean active`; si falta, 400.

Por qué: con `boolean` un PUT que olvide el campo desactivaría la pista sin que nadie lo pida;
ser obligatorio encaja con `PUT` como reemplazo completo (#010) y hace que POST y PUT se
comporten igual.

Consecuencias: el cliente siempre tiene que enviar `active`, también al crear una pista.
La garantía de que `active` no es `null` depende de `@Valid` en el controller: el service
confía en ello. Si aparece otro llamante del service (otro service, un job…), valorar
`@Validated` en el service.
