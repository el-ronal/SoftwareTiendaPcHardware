package cl.pchardware.tasacion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.tasacion.dto.OfertaCompraRequest;
import cl.pchardware.tasacion.dto.OfertaCompraResponse;
import cl.pchardware.tasacion.service.OfertaCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ofertas-compra")
@RequiredArgsConstructor
public class OfertaCompraController {

    private final OfertaCompraService ofertaService;

    @GetMapping
    public ResponseEntity<List<OfertaCompraResponse>> findAll() {
        List<OfertaCompraResponse> respuestas = ofertaService.findAll();
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaCompraResponse> findById(@PathVariable Integer id) {
        OfertaCompraResponse respuesta = ofertaService.findById(id);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<OfertaCompraResponse> create(@Valid @RequestBody OfertaCompraRequest request) {
        OfertaCompraResponse respuesta = ofertaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaCompraResponse> update(
            @PathVariable Integer id, 
            @Valid @RequestBody OfertaCompraRequest request) {
        
        OfertaCompraResponse respuesta = ofertaService.update(id, request);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ofertaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}