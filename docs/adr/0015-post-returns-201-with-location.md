# ADR-0015 — POST responde 201 con cabecera `Location`

Estado: Aceptada

Fecha: 2026-11-10

Contexto: al crear una pista hay que decidir qué respuesta HTTP recibe el cliente.

Opciones: 200 con la pista · 201 con la pista (`ResponseEntity.status(CREATED)` o
`@ResponseStatus`) · 201 con la pista + cabecera `Location` con la URL del recurso

Decisión: 201 + `Location`, construida con `ServletUriComponentsBuilder.fromCurrentRequest()`.

Por qué: es lo que recomienda HTTP (RFC 9110) para un recurso creado; el cliente sabe
dónde está la pista sin montar la URL a mano. Partir de la petición actual evita repetir
`/api/courts` y da una URL completa que sigue siendo válida al desplegar (hito 5).

Consecuencias: el método `create` devuelve `ResponseEntity` y no el DTO directamente.
Los futuros POST (reservas en el hito 2) seguirán el mismo patrón.
