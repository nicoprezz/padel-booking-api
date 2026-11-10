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

    // AUTO_INCREMENT de MySQL (→ DECISIONES.md #007)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourtType type;

    @Column(nullable = false)
    private boolean active;

    // JPA exige un constructor sin argumentos; protected para que no se use desde fuera.
    protected Court() {
    }

    public Court(String name, CourtType type, boolean active) {
        this.name = name;
        this.type = type;
        this.active = active;
    }

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
