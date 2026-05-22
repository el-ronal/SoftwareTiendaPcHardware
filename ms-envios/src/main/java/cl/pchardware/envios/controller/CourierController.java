package cl.pchardware.envios.controller;

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

import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import cl.pchardware.envios.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierController {

    private final CourierService courierService;

    @GetMapping
    public ResponseEntity<List<CourierResponse>> findAll() {
        return ResponseEntity.ok(courierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(courierService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CourierResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(courierService.findByCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<CourierResponse> create(@Valid @RequestBody CourierRequest request) {
        CourierResponse creado = courierService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourierResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody CourierRequest request) {
        return ResponseEntity.ok(courierService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        courierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
