# CLAUDE.md — Padel Booking API

## Stack
- Java 21 o 25 (LTS) · Spring Boot (última estable de Spring Initializr) · Maven
- Spring Web, Spring Data JPA, Validation · MySQL 8.4 en Docker
- Tests: JUnit 5 + Mockito
- Más adelante: Spring Security, Docker Compose, API de un modelo de IA

## Arquitectura
Por capas: `controller` → `service` → `repository`.
- Los controllers no tienen lógica de negocio: reciben, validan formato y delegan.
- Las reglas del club viven en los `service`.
- Nunca se exponen entidades JPA en la API: siempre DTOs.

## Comandos
- Arrancar: `./mvnw spring-boot:run`
- Tests: `./mvnw test`

<!-- Instrucciones personales (no versionadas) -->
@docs-privados/CLAUDE-personal.md
