package cl.pchardware.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> findAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @GetMapping("/inventario/{idInventario}")
    public ResponseEntity<List<MovimientoResponse>> findByInventario(@PathVariable Integer idInventario) {
        return ResponseEntity.ok(movimientoService.findByInventario(idInventario));
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> create(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.create(request));
    }
}
