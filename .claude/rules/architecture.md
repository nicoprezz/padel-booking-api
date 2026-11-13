# Arquitectura

## Capas
`controller → service → repository`, cada una solo llama a la siguiente.

- **controller**: recibe la petición, valida el formato con `@Valid` y delega en el
  service. No tiene lógica de negocio.
- **service**: casos de uso y reglas del club (solapes, horarios, límites). Toda la lógica
  de negocio vive aquí.
- **repository**: acceso a la base de datos con Spring Data JPA.

## Reglas
- Las entidades JPA nunca se exponen en la API: el service recibe y devuelve DTOs, para
  que el controller no toque entidades.
- La conversión DTO ↔ entidad está en los propios DTOs (`from()` / `toEntity()`), para no
  repetirla en cada service.
- Los errores de negocio se lanzan como excepciones propias y se traducen a HTTP en el
  `@RestControllerAdvice` central; ni el service ni el controller construyen respuestas
  de error.
