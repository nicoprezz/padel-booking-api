# Registro de decisiones (ADR)

Un ADR (*Architecture Decision Record*) recoge una decisión técnica del proyecto: qué
problema había, qué opciones se valoraron, cuál se eligió y por qué. Cada decisión tiene
su propio archivo y no se reescribe: si cambia, se escribe un ADR nuevo y el antiguo
pasa a "Sustituida por".

## Formato
Se parte del formato de Michael Nygard (Título, Estado, Contexto, Decisión,
Consecuencias) y se amplía a propósito con **Fecha**, **Opciones** y **Por qué**, para
conservar también las alternativas descartadas y el razonamiento.

- Archivo: `NNNN-<slug-en-inglés>.md`. El contenido va en español.
- Entre ADR se enlaza con rutas relativas; en el código se cita como `ADR-NNNN`.
- En las ADR 0001–0017 (migradas del registro anterior, de un solo archivo), las
  referencias `#NNN` del texto equivalen a `ADR-0NNN`.

### Estados
- `Aceptada`: vigente.
- `Aceptada · Ampliada por [ADR-NNNN](...)`: vigente; otra decisión la completa sin
  anularla.
- `Sustituida por [ADR-NNNN](...)`: ya no está vigente; manda la nueva.

La ADR nueva enlaza a la antigua con una línea `Sustituye a: [ADR-NNNN](...)` o
`Amplía a: [ADR-NNNN](...)` debajo de su Estado. En la antigua solo cambia el Estado.

### Plantilla
```
# ADR-NNNN — Título corto

Estado: Aceptada

Fecha: AAAA-MM-DD

Contexto: qué problema había

Opciones: A · B · (C)

Decisión: cuál se eligió

Por qué: 2-3 razones

Consecuencias: qué se gana y qué se pierde
```

## Índice
| ADR | Título | Estado |
|---|---|---|
| [0001](0001-layered-architecture.md) | Arquitectura por capas | Aceptada |
| [0002](0002-mysql-with-docker-run.md) | MySQL en Docker con `docker run` (Compose más adelante) | Aceptada |
| [0003](0003-java-group-and-package.md) | Group y paquete Java | Aceptada |
| [0004](0004-spring-config-format.md) | Formato de configuración de Spring | Aceptada |
| [0005](0005-database-password-location.md) | Dónde va la contraseña de la base de datos | Aceptada |
| [0006](0006-ddl-auto-schema-management.md) | Gestión del esquema de la base de datos (`ddl-auto`) | Sustituida por [ADR-0017](0017-flyway-with-ddl-validate.md) |
| [0007](0007-id-generation-strategy.md) | Estrategia de generación de ids | Aceptada |
| [0008](0008-repository-base-interface.md) | Interfaz base de los repositorios | Aceptada |
| [0009](0009-duplicate-court-name-conflict.md) | Nombre de pista duplicado → 409 Conflict | Aceptada |
| [0010](0010-court-update-with-put.md) | Edición de pistas con PUT (sin PATCH) | Aceptada |
| [0011](0011-no-court-deletion.md) | Sin borrado de pistas (se desactivan) | Aceptada |
| [0012](0012-required-active-in-court-request.md) | `active` obligatorio en `CourtRequest` | Aceptada |
| [0013](0013-dto-entity-mapping-in-dtos.md) | Conversión DTO ↔ entidad en los propios DTOs | Aceptada |
| [0014](0014-service-receives-and-returns-dtos.md) | El service recibe y devuelve DTOs | Aceptada |
| [0015](0015-post-returns-201-with-location.md) | POST responde 201 con cabecera `Location` | Aceptada |
| [0016](0016-custom-error-response.md) | Formato del JSON de error: `record ErrorResponse` propio | Aceptada |
| [0017](0017-flyway-with-ddl-validate.md) | Esquema con Flyway + `ddl-auto=validate` (sustituye a #006) | Aceptada |
| [0018](0018-simplified-gitflow-branching.md) | Estrategia de ramas: GitFlow simplificado | Aceptada |
| [0019](0019-claude-config-structure.md) | Estructura de la configuración de Claude Code | Aceptada |
| [0020](0020-adr-decision-records.md) | Registro de decisiones en formato ADR | Aceptada |
| [0021](0021-database-constraint-names.md) | Nombres de las restricciones de la base de datos | Aceptada |
| [0022](0022-milestone-versioning.md) | Versionado de los hitos | Aceptada |
