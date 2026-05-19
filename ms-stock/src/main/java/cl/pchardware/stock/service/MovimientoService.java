package cl.pchardware.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.mapper.MovimientoMapper;
import cl.pchardware.stock.model.Inventario;
import cl.pchardware.stock.model.Movimiento;
import cl.pchardware.stock.model.TipoMovimiento;
import cl.pchardware.stock.repository.InventarioRepository;
import cl.pchardware.stock.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;
    private final MovimientoMapper movimientoMapper;

    @Transactional(readOnly = true)
    public List<MovimientoResponse> findAll() {
        return movimientoMapper.toResponseList(movimientoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public MovimientoResponse findById(Integer id) {
        return movimientoMapper.toResponse(getMovimientoById(id));
    }

    @Transactional(readOnly = true)
    public List<MovimientoResponse> findByInventario(Integer idInventario) {
        return movimientoMapper.toResponseList(movimientoRepository.findByInventario_IdInventario(idInventario));
    }

    @Transactional
    public MovimientoResponse create(MovimientoRequest request) {
        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new EntityNotFoundException("Inventario", "ID", request.getIdInventario()));
        Movimiento movimiento = movimientoMapper.toEntity(request);
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento(TipoMovimiento.valueOf(request.getTipoMovimiento()));

        // Ajustar la cantidad del inventario según el tipo de movimiento
        int variacion = request.getCantidadVariacion();
        int nuevaCantidad = inventario.getCantidad() + variacion;
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException(
                "La variación dejaría el inventario en negativo. Cantidad actual: " + inventario.getCantidad());
        }
        inventario.setCantidad(nuevaCantidad);
        inventarioRepository.save(inventario);
        return movimientoMapper.toResponse(movimientoRepository.save(movimiento));
    }

    private Movimiento getMovimientoById(Integer id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento", "ID", id));
    }
}
