package io.github.nicoprezz.padel.controller;

import io.github.nicoprezz.padel.dto.CourtRequest;
import io.github.nicoprezz.padel.dto.CourtResponse;
import io.github.nicoprezz.padel.service.CourtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Endpoints REST de las pistas ({@code /api/courts}). Sin lógica de negocio: recibe la
 * petición, valida el formato con {@code @Valid} y delega en {@link CourtService}.
 * Sin {@code DELETE}: las pistas se desactivan con un PUT (→ DECISIONES.md #011).
 */
@RestController
@RequestMapping("/api/courts")
public class CourtController {

    private final CourtService courtService;

    /**
     * Crea el controller con su servicio (inyección por constructor).
     *
     * @param courtService reglas de negocio de las pistas
     */
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    /**
     * Lista todas las pistas, activas o no.
     *
     * @return 200 con la lista (vacía si no hay pistas)
     */
    @GetMapping
    public List<CourtResponse> findAll() {
        return courtService.findAll();
    }

    /**
     * Devuelve el detalle de una pista.
     *
     * @param id id de la pista, tomado de la URL
     * @return 200 con la pista
     * @throws io.github.nicoprezz.padel.exception.NotFoundException si no existe (404)
     */
    @GetMapping("/{id}")
    public CourtResponse findById(@PathVariable Long id) {
        return courtService.findById(id);
    }

    /**
     * Crea una pista nueva.
     *
     * @param request datos de la pista; {@code @Valid} los comprueba antes de entrar
     * @return 201 con la pista creada (con su id) y la cabecera {@code Location} con su URL
     * @throws io.github.nicoprezz.padel.exception.ConflictException si el nombre ya existe (409)
     *
     * @implNote
     * La URL de {@code Location} se construye a partir de la petición actual
     * ({@code /api/courts}) + el id, así no se repite la ruta de {@code @RequestMapping}
     * (→ DECISIONES.md #015).
     */
    @PostMapping
    public ResponseEntity<CourtResponse> create(@Valid @RequestBody CourtRequest request) {
        CourtResponse courtResponse = courtService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(courtResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(courtResponse);
    }

    /**
     * Reemplaza todos los datos de una pista (→ DECISIONES.md #010).
     *
     * @param id      id de la pista, tomado de la URL
     * @param request datos nuevos; {@code @Valid} los comprueba antes de entrar
     * @return 200 con la pista actualizada
     * @throws io.github.nicoprezz.padel.exception.NotFoundException si no existe (404)
     * @throws io.github.nicoprezz.padel.exception.ConflictException si otra pista usa ese nombre (409)
     */
    @PutMapping("/{id}")
    public CourtResponse update(@PathVariable Long id, @Valid @RequestBody CourtRequest request) {
        return courtService.update(id, request);
    }
}
