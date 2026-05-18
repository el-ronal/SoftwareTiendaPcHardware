package cl.pchardware.pagos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.pchardware.pagos.dto.TransaccionRequest;
import cl.pchardware.pagos.dto.TransaccionResponse;
import cl.pchardware.pagos.mapper.TransaccionMapper;
import cl.pchardware.pagos.model.MetodoPago;
import cl.pchardware.pagos.model.Transaccion;
import cl.pchardware.pagos.repository.MetodoPagoRepository;
import cl.pchardware.pagos.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de transacciones financieras.
 */
@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final TransaccionMapper transaccionMapper;

    public List<TransaccionResponse> findAll() {
        return transaccionMapper.toResponseList(transaccionRepository.findAll());
    }

    public TransaccionResponse findById(Integer id) {
        return transaccionMapper.toResponse(getTransaccionById(id));
    }

    @Transactional
    public TransaccionResponse create(TransaccionRequest request) {
        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodo())
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Método de Pago no encontrado con ID: " + request.getIdMetodo()));
        
        Transaccion transaccion = new Transaccion();
        transaccionMapper.updateEntity(request, transaccion);
        transaccion.setMetodoPago(metodoPago);
        
        return transaccionMapper.toResponse(transaccionRepository.save(transaccion));
    }

    @Transactional
    public TransaccionResponse update(Integer id, TransaccionRequest request) {
        Transaccion transaccion = getTransaccionById(id);
        
        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodo())
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Método de Pago no encontrado con ID: " + request.getIdMetodo()));
        
        transaccionMapper.updateEntity(request, transaccion);
        transaccion.setMetodoPago(metodoPago);
        
        return transaccionMapper.toResponse(transaccionRepository.save(transaccion));
    }

    @Transactional
    public void deleteById(Integer id) {
        Transaccion transaccion = getTransaccionById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (transaccion.getReembolso() != null) {
            tablasAsociadas.add("Reembolso");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new RuntimeException("ReferentialIntegrityException: No se puede eliminar la transacción con ID " + id + " porque tiene un registro asociado en: " + String.join(", ", tablasAsociadas));
        }
        transaccionRepository.delete(transaccion);
    }

    private Transaccion getTransaccionById(Integer id) {
        return transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Transacción no encontrada con ID: " + id));
    }
}
