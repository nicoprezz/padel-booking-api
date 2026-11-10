package io.github.nicoprezz.padel.exception;

/**
 * El recurso pedido no existe (p. ej., GET /api/courts/99). Se traducirá a un 404.
 * Hereda de RuntimeException para no obligar a declarar "throws" en cada método:
 * la captura el GlobalExceptionHandler (@RestControllerAdvice), no quien la llama.
 */
public class NotFoundException extends RuntimeException {

    /**
     * @param message explicación para el cliente; acabará en el JSON de error
     */
    public NotFoundException(String message) {
        super(message);
    }
}
