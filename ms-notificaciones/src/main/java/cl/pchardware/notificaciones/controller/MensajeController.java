package cl.pchardware.notificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.notificaciones.dto.MensajeRequest;
import cl.pchardware.notificaciones.dto.MensajeResponse;
import cl.pchardware.notificaciones.service.MensajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    @GetMapping
    public ResponseEntity<List<MensajeResponse>> findAll() {
        return ResponseEntity.ok(mensajeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mensajeService.findById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MensajeResponse>> findByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(mensajeService.findByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<MensajeResponse> create(@Valid @RequestBody MensajeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        mensajeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
