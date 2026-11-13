# CLAUDE.md — Padel Booking API

API REST para gestionar las reservas de pistas de un club de pádel, aplicando las reglas
del club.

## Stack
- Java 21 · Spring Boot 4 · Maven
- Spring Web MVC, Spring Data JPA, Validation · Flyway
- MySQL 8.4 en Docker
- Tests: JUnit 5 + Mockito
- Más adelante: Spring Security, Docker Compose, API de un modelo de IA

## Comandos
- Arrancar: `./mvnw spring-boot:run`
- Compilar: `./mvnw compile`
- Tests: `./mvnw test`
- Issues y PR: `gh` (GitHub CLI)

## Mapa
- `.claude/rules/`: normas del proyecto. Siempre: arquitectura, decisiones, Git y estilo
  de código. Solo al tocar sus carpetas: API y base de datos
- `docs/DECISIONES.md`: registro de decisiones técnicas
- `src/main/resources/db/migration/`: migraciones de Flyway
- `bruno/`: peticiones de prueba de la API

## Hito actual
**Hito 1.5 — Organización del proyecto** (milestone en GitHub)

<!-- Instrucciones personales (no versionadas) -->
@docs-privados/CLAUDE-personal.md
