package io.github.nicoprezz.padel.exception;

/**
 * Los datos tienen un formato válido, pero chocan con el estado actual (p. ej., nombre de
 * pista repetido). Se traducirá a un 409 (→ DECISIONES.md #009).
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
