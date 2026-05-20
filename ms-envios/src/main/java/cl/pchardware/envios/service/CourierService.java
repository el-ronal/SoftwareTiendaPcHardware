package cl.pchardware.envios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import cl.pchardware.envios.mapper.CourierMapper;
import cl.pchardware.envios.model.Courier;
import cl.pchardware.envios.repository.CourierRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de los Couriers (Transportistas):
 * - Gestiona operaciones CRUD, validaciones de unicidad del código de courier.
 * - Controla la integridad referencial impidiendo la eliminación si posee despachos asignados.
 */
@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;

    public List<CourierResponse> findAll() {
        return courierMapper.toResponseList(courierRepository.findAll());
    }

    public CourierResponse findById(Integer id) {
        return courierMapper.toResponse(getCourierById(Objects.requireNonNull(id, "id")));
    }

    public CourierResponse findByCodigo(String codigo) {
        return courierMapper.toResponse(getCourierByCodigo(Objects.requireNonNull(codigo, "codigo")));
    }

    @Transactional
    public CourierResponse create(CourierRequest request) {
        String codigo = Objects.requireNonNull(request.getCodigo(), "codigo");
        validateCodigoUnico(codigo);
        Courier courier = Objects.requireNonNull(new Courier(), "courier");
        courierMapper.updateEntity(request, courier);
        return courierMapper.toResponse(courierRepository.save(Objects.requireNonNull(courier, "courier")));
    }

    @Transactional
    public CourierResponse update(Integer id, CourierRequest request) {
        String codigo = Objects.requireNonNull(request.getCodigo(), "codigo");
        if (!checkMismoCodigo(id, codigo)) {
            validateCodigoUnico(codigo);
        }
        Courier courier = getCourierById(id);
        courierMapper.updateEntity(request, courier);
        return courierMapper.toResponse(courierRepository.save(Objects.requireNonNull(courier, "courier")));
    }

    @Transactional
    public void deleteById(Integer id) {
        Courier courier = getCourierById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (courier.getDespachos() != null && !courier.getDespachos().isEmpty()) {
            tablasAsociadas.add("Despachos");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Courier", id, String.join(", ", tablasAsociadas));
        }
        
        courierRepository.delete(Objects.requireNonNull(courier, "courier"));
    }

    private void validateCodigoUnico(String codigo) {
        courierRepository.findByCodigo(codigo).ifPresent(c -> {
            throw new DuplicateResourceException("Un Courier", "Código", codigo, c.getNombreEmpresa());
        });
    }

    private Courier getCourierById(Integer id) {
        Objects.requireNonNull(id, "id");
        return courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courier", "ID", id));
    }

    private Courier getCourierByCodigo(String codigo) {
        Objects.requireNonNull(codigo, "codigo");
        return courierRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Courier", "Código", codigo));
    }

    private boolean checkMismoCodigo(Integer id, String codigo) {
        Courier courier = getCourierById(id);
        return codigo.equalsIgnoreCase(courier.getCodigo());
    }
}
