package io.github.nicoprezz.padel.service;

import io.github.nicoprezz.padel.dto.CourtRequest;
import io.github.nicoprezz.padel.dto.CourtResponse;
import io.github.nicoprezz.padel.exception.ConflictException;
import io.github.nicoprezz.padel.exception.NotFoundException;
import io.github.nicoprezz.padel.model.Court;
import io.github.nicoprezz.padel.repository.CourtRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Reglas de negocio de las pistas. Recibe y devuelve DTOs: la entidad Court no sale de
 * aquí (→ DECISIONES.md #014). Lanza excepciones propias; el GlobalExceptionHandler las
 * convierte en respuestas HTTP (404, 409).
 */
@Service
public class CourtService {

    private final CourtRepository courtRepository;

    // Inyección por constructor: Spring ve un único constructor y le pasa el repositorio.
    // "final" garantiza que siempre está asignado y no cambia.
    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public List<CourtResponse> findAll() {
        return courtRepository.findAll().stream()
                .map(CourtResponse::from)
                .toList();
    }

    public CourtResponse findById(Long id) {
        // En este caso no hay nada que guardar solo se devuelve
        return CourtResponse.from(findCourtOrThrow(id));
    }

    public CourtResponse create(CourtRequest request) {
        // Si ya hay una pista con ese nombre → ConflictException (409).
        if(courtRepository.existsByName(request.name())) {
            throw new ConflictException("Ya existe una pista con el mismo nombre.");
        }
        // Si no, convierte el request en entidad, guárdala y devuelve el CourtResponse.
        return CourtResponse.from(courtRepository.save(request.toEntity()));
    }

    public CourtResponse update(Long id, CourtRequest request) {
        // 404 si no existe, 409 si el nombre lo usa OTRA pista,
        // actualizar con los setters, guardar y devolver el CourtResponse.
        Court court = findCourtOrThrow(id);

        if(courtRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new ConflictException("Ya existe una pista con el mismo nombre.");
        }

        court.setName(request.name());
        court.setActive(request.active());
        court.setType(request.type());

        return CourtResponse.from(courtRepository.save(court));
    }

    private Court findCourtOrThrow(Long id){
        // Si no existe → NotFoundException (404).
        return courtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe una pista con este Id."));
    }
}
