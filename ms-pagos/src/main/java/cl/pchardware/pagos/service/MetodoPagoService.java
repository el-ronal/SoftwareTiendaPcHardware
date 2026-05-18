package cl.pchardware.pagos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.pchardware.pagos.dto.MetodoPagoRequest;
import cl.pchardware.pagos.dto.MetodoPagoResponse;
import cl.pchardware.pagos.mapper.MetodoPagoMapper;
import cl.pchardware.pagos.model.MetodoPago;
import cl.pchardware.pagos.repository.MetodoPagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de métodos de pago.
 */
@Service
@RequiredArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;
    private final MetodoPagoMapper metodoPagoMapper;

    public List<MetodoPagoResponse> findAll() {
        return metodoPagoMapper.toResponseList(metodoPagoRepository.findAll());
    }

    public MetodoPagoResponse findById(Integer id) {
        return metodoPagoMapper.toResponse(getMetodoPagoById(id));
    }

    @Transactional
    public MetodoPagoResponse create(MetodoPagoRequest request) {
        validateCodigoUnico(request.getCodigo());
        MetodoPago metodoPago = new MetodoPago();
        metodoPagoMapper.updateEntity(request, metodoPago);
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(metodoPago));
    }

    @Transactional
    public MetodoPagoResponse update(Integer id, MetodoPagoRequest request) {
        MetodoPago metodoPago = getMetodoPagoById(id);
        if (!metodoPago.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validateCodigoUnico(request.getCodigo());
        }
        metodoPagoMapper.updateEntity(request, metodoPago);
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(metodoPago));
    }

    @Transactional
    public void deleteById(Integer id) {
        MetodoPago metodoPago = getMetodoPagoById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (metodoPago.getTransacciones() != null && !metodoPago.getTransacciones().isEmpty()) {
            tablasAsociadas.add("Transacciones");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new RuntimeException("ReferentialIntegrityException: No se puede eliminar el método de pago con ID " + id + " porque tiene registros asociados en: " + String.join(", ", tablasAsociadas));
        }
        metodoPagoRepository.delete(metodoPago);
    }

    private MetodoPago getMetodoPagoById(Integer id) {
        return metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Método de Pago no encontrado con ID: " + id));
    }

    private void validateCodigoUnico(String codigo) {
        boolean existe = metodoPagoRepository.findAll().stream()
                .anyMatch(mp -> mp.getCodigo().equalsIgnoreCase(codigo));
        if (existe) {
            throw new RuntimeException("DuplicateResourceException: El código de método de pago '" + codigo + "' ya está registrado.");
        }
    }
}
