package cl.pchardware.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioResponse>> findAll() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<InventarioResponse>> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(inventarioService.findBySku(sku));
    }

    @PostMapping
    public ResponseEntity<InventarioResponse> create(@Valid @RequestBody InventarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponse> update(@PathVariable Integer id, @Valid @RequestBody InventarioRequest request) {
        return ResponseEntity.ok(inventarioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        inventarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
