package cl.pchardware.stock.controller;

import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping("/inventario/{idInventario}")
    public ResponseEntity<List<MovimientoResponse>> getHistorial(@PathVariable Long idInventario) {
        return ResponseEntity.ok(movimientoService.getHistorialByInventario(idInventario));
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> registrarMovimiento(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrarMovimiento(request));
    }
}