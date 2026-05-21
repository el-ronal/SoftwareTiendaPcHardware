package cl.pchardware.pagos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.pagos.dto.TransaccionRequest;
import cl.pchardware.pagos.dto.TransaccionResponse;
import cl.pchardware.pagos.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<List<TransaccionResponse>> findAll() {
        return ResponseEntity.ok(transaccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(transaccionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TransaccionResponse> create(@Valid @RequestBody TransaccionRequest request) {
        TransaccionResponse creado = transaccionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody TransaccionRequest request) {
        return ResponseEntity.ok(transaccionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        transaccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
