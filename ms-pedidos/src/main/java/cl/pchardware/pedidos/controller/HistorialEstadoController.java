package cl.pchardware.pedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.pedidos.dto.HistorialEstadoRequest;
import cl.pchardware.pedidos.dto.HistorialEstadoResponse;
import cl.pchardware.pedidos.service.HistorialEstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/historial-estados")
public class HistorialEstadoController {

    private final HistorialEstadoService historialEstadoService;

    @GetMapping
    public ResponseEntity<List<HistorialEstadoResponse>> findAll() {
        return ResponseEntity.ok(historialEstadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEstadoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(historialEstadoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HistorialEstadoResponse> create(@Valid @RequestBody HistorialEstadoRequest request) {
        HistorialEstadoResponse creado = historialEstadoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        historialEstadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
