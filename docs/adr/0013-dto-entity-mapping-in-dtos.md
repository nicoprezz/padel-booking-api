# ADR-0013 — Conversión DTO ↔ entidad en los propios DTOs

Estado: Aceptada

Fecha: 2026-11-10

Contexto: el service necesita convertir `CourtRequest` → `Court` y `Court` → `CourtResponse`.

Opciones: métodos privados en el service · clase `CourtMapper` aparte · métodos en el
propio DTO · librería MapStruct

Decisión: métodos en el DTO (`CourtResponse.from(Court)`, `CourtRequest.toEntity()`).

Por qué: con tres campos es lo más legible y no añade archivos; el service se queda
con las reglas del club. MapStruct se descarta por ser una dependencia que oculta
lo que se quiere aprender.

Consecuencias: los DTOs importan la entidad (la entidad sigue sin conocer la API). La
actualización del PUT se hace con setters en el service, no en el DTO. Si el proyecto
crece (más entidades o conversiones complejas), pasar a una clase mapper.
