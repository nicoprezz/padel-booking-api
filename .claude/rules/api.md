---
paths:
  - "src/main/java/**/controller/**"
  - "src/main/java/**/dto/**"
  - "src/main/java/**/exception/**"
  - "bruno/**"
---

# API REST

## Rutas y métodos
- Rutas en inglés, en plural y en minúscula, bajo `/api`: `/api/courts`, `/api/bookings`.
- POST de creación: 201 con cabecera `Location` (ruta del recurso nuevo) y el recurso en
  el cuerpo.
- Edición de un recurso: PUT con reemplazo completo, así que todos los campos son
  obligatorios.
- Nada se borra físicamente: los recursos se desactivan o se cancelan, para no perder el
  historial.

## Controllers y DTOs
- `@Valid` en todo `@RequestBody`; el formato se valida con anotaciones en el DTO.
- Los controllers no capturan excepciones: las traduce el `GlobalExceptionHandler`.

## Errores
- Todas las respuestas de error usan `ErrorResponse` (`status`, `error`, `message`). Por
  ahora `error` se deriva del estado HTTP.
- Códigos: 400 formato o validación, 404 recurso inexistente, 409 conflicto con datos
  existentes.
- Un error de negocio nuevo lleva su propia excepción y su `@ExceptionHandler` en el
  handler central.
- Mensajes en español, pensados para el cliente de la API.

## Bruno
- Cada endpoint nuevo tiene su caso correcto y un caso por cada error posible.
- Nombre de cada petición: `<método>-<caso>-<código>.yml` (p. ej.
  `post-duplicate-court-409.yml`, `post-create-court-201.yml`). El nombre del archivo y
  `info.name` coinciden.
- Las peticiones existentes no se renombran.
