# ADR-0001 — Arquitectura por capas

Estado: Aceptada

Fecha: nov 2026

Contexto: necesito una estructura para la API que pueda entender y explicar.

Opciones: por capas (controller/service/repository) · por funcionalidad (court/, booking/)

Decisión: por capas.

Por qué: es la más común en ofertas de Spring Boot y la más fácil de aprender primero.

Consecuencias: si el proyecto crece mucho, los paquetes se llenan; valorar migrar a
paquetes por funcionalidad más adelante.
