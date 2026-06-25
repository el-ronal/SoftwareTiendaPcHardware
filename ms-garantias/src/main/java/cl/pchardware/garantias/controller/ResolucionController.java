package cl.pchardware.garantias.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.garantias.dto.ResolucionRequest;
import cl.pchardware.garantias.dto.ResolucionResponse;
import cl.pchardware.garantias.service.ResolucionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/resoluciones")
@RequiredArgsConstructor
public class ResolucionController {

    private final ResolucionService resolucionService;

    @GetMapping
    public List<ResolucionResponse> findAll() {
        return resolucionService.findAll();
    }

    @GetMapping("/{id}")
    public ResolucionResponse findById(
            @PathVariable Integer id
    ) {
        return resolucionService.findById(id);
    }

    @GetMapping("/inspeccion/{idInspeccion}")
    public ResolucionResponse findByInspeccion(
            @PathVariable Integer idInspeccion
    ) {
        return resolucionService.findByInspeccion(idInspeccion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResolucionResponse create(
            @Valid @RequestBody ResolucionRequest request
    ) {
        return resolucionService.create(request);
    }

    @PutMapping("/{id}")
    public ResolucionResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody ResolucionRequest request
    ) {
        return resolucionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Integer id
    ) {
        resolucionService.deleteById(id);
    }
}