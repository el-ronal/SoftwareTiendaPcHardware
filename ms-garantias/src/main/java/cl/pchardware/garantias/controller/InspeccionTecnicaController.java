package cl.pchardware.garantias.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.garantias.dto.InspeccionTecnicaRequest;
import cl.pchardware.garantias.dto.InspeccionTecnicaResponse;
import cl.pchardware.garantias.service.InspeccionTecnicaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inspecciones-tecnicas")
@RequiredArgsConstructor
public class InspeccionTecnicaController {

    private final InspeccionTecnicaService inspeccionService;

    @GetMapping
    public List<InspeccionTecnicaResponse> findAll() {

        return inspeccionService.findAll();
    }

    @GetMapping("/{id}")
    public InspeccionTecnicaResponse findById(
            @PathVariable Integer id
    ) {

        return inspeccionService.findById(id);
    }

    @GetMapping("/ticket/{idTicket}")
    public InspeccionTecnicaResponse findByTicket(
            @PathVariable Integer idTicket
    ) {

        return inspeccionService.findByTicket(idTicket);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InspeccionTecnicaResponse create(
            @Valid @RequestBody InspeccionTecnicaRequest request
    ) {

        return inspeccionService.create(request);
    }

    @PutMapping("/{id}")
    public InspeccionTecnicaResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody InspeccionTecnicaRequest request
    ) {

        return inspeccionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Integer id
    ) {

        inspeccionService.deleteById(id);
    }
}