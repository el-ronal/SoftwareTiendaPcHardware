package cl.pchardware.pagos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.pagos.dto.TransaccionRequest;
import cl.pchardware.pagos.dto.TransaccionResponse;
import cl.pchardware.pagos.mapper.TransaccionMapper;
import cl.pchardware.pagos.model.MetodoPago;
import cl.pchardware.pagos.model.Transaccion;
import cl.pchardware.pagos.repository.MetodoPagoRepository;
import cl.pchardware.pagos.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de gestionar el ciclo de vida de las Transacciones financieras.
 * Asocia de forma segura el método de pago validando su existencia.
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
        Integer idMetodo = Objects.requireNonNull(request.getIdMetodo(), "idMetodo");
        MetodoPago metodoPago = getMetodoPagoById(idMetodo);
        Transaccion transaccion = Objects.requireNonNull(new Transaccion(), "transaccion");
        transaccionMapper.updateEntity(request, transaccion);
        transaccion.setMetodoPago(metodoPago);
        return transaccionMapper.toResponse(transaccionRepository.save(Objects.requireNonNull(transaccion, "transaccion")));
    }

    @Transactional
    public TransaccionResponse update(Integer id, TransaccionRequest request) {
        Transaccion transaccion = getTransaccionById(id);
        Integer idMetodo = Objects.requireNonNull(request.getIdMetodo(), "idMetodo");
        MetodoPago metodoPago = getMetodoPagoById(idMetodo);
        
        transaccionMapper.updateEntity(request, transaccion);
        transaccion.setMetodoPago(metodoPago);
        return transaccionMapper.toResponse(transaccionRepository.save(Objects.requireNonNull(transaccion, "transaccion")));
    }

    @Transactional
    public void deleteById(Integer id) {
        Transaccion transaccion = getTransaccionById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (transaccion.getReembolso() != null) {
            tablasAsociadas.add("Reembolsos");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Transacción", id, String.join(", ", tablasAsociadas));
        }
        
        transaccionRepository.delete(Objects.requireNonNull(transaccion, "transaccion"));
    }

    private Transaccion getTransaccionById(Integer id) {
        Objects.requireNonNull(id, "id");
        return transaccionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transacción", "ID", id));
    }

    private MetodoPago getMetodoPagoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return metodoPagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de Pago", "ID", id));
    }
}
