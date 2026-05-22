package cl.pchardware.envios.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.envios.dto.DireccionEnvioRequest;
import cl.pchardware.envios.dto.DireccionEnvioResponse;
import cl.pchardware.envios.service.DireccionEnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/direcciones-envio")
public class DireccionEnvioController {

    private final DireccionEnvioService direccionEnvioService;

    @GetMapping
    public ResponseEntity<List<DireccionEnvioResponse>> findAll() {
        return ResponseEntity.ok(direccionEnvioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionEnvioResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(direccionEnvioService.findById(id));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<DireccionEnvioResponse> findByIdPedido(@PathVariable @NonNull Integer idPedido) {
        return ResponseEntity.ok(direccionEnvioService.findByIdPedido(idPedido));
    }

    @PostMapping
    public ResponseEntity<DireccionEnvioResponse> create(@Valid @RequestBody DireccionEnvioRequest request) {
        DireccionEnvioResponse creado = direccionEnvioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionEnvioResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody DireccionEnvioRequest request) {
        return ResponseEntity.ok(direccionEnvioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        direccionEnvioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
