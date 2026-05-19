package cl.pchardware.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.stock.dto.BodegaRequest;
import cl.pchardware.stock.dto.BodegaResponse;
import cl.pchardware.stock.service.BodegaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bodegas")
public class BodegaController {

    private final BodegaService bodegaService;

    @GetMapping
    public ResponseEntity<List<BodegaResponse>> findAll() {
        return ResponseEntity.ok(bodegaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodegaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(bodegaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BodegaResponse> create(@Valid @RequestBody BodegaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BodegaResponse> update(@PathVariable Integer id, @Valid @RequestBody BodegaRequest request) {
        return ResponseEntity.ok(bodegaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        bodegaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
