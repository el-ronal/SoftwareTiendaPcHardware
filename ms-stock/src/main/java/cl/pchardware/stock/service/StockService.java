package cl.pchardware.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.pchardware.dto.InventarioRequest;
import cl.pchardware.dto.InventarioResponse;
import cl.pchardware.dto.MovimientoRequest;
import cl.pchardware.dto.MovimientoResponse;
import cl.pchardware.mapper.InventarioMapper;
import cl.pchardware.mapper.MovimientoMapper;
import cl.pchardware.model.Bodega;
import cl.pchardware.model.Inventario;
import cl.pchardware.model.Movimiento;
import cl.pchardware.repository.BodegaRepository;
import cl.pchardware.repository.InventarioRepository;
import cl.pchardware.repository.MovimientoRepository;
import cl.triskeledu.common.exception.DuplicateResourceException;
import cl.triskeledu.common.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de gestionar el inventario físico y sus movimientos:
 * - Asegura que no existan duplicados de (Bodega + SKU).
 * - Registra los movimientos y actualiza el stock automáticamente.
 * - Garantiza que el stock nunca sea negativo mediante validación de negocio.
 */
@Service
@RequiredArgsConstructor
public class StockService {

    private final InventarioRepository inventarioRepository;
    private final BodegaRepository bodegaRepository;
    private final MovimientoRepository movimientoRepository;
    private final InventarioMapper inventarioMapper;
    private final MovimientoMapper movimientoMapper;

    public List<InventarioResponse> findBySku(String skuProducto) {
        return inventarioMapper.toResponseList(inventarioRepository.findBySkuProducto(skuProducto));
    }

    @Transactional
    public InventarioResponse inicializarInventario(InventarioRequest request) {
        if (inventarioRepository.existsByBodegaCodigoAndSkuProducto(request.getCodigoBodega(), request.getSkuProducto())) {
            throw new DuplicateResourceException("Un registro de inventario", "SKU en bodega", request.getSkuProducto(), request.getCodigoBodega());
        }

        Bodega bodega = bodegaRepository.findByCodigo(request.getCodigoBodega())
                .orElseThrow(() -> new EntityNotFoundException("Bodegas", "código", request.getCodigoBodega()));

        Inventario inventario = inventarioMapper.toEntity(request);
        inventario.setBodega(bodega);
        
        Inventario guardado = inventarioRepository.save(inventario);

        // Si se inicializa con cantidad > 0, creamos el movimiento inicial por trazabilidad
        if (request.getCantidad() > 0) {
            registrarMovimientoInterno(guardado, "ENTRADA", request.getCantidad());
        }

        return inventarioMapper.toResponse(guardado);
    }

    @Transactional
    public MovimientoResponse registrarMovimiento(Long idInventario, MovimientoRequest request) {
        if (request.getCantidadVariacion() == 0) {
            throw new IllegalArgumentException("La variación de cantidad no puede ser cero.");
        }

        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new EntityNotFoundException("Inventario", "ID", idInventario));

        // Validación de negocio: Evitar stock negativo
        int nuevoStock = inventario.getCantidad() + request.getCantidadVariacion();
        if (nuevoStock < 0) {
            throw new IllegalStateException("Operación rechazada: El stock resultante no puede ser negativo.");
        }

        inventario.setCantidad(nuevoStock);
        inventarioRepository.save(inventario);

        return movimientoMapper.toResponse(
            registrarMovimientoInterno(inventario, request.getTipoMovimiento(), request.getCantidadVariacion())
        );
    }

    public List<MovimientoResponse> obtenerHistorialMovimientos(Long idInventario) {
        return movimientoMapper.toResponseList(
            movimientoRepository.findByInventarioIdInventarioOrderByFechaRegistroDesc(idInventario)
        );
    }

    private Movimiento registrarMovimientoInterno(Inventario inventario, String tipoMovimiento, Integer variacion) {
        Movimiento movimiento = new Movimiento();
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setCantidadVariacion(variacion);
        // fechaRegistro se autocompleta por @CreatedDate de JPA Auditing
        return movimientoRepository.save(movimiento);
    }
}