package cl.pchardware.pagos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.pagos.dto.MetodoPagoRequest;
import cl.pchardware.pagos.dto.MetodoPagoResponse;
import cl.pchardware.pagos.service.MetodoPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagoResponse>> findAll() {
        return ResponseEntity.ok(metodoPagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(metodoPagoService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<MetodoPagoResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(metodoPagoService.findByCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<MetodoPagoResponse> create(@Valid @RequestBody MetodoPagoRequest request) {
        MetodoPagoResponse creado = metodoPagoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody MetodoPagoRequest request) {
        return ResponseEntity.ok(metodoPagoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        metodoPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
