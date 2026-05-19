package cl.pchardware.tasacion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.tasacion.dto.SolicitudTasacionRequest;
import cl.pchardware.tasacion.dto.SolicitudTasacionResponse;
import cl.pchardware.tasacion.service.SolicitudTasacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solicitudes-tasacion")
public class SolicitudTasacionController {

    private final SolicitudTasacionService solicitudService;

    @GetMapping
    public ResponseEntity<List<SolicitudTasacionResponse>> findAll() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudTasacionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.findById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<SolicitudTasacionResponse>> findByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(solicitudService.findByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<SolicitudTasacionResponse> create(@Valid @RequestBody SolicitudTasacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudTasacionResponse> update(@PathVariable Integer id, @Valid @RequestBody SolicitudTasacionRequest request) {
        return ResponseEntity.ok(solicitudService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        solicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
