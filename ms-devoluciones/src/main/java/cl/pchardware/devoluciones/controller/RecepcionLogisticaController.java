package cl.pchardware.devoluciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.devoluciones.dto.RecepcionLogisticaRequest;
import cl.pchardware.devoluciones.dto.RecepcionLogisticaResponse;
import cl.pchardware.devoluciones.service.RecepcionLogisticaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recepciones-logisticas")
@RequiredArgsConstructor
public class RecepcionLogisticaController {

    private final RecepcionLogisticaService recepcionService;

    @GetMapping
    public List<RecepcionLogisticaResponse> findAll() {
        return recepcionService.findAll();
    }

    @GetMapping("/{id}")
    public RecepcionLogisticaResponse findById(
            @PathVariable Integer id
    ) {
        return recepcionService.findById(id);
    }

    @GetMapping("/devolucion/{idDevolucion}")
    public RecepcionLogisticaResponse findByDevolucion(
            @PathVariable Integer idDevolucion
    ) {
        return recepcionService.findByDevolucion(idDevolucion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecepcionLogisticaResponse create(
            @Valid @RequestBody RecepcionLogisticaRequest request
    ) {
        return recepcionService.create(request);
    }

    @PutMapping("/{id}")
    public RecepcionLogisticaResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody RecepcionLogisticaRequest request
    ) {
        return recepcionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Integer id
    ) {
        recepcionService.deleteById(id);
    }
}