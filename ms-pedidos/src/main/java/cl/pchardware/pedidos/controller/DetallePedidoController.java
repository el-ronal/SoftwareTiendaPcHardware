package cl.pchardware.pedidos.controller;

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

import cl.pchardware.pedidos.dto.DetallePedidoRequest;
import cl.pchardware.pedidos.dto.DetallePedidoResponse;
import cl.pchardware.pedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/detalles-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoResponse>> findAll() {
        return ResponseEntity.ok(detallePedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(detallePedidoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoResponse> create(@Valid @RequestBody DetallePedidoRequest request) {
        DetallePedidoResponse creado = detallePedidoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody DetallePedidoRequest request) {
        return ResponseEntity.ok(detallePedidoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        detallePedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
