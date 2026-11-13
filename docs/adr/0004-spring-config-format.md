# ADR-0004 — Formato de configuración de Spring

Estado: Aceptada

Fecha: 2026-11-05

Contexto: Spring Initializr pide elegir el formato de `application.*`.

Opciones: `application.properties` · `application.yaml`

Decisión: Properties.

Por qué: una línea por propiedad, sin errores de sangría; es lo que usan las guías
oficiales de spring.io, así que puedo compararlas directamente.

Consecuencias: con muchas propiedades se repiten prefijos (`spring.datasource.*`);
si crece, puedo migrar a YAML (Spring lee los dos).
