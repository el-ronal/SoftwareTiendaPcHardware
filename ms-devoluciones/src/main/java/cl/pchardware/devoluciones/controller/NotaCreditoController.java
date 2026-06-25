package cl.pchardware.devoluciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.devoluciones.dto.NotaCreditoRequest;
import cl.pchardware.devoluciones.dto.NotaCreditoResponse;
import cl.pchardware.devoluciones.service.NotaCreditoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notas-credito")
@RequiredArgsConstructor
public class NotaCreditoController {

    private final NotaCreditoService notaService;

    @GetMapping
    public List<NotaCreditoResponse> findAll() {

        return notaService.findAll();
    }

    @GetMapping("/{id}")
    public NotaCreditoResponse findById(
            @PathVariable Integer id
    ) {

        return notaService.findById(id);
    }

    @GetMapping("/recepcion/{idRecepcion}")
    public NotaCreditoResponse findByRecepcion(
            @PathVariable Integer idRecepcion
    ) {

        return notaService.findByRecepcion(idRecepcion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotaCreditoResponse create(
            @Valid @RequestBody NotaCreditoRequest request
    ) {

        return notaService.create(request);
    }

    @PutMapping("/{id}")
    public NotaCreditoResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody NotaCreditoRequest request
    ) {

        return notaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Integer id
    ) {

        notaService.deleteById(id);
    }
}