package cl.pchardware.stock.service;

import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.mapper.MovimientoMapper;
import cl.pchardware.stock.model.Inventario;
import cl.pchardware.stock.model.Movimiento;
import cl.pchardware.stock.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final InventarioService inventarioService;

    public List<MovimientoResponse> getHistorialByInventario(Long idInventario) {
        return movimientoMapper.toResponseList(movimientoRepository.findByInventarioIdInventario(idInventario));
    }

    @Transactional
    public MovimientoResponse registrarMovimiento(MovimientoRequest request) {
        // Validamos que el 0 no esté permitido, como dice tu Constraint SQL
        if (request.getCantidadVariacion() == 0) {
            throw new IllegalArgumentException("La variación no puede ser cero.");
        }

        Inventario inventario = inventarioService.getInventarioById(request.getIdInventario());

        // Lógica Core de Stock
        int nuevaCantidad = inventario.getCantidad() + request.getCantidadVariacion();
        
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("Stock insuficiente para realizar esta salida.");
        }
        
        // Actualizamos la cantidad en memoria (Hibernate lo guardará por el @Transactional)
        inventario.setCantidad(nuevaCantidad);

        Movimiento movimiento = movimientoMapper.toEntity(request);
        movimiento.setInventario(inventario);

        return movimientoMapper.toResponse(movimientoRepository.save(movimiento));
    }
}