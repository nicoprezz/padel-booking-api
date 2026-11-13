# ADR-0011 — Sin borrado de pistas (se desactivan)

Estado: Aceptada

Fecha: 2026-11-10

Contexto: `DELETE /api/courts/{id}` podía borrar físicamente o desactivar.

Opciones: borrado físico · `DELETE` que desactiva (soft delete) · sin `DELETE`, desactivar con `PUT`

Decisión: no hay endpoint `DELETE`; una pista se desactiva con `PUT` y `"active": false`.

Por qué: conserva el historial (las reservas apuntarán a la pista); un `DELETE` que no
borra de verdad confundiría a quien use la API; encaja con la regla "una pista inactiva
no admite reservas nuevas".

Consecuencias: las pistas no desaparecen nunca; a revisar en el futuro (p. ej., un
borrado físico solo para pistas sin reservas).
