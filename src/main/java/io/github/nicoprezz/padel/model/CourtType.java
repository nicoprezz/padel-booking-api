package io.github.nicoprezz.padel.model;

/**
 * Tipo de pista: cubierta (cristal) o exterior.
 * En la base de datos se guarda por nombre (ver {@link Court}).
 */
public enum CourtType {
    /** Pista cubierta de cristal. */
    INDOOR,
    /** Pista exterior. */
    OUTDOOR
}
