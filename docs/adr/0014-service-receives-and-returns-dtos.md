# ADR-0014 — El service recibe y devuelve DTOs

Estado: Aceptada

Fecha: 2026-11-10

Contexto: hay que decidir en qué capa se convierte DTO ↔ entidad, es decir, quién llama a
`from()` y `toEntity()`.

Opciones: el service devuelve DTOs (la entidad no sale del service) · el service devuelve
entidades y el controller convierte

Decisión: el service recibe `CourtRequest` y devuelve `CourtResponse`.

Por qué: el controller queda sin lógica (solo recibe, valida y delega) y la entidad JPA
vive en una sola capa, así que no se puede exponer por accidente.

Consecuencias: los métodos del service no se pueden reutilizar desde otro service para
obtener entidades; en el hito 2, `BookingService` usará `CourtRepository` directamente
para conseguir el `Court` de una reserva.
