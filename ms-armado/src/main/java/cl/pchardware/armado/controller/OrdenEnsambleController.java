package cl.pchardware.armado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.armado.dto.OrdenEnsambleRequest;
import cl.pchardware.armado.dto.OrdenEnsambleResponse;
import cl.pchardware.armado.service.OrdenEnsambleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ordenes-ensamble")
public class OrdenEnsambleController {

    private final OrdenEnsambleService ordenService;

    @GetMapping
    public ResponseEntity<List<OrdenEnsambleResponse>> findAll() {
        return ResponseEntity.ok(ordenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenEnsambleResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ordenService.findById(id));
    }

    @GetMapping("/tecnico/{idTecnico}")
    public ResponseEntity<List<OrdenEnsambleResponse>> findByTecnico(@PathVariable Integer idTecnico) {
        return ResponseEntity.ok(ordenService.findByTecnico(idTecnico));
    }

    @PostMapping
    public ResponseEntity<OrdenEnsambleResponse> create(@Valid @RequestBody OrdenEnsambleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenEnsambleResponse> update(@PathVariable Integer id, @Valid @RequestBody OrdenEnsambleRequest request) {
        return ResponseEntity.ok(ordenService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ordenService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
