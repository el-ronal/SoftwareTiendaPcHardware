package cl.pchardware.armado.controller;

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

import cl.pchardware.armado.dto.ResultadoTestingRequest;
import cl.pchardware.armado.dto.ResultadoTestingResponse;
import cl.pchardware.armado.service.ResultadoTestingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/resultados-testing")
@RequiredArgsConstructor
public class ResultadoTestingController {

    private final ResultadoTestingService resultadoService;

    @GetMapping
    public ResponseEntity<List<ResultadoTestingResponse>> findAll() {
        List<ResultadoTestingResponse> respuestas = resultadoService.findAll();
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoTestingResponse> findById(@PathVariable Integer id) {
        ResultadoTestingResponse respuesta = resultadoService.findById(id);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<ResultadoTestingResponse> create(@Valid @RequestBody ResultadoTestingRequest request) {
        ResultadoTestingResponse respuesta = resultadoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoTestingResponse> update(
            @PathVariable Integer id, 
            @Valid @RequestBody ResultadoTestingRequest request) {
        
        ResultadoTestingResponse respuesta = resultadoService.update(id, request);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        resultadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}