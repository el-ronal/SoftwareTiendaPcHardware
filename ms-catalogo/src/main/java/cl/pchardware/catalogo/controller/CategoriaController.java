package cl.pchardware.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.catalogo.dto.CategoriaRequest;
import cl.pchardware.catalogo.dto.CategoriaResponse;
import cl.pchardware.catalogo.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(@PathVariable Integer id, @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
