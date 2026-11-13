# Padel Booking API

> 🚧 Work in progress — learning project.

This is a learning and portfolio project: understanding every piece of code matters more
than shipping features fast.

REST API for managing court bookings at a padel club, built with Java and Spring Boot.
Includes club booking rules, authentication and an AI assistant that books courts from
natural-language requests.

## Features
- [x] Court management
- [ ] Bookings with club rules (no overlaps, opening hours, limits)
- [ ] Free time slots per court and day
- [ ] JWT authentication with roles
- [ ] Automated tests
- [ ] Docker deployment
- [ ] AI assistant with tool calling

## Tech stack
Java · Spring Boot · Spring Data JPA · MySQL · Spring Security · JUnit · Docker

## Getting started
```bash
# TODO: completar cuando funcione el hito 5
./mvnw spring-boot:run
```

## API
<!-- TODO: tabla de endpoints o enlace a Swagger -->

## What I learned
<!-- TODO: 3-5 puntos. Ejemplo: cómo evitar solapes de reservas con consultas JPA -->

## How I work on this project
- **Git:** simplified GitFlow. `main` holds released versions and `develop` is the
  integration branch. Each GitHub issue gets its own `feature/<n>-<slug>` branch,
  merged into `develop` through a squashed pull request.
- **Commits:** [Conventional Commits](https://www.conventionalcommits.org/) (`feat:`,
  `fix:`, `docs:`…).
- **Milestones:** each project stage is a GitHub milestone; closing one means a PR from
  `develop` to `main` and an annotated version tag.
- **Decisions:** every technical decision is recorded as an ADR (context, options
  considered, decision and consequences) in [`docs/adr/`](docs/adr/README.md).
- **Claude Code:** I use it as a tutor, not as a code generator. The project rules it
  follows are versioned:
  - `CLAUDE.md`: project overview and map
  - `.claude/rules/`: architecture, code style, Git, API and database conventions
  - `.claude/skills/`: guided steps for repeated tasks (new resource, new migration,
    closing a milestone)

## Author
Nicolás Pérez Fernández · Software Engineering student, University of Oviedo
<!-- TODO: añadir enlace a LinkedIn -->

## License
Released under the [MIT License](LICENSE).
