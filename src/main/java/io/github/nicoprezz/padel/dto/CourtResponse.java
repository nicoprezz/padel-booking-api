package io.github.nicoprezz.padel.dto;

import io.github.nicoprezz.padel.model.Court;
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

    /**
     * Convierte la entidad en lo que ve el cliente (→ ADR-0013).
     * Lo llama el service; el controller nunca ve la entidad (→ ADR-0014).
     *
     * @param court pista ya guardada (con id)
     * @return el DTO con los datos de la pista
     */
    public static CourtResponse from(Court court) {
        return new CourtResponse(court.getId(), court.getName(), court.getType(), court.isActive());
    }
}
