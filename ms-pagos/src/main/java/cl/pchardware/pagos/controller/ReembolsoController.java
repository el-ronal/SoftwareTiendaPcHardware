package cl.pchardware.pagos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.pagos.dto.ReembolsoRequest;
import cl.pchardware.pagos.dto.ReembolsoResponse;
import cl.pchardware.pagos.service.ReembolsoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reembolsos")
public class ReembolsoController {

    private final ReembolsoService reembolsoService;

    @GetMapping
    public ResponseEntity<List<ReembolsoResponse>> findAll() {
        return ResponseEntity.ok(reembolsoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReembolsoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(reembolsoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReembolsoResponse> create(@Valid @RequestBody ReembolsoRequest request) {
        ReembolsoResponse creado = reembolsoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReembolsoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody ReembolsoRequest request) {
        return ResponseEntity.ok(reembolsoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        reembolsoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
