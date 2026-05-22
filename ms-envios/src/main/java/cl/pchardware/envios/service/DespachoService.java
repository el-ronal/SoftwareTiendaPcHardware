package cl.pchardware.envios.service;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import cl.pchardware.envios.mapper.DespachoMapper;
import cl.pchardware.envios.model.Courier;
import cl.pchardware.envios.model.Despacho;
import cl.pchardware.envios.model.DireccionEnvio;
import cl.pchardware.envios.repository.CourierRepository;
import cl.pchardware.envios.repository.DespachoRepository;
import cl.pchardware.envios.repository.DireccionEnvioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la gestión logística de los Despachos:
 * - Valida la unicidad del código de seguimiento en tiempo real.
 * - Resuelve y vincula físicamente las entidades complejas de Courier y Dirección de Envío.
 */
@Service
@RequiredArgsConstructor
public class DespachoService {

    private final DespachoRepository despachoRepository;
    private final CourierRepository courierRepository;
    private final DireccionEnvioRepository direccionEnvioRepository;
    private final DespachoMapper despachoMapper;

    public List<DespachoResponse> findAll() {
        return despachoMapper.toResponseList(despachoRepository.findAll());
    }

    public DespachoResponse findById(Integer id) {
        return despachoMapper.toResponse(getDespachoById(Objects.requireNonNull(id, "id")));
    }

    public DespachoResponse findByCodigoSeguimiento(String codigoSeguimiento) {
        return despachoMapper.toResponse(getDespachoByCodigoSeguimiento(Objects.requireNonNull(codigoSeguimiento, "codigoSeguimiento")));
    }

    @Transactional
    public DespachoResponse create(DespachoRequest request) {
        if (request.getCodigoSeguimiento() != null && !request.getCodigoSeguimiento().isBlank()) {
            validateCodigoSeguimientoUnico(request.getCodigoSeguimiento());
        }
        
        Integer idDireccion = Objects.requireNonNull(request.getIdDireccion(), "idDireccion");
        Integer idCourier = Objects.requireNonNull(request.getIdCourier(), "idCourier");
        DireccionEnvio direccion = getDireccionEnvioById(idDireccion);
        Courier courier = getCourierById(idCourier);
        
        Despacho despacho = Objects.requireNonNull(new Despacho(), "despacho");
        despachoMapper.updateEntity(request, despacho);
        despacho.setDireccionEnvio(direccion);
        despacho.setCourier(courier);
        
        return despachoMapper.toResponse(despachoRepository.save(Objects.requireNonNull(despacho, "despacho")));
    }

    @Transactional
    public DespachoResponse update(Integer id, DespachoRequest request) {
        Despacho despacho = getDespachoById(id);
        
        if (request.getCodigoSeguimiento() != null && !request.getCodigoSeguimiento().isBlank() 
                && !checkMismoCodigoSeguimiento(id, request.getCodigoSeguimiento())) {
            validateCodigoSeguimientoUnico(request.getCodigoSeguimiento());
        }
        
        Integer idDireccion = Objects.requireNonNull(request.getIdDireccion(), "idDireccion");
        Integer idCourier = Objects.requireNonNull(request.getIdCourier(), "idCourier");
        DireccionEnvio direccion = getDireccionEnvioById(idDireccion);
        Courier courier = getCourierById(idCourier);
        
        despachoMapper.updateEntity(request, despacho);
        despacho.setDireccionEnvio(direccion);
        despacho.setCourier(courier);
        
        return despachoMapper.toResponse(despachoRepository.save(Objects.requireNonNull(despacho, "despacho")));
    }

    @Transactional
    public void deleteById(Integer id) {
        Despacho despacho = getDespachoById(id);
        despachoRepository.delete(Objects.requireNonNull(despacho, "despacho"));
    }

    private void validateCodigoSeguimientoUnico(String codigoSeguimiento) {
        despachoRepository.findByCodigoSeguimiento(codigoSeguimiento).ifPresent(d -> {
            throw new DuplicateResourceException("Un Despacho", "Código Seguimiento", codigoSeguimiento, d.getEstadoLogistico().toString());
        });
    }

    private Despacho getDespachoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return despachoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despacho", "ID", id));
    }

    private Despacho getDespachoByCodigoSeguimiento(String codigoSeguimiento) {
        return despachoRepository.findByCodigoSeguimiento(codigoSeguimiento)
                .orElseThrow(() -> new EntityNotFoundException("Despacho", "Código Seguimiento", codigoSeguimiento));
    }

    private DireccionEnvio getDireccionEnvioById(Integer id) {
        Objects.requireNonNull(id, "id");
        return direccionEnvioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección de Envío", "ID", id));
    }

    private Courier getCourierById(Integer id) {
        Objects.requireNonNull(id, "id");
        return courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courier", "ID", id));
    }

    private boolean checkMismoCodigoSeguimiento(Integer id, String codigoSeguimiento) {
        Despacho despacho = getDespachoById(id);
        return despacho.getCodigoSeguimiento() != null && despacho.getCodigoSeguimiento().equalsIgnoreCase(codigoSeguimiento);
    }
}
