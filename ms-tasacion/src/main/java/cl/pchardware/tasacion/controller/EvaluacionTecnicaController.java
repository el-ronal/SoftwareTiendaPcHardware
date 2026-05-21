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

import cl.pchardware.tasacion.dto.EvaluacionTecnicaRequest;
import cl.pchardware.tasacion.dto.EvaluacionTecnicaResponse;
import cl.pchardware.tasacion.service.EvaluacionTecnicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/evaluaciones-tecnicas")
@RequiredArgsConstructor
public class EvaluacionTecnicaController {

    private final EvaluacionTecnicaService evaluacionService;

    @GetMapping
    public ResponseEntity<List<EvaluacionTecnicaResponse>> findAll() {
        List<EvaluacionTecnicaResponse> respuestas = evaluacionService.findAll();
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionTecnicaResponse> findById(@PathVariable Integer id) {
        EvaluacionTecnicaResponse respuesta = evaluacionService.findById(id);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<EvaluacionTecnicaResponse> create(@Valid @RequestBody EvaluacionTecnicaRequest request) {
        EvaluacionTecnicaResponse respuesta = evaluacionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionTecnicaResponse> update(
            @PathVariable Integer id, 
            @Valid @RequestBody EvaluacionTecnicaRequest request) {
        
        EvaluacionTecnicaResponse respuesta = evaluacionService.update(id, request);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        evaluacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}