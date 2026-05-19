package cl.pchardware.armado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.armado.dto.TecnicoArmadoRequest;
import cl.pchardware.armado.dto.TecnicoArmadoResponse;
import cl.pchardware.armado.service.TecnicoArmadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tecnicos-armado")
public class TecnicoArmadoController {

    private final TecnicoArmadoService tecnicoService;

    @GetMapping
    public ResponseEntity<List<TecnicoArmadoResponse>> findAll() {
        return ResponseEntity.ok(tecnicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoArmadoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TecnicoArmadoResponse> create(@Valid @RequestBody TecnicoArmadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoArmadoResponse> update(@PathVariable Integer id, @Valid @RequestBody TecnicoArmadoRequest request) {
        return ResponseEntity.ok(tecnicoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        tecnicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
