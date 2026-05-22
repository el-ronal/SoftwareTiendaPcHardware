package cl.pchardware.stock.controller;

import cl.pchardware.stock.dto.BodegaRequest;
import cl.pchardware.stock.dto.BodegaResponse;
import cl.pchardware.stock.service.BodegaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bodegas")
public class BodegaController {

    private final BodegaService bodegaService;

    @GetMapping
    public ResponseEntity<List<BodegaResponse>> findAll() {
        return ResponseEntity.ok(bodegaService.findAll());
    }

    @PostMapping
    public ResponseEntity<BodegaResponse> create(@Valid @RequestBody BodegaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaService.create(request));
    }
}