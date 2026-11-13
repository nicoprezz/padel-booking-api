package io.github.nicoprezz.padel.repository;

import io.github.nicoprezz.padel.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a la tabla "courts". Spring Data genera la implementación en tiempo de ejecución.
 * JpaRepository<Court, Long>: entidad que gestiona y tipo de su id (→ ADR-0024).
 */
public interface CourtRepository extends JpaRepository<Court, Long> {

    /**
     * Comprueba si hay alguna pista con este nombre. Se usa al crear (POST).
     *
     * @implNote
     * Consulta derivada: Spring Data genera el SQL a partir del nombre del método.
     *
     * @param name nombre a comprobar
     * @return {@code true} si ya existe una pista con ese nombre
     */
    boolean existsByName(String name);

    /**
     * Comprueba si hay OTRA pista (con distinto id) con este nombre. Se usa al editar (PUT),
     * para que mantener el mismo nombre no choque con la propia pista.
     *
     * @param name nombre a comprobar
     * @param id   id de la pista que se está editando (se excluye de la búsqueda)
     * @return {@code true} si otra pista ya usa ese nombre
     */
    boolean existsByNameAndIdNot(String name, Long id);
}
