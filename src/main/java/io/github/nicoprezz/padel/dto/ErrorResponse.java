package io.github.nicoprezz.padel.dto;

import org.springframework.http.HttpStatus;

/**
 * Cuerpo JSON que recibe el cliente cuando algo sale mal (→ DECISIONES.md #016).
 * Ejemplo: {@code { "status": 409, "error": "Conflict", "message": "Ya existe una pista..." }}.
 * Lo construye siempre el GlobalExceptionHandler, nunca un controller.
 *
 * @param status  código HTTP numérico (repetido en el cuerpo para quien no mire la cabecera)
 * @param error   nombre estándar del código HTTP ("Not Found", "Conflict"...)
 * @param message explicación en lenguaje natural, pensada para mostrarse al usuario
 */
public record ErrorResponse(int status, String error, String message) {

    /**
     * Crea la respuesta a partir del estado HTTP, para no repetir el código y su nombre
     * a mano en cada handler.
     *
     * @param status  estado HTTP de la respuesta
     * @param message explicación para el cliente
     * @return el cuerpo de error listo para serializar
     */
    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(status.value(), status.getReasonPhrase(), message);
    }
}
