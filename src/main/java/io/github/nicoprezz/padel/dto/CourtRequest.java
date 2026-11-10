package io.github.nicoprezz.padel.dto;

import io.github.nicoprezz.padel.model.CourtType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Lo que el cliente envía para crear (POST) o editar (PUT) una pista.
 * No lleva id: en el POST lo genera MySQL y en el PUT va en la URL (/api/courts/{id}).
 * Las anotaciones de validación se comprueban cuando el controller recibe el DTO con @Valid.
 */
public record CourtRequest(
        // @NotBlank: obligatorio y con texto real (rechaza null, "" y "   ").
        // @Size(max = 255): como mucho 255 caracteres, lo que cabe en la columna VARCHAR(255).
        @NotBlank @Size(max = 255) String name,

        // @NotNull: obligatorio. Un valor que no existe en CourtType ya lo rechaza Jackson.
        @NotNull CourtType type,

        // @NotNull: obligatorio (→ DECISIONES.md #012). Va con Boolean y no boolean porque
        // un primitivo no puede ser null: si falta el campo, Jackson pondría false sin avisar.
        @NotNull Boolean active
) {
}
