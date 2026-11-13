package io.github.nicoprezz.padel.exception;

import io.github.nicoprezz.padel.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Traduce las excepciones que salen de los controllers a respuestas HTTP con un
 * {@link ErrorResponse} (→ ADR-0016).
 *
 * @implNote
 * {@code @RestControllerAdvice} hace que Spring aplique estos métodos a <b>todos</b> los
 * controllers: cuando una excepción sube sin capturar, Spring busca aquí el
 * {@code @ExceptionHandler} cuyo tipo encaje y usa su respuesta en vez del 500 por defecto.
 * Así los services solo lanzan excepciones y no saben nada de HTTP.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * El recurso pedido no existe.
     *
     * @param ex la excepción lanzada por el service
     * @return 404 Not Found con el mensaje de la excepción
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Los datos chocan con el estado actual (p. ej., nombre de pista repetido).
     *
     * @param ex la excepción lanzada por el service
     * @return 409 Conflict con el mensaje de la excepción
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * La base de datos rechaza la operación por una restricción de integridad
     * ({@code UNIQUE}, clave foránea, {@code NOT NULL}...).
     *
     * @implNote
     * Es la red de seguridad de la comprobación previa del service ({@code existsByName}):
     * si llegan dos peticiones con el mismo nombre a la vez, ambas pasan la comprobación y
     * solo el {@code UNIQUE} de la tabla frena la segunda (→ ADR-0009). No se
     * devuelve {@code ex.getMessage()}: contiene la sentencia SQL, columnas y nombres de
     * restricciones, que revelan el esquema de la base de datos.
     *
     * @param ex la excepción traducida por Spring desde el error de la base de datos
     * @return 409 Conflict con un mensaje genérico
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataViolation(DataIntegrityViolationException ex) {
        return build(HttpStatus.CONFLICT,
                "La operación entra en conflicto con los datos existentes.");
    }

    /**
     * Falla alguna anotación de validación del DTO ({@code @NotBlank}, {@code @Size}...)
     * al recibirlo con {@code @Valid}.
     *
     * @param ex contiene la lista de campos que no pasaron la validación
     * @return 400 Bad Request indicando qué campos fallan
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        String message = errors.stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return build(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * El cuerpo no se puede convertir al DTO: JSON mal formado o un valor que no encaja
     * con el tipo (p. ej., {@code "type": "PADEL"}, que no existe en {@code CourtType}).
     *
     * @implNote
     * No se devuelve {@code ex.getMessage()}: es un mensaje interno de Jackson con nombres
     * de clases Java, que no le sirve al cliente y revela detalles de la implementación.
     *
     * @param ex la excepción lanzada por Spring al leer el cuerpo
     * @return 400 Bad Request con un mensaje genérico
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición no es un JSON válido o contiene valores no permitidos.");
    }

    /** Envuelve el {@link ErrorResponse} con su código HTTP para no repetirlo en cada handler. */
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ErrorResponse.of(status, message));
    }
}
