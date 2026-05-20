package cl.pchardware.catalogo.controller;

import cl.pchardware.catalogo.dto.MarcaRequest;
import cl.pchardware.catalogo.dto.MarcaResponse;
import cl.pchardware.catalogo.service.MarcaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> findAll() {
        return ResponseEntity.ok(marcaService.findAll());
    }

    @PostMapping
    public ResponseEntity<MarcaResponse> create(@Valid @RequestBody MarcaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.create(request));
    }
}