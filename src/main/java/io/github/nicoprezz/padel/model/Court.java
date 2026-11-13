package io.github.nicoprezz.padel.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

/**
 * Pista del club. Entidad JPA: se mapea a la tabla "courts".
 * Nunca se devuelve directamente en la API (para eso está CourtResponse).
 */
@Entity
@Table(name = "courts")
public class Court {

    /**
     * Clave primaria. La genera MySQL con AUTO_INCREMENT al hacer el INSERT
     * (→ ADR-0023), por eso es {@code null} hasta que se guarda.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Long id;

    /** Nombre de la pista; único en la tabla (un repetido da 409, → ADR-0009). */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Cubierta o exterior. Se guarda como texto ({@code EnumType.STRING}) y no como
     * posición, para que añadir valores al enum no cambie el significado de los datos.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourtType type;

    /**
     * Si admite reservas nuevas. Las pistas no se borran, se desactivan (→ ADR-0011).
     *
     * @implNote
     * {@code nullable = false} es necesario aunque sea un primitivo: un {@code @Column}
     * sin él genera la columna como NULL en MySQL.
     */
    @Column(nullable = false)
    private boolean active;

    /**
     * Constructor vacío que exige JPA para crear la entidad al leerla de la base de datos.
     * Es {@code protected} para que no se use desde fuera: una pista sin datos no es válida.
     */
    protected Court() {
    }

    /**
     * Crea una pista nueva, todavía sin guardar (sin id).
     *
     * @param name   nombre único de la pista
     * @param type   cubierta o exterior
     * @param active si admite reservas
     */
    public Court(String name, CourtType type, boolean active) {
        this.name = name;
        this.type = type;
        this.active = active;
    }

    /**
     * @return el id de la pista, o {@code null} si todavía no se ha guardado
     */
    @Nullable
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourtType getType() {
        return type;
    }

    public void setType(CourtType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
