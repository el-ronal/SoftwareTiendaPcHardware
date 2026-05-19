package cl.pchardware.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductoResponse> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productoService.findBySku(sku));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(@PathVariable Integer id, @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
