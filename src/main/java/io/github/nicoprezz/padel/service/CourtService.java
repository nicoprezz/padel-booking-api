package io.github.nicoprezz.padel.service;

import io.github.nicoprezz.padel.dto.CourtRequest;
import io.github.nicoprezz.padel.dto.CourtResponse;
import io.github.nicoprezz.padel.exception.ConflictException;
import io.github.nicoprezz.padel.exception.NotFoundException;
import io.github.nicoprezz.padel.model.Court;
import io.github.nicoprezz.padel.repository.CourtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Reglas de negocio de las pistas. Recibe y devuelve DTOs: la entidad Court no sale de
 * aquí (→ DECISIONES.md #014). Lanza excepciones propias; el GlobalExceptionHandler las
 * convierte en respuestas HTTP (404, 409).
 */
@Service
public class CourtService {

    private final CourtRepository courtRepository;

    /**
     * Crea el servicio con su repositorio.
     *
     * @implNote
     * Inyección por constructor: Spring ve un único constructor y le pasa el repositorio.
     * {@code final} garantiza que siempre está asignado y no cambia.
     *
     * @param courtRepository acceso a la tabla "courts"
     */
    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    /**
     * Devuelve todas las pistas del club, activas o no.
     *
     * @return las pistas como DTOs; una lista vacía si no hay ninguna
     */
    public List<CourtResponse> findAll() {
        return courtRepository.findAll().stream()
                .map(CourtResponse::from)
                .toList();
    }

    /**
     * Devuelve una pista por su id. Solo lee: no guarda nada.
     *
     * @param id id de la pista
     * @return la pista como DTO
     * @throws NotFoundException si no existe una pista con ese id (404)
     */
    public CourtResponse findById(Long id) {
        return CourtResponse.from(findCourtOrThrow(id));
    }

    /**
     * Crea una pista nueva.
     *
     * @implNote
     * Se convierte a DTO lo que devuelve {@code save}, no la entidad original:
     * el id lo genera MySQL al hacer el INSERT (→ DECISIONES.md #007).
     *
     * @param request datos de la pista, ya validados con {@code @Valid}
     * @return la pista creada, con su id
     * @throws ConflictException si ya existe una pista con ese nombre (409)
     */
    public CourtResponse create(CourtRequest request) {
        if (courtRepository.existsByName(request.name())) {
            throw new ConflictException("Ya existe una pista con el mismo nombre.");
        }
        return CourtResponse.from(courtRepository.save(request.toEntity()));
    }

    /**
     * Reemplaza todos los datos de una pista existente (PUT, → DECISIONES.md #010).
     * Desactivar una pista es enviar {@code "active": false} (→ DECISIONES.md #011).
     *
     * @implNote
     * Primero el 404 y después el 409: si la pista no existe, el nombre da igual.
     * Se usa {@code existsByNameAndIdNot} para que mantener el mismo nombre no choque
     * con la propia pista. Se modifica la entidad existente con setters (no
     * {@code toEntity()}) para que {@code save} haga un UPDATE y no un INSERT.
     *
     * @param id      id de la pista a editar
     * @param request datos nuevos, ya validados con {@code @Valid}
     * @return la pista actualizada
     * @throws NotFoundException si no existe una pista con ese id (404)
     * @throws ConflictException si otra pista ya usa ese nombre (409)
     */
    public CourtResponse update(Long id, CourtRequest request) {
        Court court = findCourtOrThrow(id);

        if (courtRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new ConflictException("Ya existe una pista con el mismo nombre.");
        }

        court.setName(request.name());
        court.setActive(request.active());
        court.setType(request.type());

        return CourtResponse.from(courtRepository.save(court));
    }

    /**
     * Busca la entidad de una pista. Lo comparten {@link #findById} y {@link #update}.
     *
     * @param id id de la pista
     * @return la entidad (no el DTO), para poder modificarla con sus setters
     * @throws NotFoundException si no existe una pista con ese id (404)
     */
    private Court findCourtOrThrow(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe una pista con este Id."));
    }
}
