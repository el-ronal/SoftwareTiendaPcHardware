package cl.pchardware.devoluciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.devoluciones.dto.SolicitudDevolucionRequest;
import cl.pchardware.devoluciones.dto.SolicitudDevolucionResponse;
import cl.pchardware.devoluciones.service.SolicitudDevolucionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devoluciones")
public class SolicitudDevolucionController {

    private final SolicitudDevolucionService solicitudService;

    @GetMapping
    public ResponseEntity<List<SolicitudDevolucionResponse>> findAll() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.findById(id));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<SolicitudDevolucionResponse>> findByPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(solicitudService.findByPedido(idPedido));
    }

    @PostMapping
    public ResponseEntity<SolicitudDevolucionResponse> create(@Valid @RequestBody SolicitudDevolucionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> update(@PathVariable Integer id, @Valid @RequestBody SolicitudDevolucionRequest request) {
        return ResponseEntity.ok(solicitudService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        solicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
