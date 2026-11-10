package io.github.nicoprezz.padel.repository;

import io.github.nicoprezz.padel.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a la tabla "courts". Spring Data genera la implementación en tiempo de ejecución.
 * JpaRepository<Court, Long>: entidad que gestiona y tipo de su id (→ DECISIONES.md #011).
 */
public interface CourtRepository extends JpaRepository<Court, Long> {

    // Para el POST: ¿hay alguna pista con este nombre?
    boolean existsByName(String name);

    // Para el PUT: ¿hay OTRA pista (con distinto id) que tenga este nombre?
    boolean existsByNameAndIdNot(String name, Long id);
}
