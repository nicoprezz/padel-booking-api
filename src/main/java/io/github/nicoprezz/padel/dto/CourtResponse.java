package io.github.nicoprezz.padel.dto;

import io.github.nicoprezz.padel.model.CourtType;

/**
 * Lo que la API devuelve de una pista (GET, y respuesta de POST/PUT).
 * Es lo único que ve el cliente: la entidad Court nunca sale de la capa de servicio.
 */
public record CourtResponse(
        Long id,
        String name,
        CourtType type,
        boolean active
) {
}
