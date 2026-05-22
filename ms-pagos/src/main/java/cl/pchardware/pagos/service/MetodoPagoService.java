package cl.pchardware.pagos.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.pagos.dto.MetodoPagoRequest;
import cl.pchardware.pagos.dto.MetodoPagoResponse;
import cl.pchardware.pagos.mapper.MetodoPagoMapper;
import cl.pchardware.pagos.model.MetodoPago;
import cl.pchardware.pagos.repository.MetodoPagoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio para los Métodos de Pago:
 * - Valida unicidad del código de la pasarela.
 * - Garantiza reglas de integridad referencial al intentar eliminar.
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

    public MetodoPagoResponse findByCodigo(String codigo) {
        return metodoPagoMapper.toResponse(getMetodoPagoByCodigo(Objects.requireNonNull(codigo, "codigo")));
    }

    @Transactional
    public MetodoPagoResponse create(MetodoPagoRequest request) {
        String codigo = Objects.requireNonNull(request.getCodigo(), "codigo");
        validateCodigoUnico(codigo);
        MetodoPago metodoPago = Objects.requireNonNull(new MetodoPago(), "metodoPago");
        metodoPagoMapper.updateEntity(request, metodoPago);
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(Objects.requireNonNull(metodoPago, "metodoPago")));
    }

    @Transactional
    public MetodoPagoResponse update(Integer id, MetodoPagoRequest request) {
        String codigo = Objects.requireNonNull(request.getCodigo(), "codigo");
        if (!checkMismoCodigo(id, codigo)) {
            validateCodigoUnico(codigo);
        }
        MetodoPago metodoPago = getMetodoPagoById(id);
        metodoPagoMapper.updateEntity(request, metodoPago);
        return metodoPagoMapper.toResponse(metodoPagoRepository.save(Objects.requireNonNull(metodoPago, "metodoPago")));
    }

    @Transactional
    public void deleteById(Integer id) {
        MetodoPago metodoPago = getMetodoPagoById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (metodoPago.getTransacciones() != null && !metodoPago.getTransacciones().isEmpty()) {
            tablasAsociadas.add("Transacciones");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Método de Pago", id, String.join(", ", tablasAsociadas));
        }
        
        metodoPagoRepository.delete(Objects.requireNonNull(metodoPago, "metodoPago"));
    }

    private void validateCodigoUnico(String codigo) {
        metodoPagoRepository.findByCodigo(codigo).ifPresent(m -> {
            throw new DuplicateResourceException("Un Método de Pago", "Código", codigo, m.getNombre());
        });
    }

    private MetodoPago getMetodoPagoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return metodoPagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de Pago", "ID", id));
    }

    private MetodoPago getMetodoPagoByCodigo(String codigo) {
        Objects.requireNonNull(codigo, "codigo");
        return metodoPagoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Método de Pago", "Código", codigo));
    }

    private boolean checkMismoCodigo(Integer id, String codigo) {
        MetodoPago metodoPago = getMetodoPagoById(id);
        return codigo.equalsIgnoreCase(metodoPago.getCodigo());
    }
}
