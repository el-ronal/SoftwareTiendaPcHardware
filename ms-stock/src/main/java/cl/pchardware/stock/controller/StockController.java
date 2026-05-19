package cl.pchardware.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    /**
     * Obtiene el stock disponible de un SKU específico en todas las bodegas.
     * GET /api/v1/stock/producto/CPU-AMD-R75800X3D-NUEVO
     */
    @GetMapping("/producto/{sku}")
    public ResponseEntity<List<InventarioResponse>> obtenerStockPorSku(@PathVariable String sku) {
        List<InventarioResponse> inventarios = stockService.findBySku(sku);
        return ResponseEntity.ok(inventarios);
    }

    /**
     * Inicializa el stock de un producto (SKU) dentro de una bodega determinada.
     * POST /api/v1/stock/inicializar
     */
    @PostMapping("/inicializar")
    public ResponseEntity<InventarioResponse> inicializarInventario(@Valid @RequestBody InventarioRequest request) {
        InventarioResponse nuevoInventario = stockService.inicializarInventario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
    }

    /**
     * Registra un nuevo movimiento de stock (ENTRADA, SALIDA, AJUSTE) para un inventario.
     * POST /api/v1/stock/1/movimientos
     */
    @PostMapping("/{idInventario}/movimientos")
    public ResponseEntity<MovimientoResponse> registrarMovimiento(
            @PathVariable Long idInventario,
            @Valid @RequestBody MovimientoRequest request) {
        MovimientoResponse nuevoMovimiento = stockService.registrarMovimiento(idInventario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovimiento);
    }

    /**
     * Obtiene el historial completo de movimientos de un ítem de inventario (Auditoría).
     * GET /api/v1/stock/1/movimientos
     */
    @GetMapping("/{idInventario}/movimientos")
    public ResponseEntity<List<MovimientoResponse>> obtenerHistorialMovimientos(@PathVariable Long idInventario) {
        List<MovimientoResponse> historial = stockService.obtenerHistorialMovimientos(idInventario);
        return ResponseEntity.ok(historial);
    }
}