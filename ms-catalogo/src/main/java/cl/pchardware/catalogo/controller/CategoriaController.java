package cl.pchardware.catalogo.controller;

import cl.pchardware.catalogo.dto.CategoriaRequest;
import cl.pchardware.catalogo.dto.CategoriaResponse;
import cl.pchardware.catalogo.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.create(request));
    }
}