package cl.pchardware.catalogo.controller;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductoResponse> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productoService.findBySku(sku));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(request));
    }

    @PutMapping("/{sku}")
    public ResponseEntity<ProductoResponse> update(
            @PathVariable String sku, 
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.update(sku, request));
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteBySku(@PathVariable String sku) {
        productoService.deleteBySku(sku);
        return ResponseEntity.noContent().build();
    }
}