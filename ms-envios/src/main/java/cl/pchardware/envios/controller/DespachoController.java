package cl.pchardware.envios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import cl.pchardware.envios.service.DespachoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/despachos")
public class DespachoController {

    private final DespachoService despachoService;

    @GetMapping
    public ResponseEntity<List<DespachoResponse>> findAll() {
        return ResponseEntity.ok(despachoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(despachoService.findById(id));
    }

    @GetMapping("/seguimiento/{codigoSeguimiento}")
    public ResponseEntity<DespachoResponse> findByCodigoSeguimiento(@PathVariable String codigoSeguimiento) {
        return ResponseEntity.ok(despachoService.findByCodigoSeguimiento(codigoSeguimiento));
    }

    @PostMapping
    public ResponseEntity<DespachoResponse> create(@Valid @RequestBody DespachoRequest request) {
        DespachoResponse creado = despachoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespachoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody DespachoRequest request) {
        return ResponseEntity.ok(despachoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        despachoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
