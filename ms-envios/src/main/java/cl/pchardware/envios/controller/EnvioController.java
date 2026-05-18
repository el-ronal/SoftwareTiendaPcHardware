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
import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import cl.pchardware.envios.dto.DireccionEnvioRequest;
import cl.pchardware.envios.dto.DireccionEnvioResponse;
import cl.pchardware.envios.service.EnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/envios")
public class EnvioController {

    private final EnvioService envioService;

    // ==========================================
    // COURIER ENDPOINTS
    // ==========================================
    @GetMapping("/couriers")
    public ResponseEntity<List<CourierResponse>> findAllCouriers() {
        return ResponseEntity.ok(envioService.findAllCouriers());
    }

    @GetMapping("/couriers/{id}")
    public ResponseEntity<CourierResponse> findCourierById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(envioService.findCourierById(id));
    }

    @PostMapping("/couriers")
    public ResponseEntity<CourierResponse> createCourier(@Valid @RequestBody CourierRequest request) {
        CourierResponse creado = envioService.createCourier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/couriers/{id}")
    public ResponseEntity<CourierResponse> updateCourier(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody CourierRequest request) {
        return ResponseEntity.ok(envioService.updateCourier(id, request));
    }

    @DeleteMapping("/couriers/{id}")
    public ResponseEntity<Void> deleteCourierById(@PathVariable @NonNull Integer id) {
        envioService.deleteCourierById(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // DIRECCION DE ENVIO ENDPOINTS
    // ==========================================
    @GetMapping("/direcciones")
    public ResponseEntity<List<DireccionEnvioResponse>> findAllDirecciones() {
        return ResponseEntity.ok(envioService.findAllDirecciones());
    }

    @GetMapping("/direcciones/{id}")
    public ResponseEntity<DireccionEnvioResponse> findDireccionById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(envioService.findDireccionById(id));
    }

    @PostMapping("/direcciones")
    public ResponseEntity<DireccionEnvioResponse> createDireccion(@Valid @RequestBody DireccionEnvioRequest request) {
        DireccionEnvioResponse creado = envioService.createDireccion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/direcciones/{id}")
    public ResponseEntity<DireccionEnvioResponse> updateDireccion(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody DireccionEnvioRequest request) {
        return ResponseEntity.ok(envioService.updateDireccion(id, request));
    }

    @DeleteMapping("/direcciones/{id}")
    public ResponseEntity<Void> deleteDireccionById(@PathVariable @NonNull Integer id) {
        envioService.deleteDireccionById(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // DESPACHO ENDPOINTS
    // ==========================================
    @GetMapping("/despachos")
    public ResponseEntity<List<DespachoResponse>> findAllDespachos() {
        return ResponseEntity.ok(envioService.findAllDespachos());
    }

    @GetMapping("/despachos/{id}")
    public ResponseEntity<DespachoResponse> findDespachoById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(envioService.findDespachoById(id));
    }

    @PostMapping("/despachos")
    public ResponseEntity<DespachoResponse> createDespacho(@Valid @RequestBody DespachoRequest request) {
        DespachoResponse creado = envioService.createDespacho(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/despachos/{id}")
    public ResponseEntity<DespachoResponse> updateDespacho(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody DespachoRequest request) {
        return ResponseEntity.ok(envioService.updateDespacho(id, request));
    }

    @DeleteMapping("/despachos/{id}")
    public ResponseEntity<Void> deleteDespachoById(@PathVariable @NonNull Integer id) {
        envioService.deleteDespachoById(id);
        return ResponseEntity.noContent().build();
    }
}
