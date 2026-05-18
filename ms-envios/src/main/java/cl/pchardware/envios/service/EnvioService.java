package cl.pchardware.envios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import cl.pchardware.envios.dto.DireccionEnvioRequest;
import cl.pchardware.envios.dto.DireccionEnvioResponse;
import cl.pchardware.envios.mapper.CourierMapper;
import cl.pchardware.envios.mapper.DespachoMapper;
import cl.pchardware.envios.mapper.DireccionEnvioMapper;
import cl.pchardware.envios.model.Courier;
import cl.pchardware.envios.model.Despacho;
import cl.pchardware.envios.model.DireccionEnvio;
import cl.pchardware.envios.repository.CourierRepository;
import cl.pchardware.envios.repository.DespachoRepository;
import cl.pchardware.envios.repository.DireccionEnvioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final CourierRepository courierRepository;
    private final DireccionEnvioRepository direccionEnvioRepository;
    private final DespachoRepository despachoRepository;

    private final CourierMapper courierMapper;
    private final DireccionEnvioMapper direccionEnvioMapper;
    private final DespachoMapper despachoMapper;

    // COURIER OPERATIONS

    public List<CourierResponse> findAllCouriers() {
        return courierMapper.toResponseList(courierRepository.findAll());
    }

    public CourierResponse findCourierById(Integer id) {
        return courierMapper.toResponse(getCourierEntityById(id));
    }

    @Transactional
    public CourierResponse createCourier(CourierRequest request) {
        validateCodigoUnico(request.getCodigo());
        Courier courier = new Courier();
        courierMapper.updateEntity(request, courier);
        return courierMapper.toResponse(courierRepository.save(courier));
    }

    @Transactional
    public CourierResponse updateCourier(Integer id, CourierRequest request) {
        Courier courier = getCourierEntityById(id);
        if (!courier.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validateCodigoUnico(request.getCodigo());
        }
        courierMapper.updateEntity(request, courier);
        return courierMapper.toResponse(courierRepository.save(courier));
    }

    @Transactional
    public void deleteCourierById(Integer id) {
        Courier courier = getCourierEntityById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        if (courier.getDespachos() != null && !courier.getDespachos().isEmpty()) {
            tablasAsociadas.add("Despachos");
        }
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Courier", id, String.join(", ", tablasAsociadas));
        }
        courierRepository.delete(courier);
    }

    private Courier getCourierEntityById(Integer id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couriers", "ID", id));
    }

    private void validateCodigoUnico(String codigo) {
        courierRepository.findAll().stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .ifPresent(c -> {
                    throw new DuplicateResourceException("Un Courier", "Código", codigo, c.getNombreEmpresa());
                });
    }

    // DIRECCION DE ENVIO OPERATIONS

    public List<DireccionEnvioResponse> findAllDirecciones() {
        return direccionEnvioMapper.toResponseList(direccionEnvioRepository.findAll());
    }

    public DireccionEnvioResponse findDireccionById(Integer id) {
        return direccionEnvioMapper.toResponse(getDireccionEntityById(id));
    }

    @Transactional
    public DireccionEnvioResponse createDireccion(DireccionEnvioRequest request) {
        validatePedidoUnico(request.getIdPedido());
        DireccionEnvio direccionEnvio = new DireccionEnvio();
        direccionEnvioMapper.updateEntity(request, direccionEnvio);
        return direccionEnvioMapper.toResponse(direccionEnvioRepository.save(direccionEnvio));
    }

    @Transactional
    public DireccionEnvioResponse updateDireccion(Integer id, DireccionEnvioRequest request) {
        DireccionEnvio direccionEnvio = getDireccionEntityById(id);
        if (!direccionEnvio.getIdPedido().equals(request.getIdPedido())) {
            validatePedidoUnico(request.getIdPedido());
        }
        direccionEnvioMapper.updateEntity(request, direccionEnvio);
        return direccionEnvioMapper.toResponse(direccionEnvioRepository.save(direccionEnvio));
    }

    @Transactional
    public void deleteDireccionById(Integer id) {
        DireccionEnvio direccionEnvio = getDireccionEntityById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        if (direccionEnvio.getDespachos() != null && !direccionEnvio.getDespachos().isEmpty()) {
            tablasAsociadas.add("Despachos");
        }
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("DireccionEnvio", id, String.join(", ", tablasAsociadas));
        }
        direccionEnvioRepository.delete(direccionEnvio);
    }

    private DireccionEnvio getDireccionEntityById(Integer id) {
        return direccionEnvioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Direcciones de Envío", "ID", id));
    }

    private void validatePedidoUnico(Integer idPedido) {
        direccionEnvioRepository.findAll().stream()
                .filter(d -> d.getIdPedido().equals(idPedido))
                .findFirst()
                .ifPresent(d -> {
                    throw new DuplicateResourceException("Una Dirección de Envío", "ID Pedido", idPedido,
                            d.getCalleNumero());
                });
    }

    // DESPACHO OPERATIONS
    public List<DespachoResponse> findAllDespachos() {
        return despachoMapper.toResponseList(despachoRepository.findAll());
    }

    public DespachoResponse findDespachoById(Integer id) {
        return despachoMapper.toResponse(getDespachoEntityById(id));
    }

    @Transactional
    public DespachoResponse createDespacho(DespachoRequest request) {
        if (request.getCodigoSeguimiento() != null && !request.getCodigoSeguimiento().isBlank()) {
            validateCodigoSeguimientoUnico(request.getCodigoSeguimiento());
        }

        Despacho despacho = new Despacho();
        despachoMapper.updateEntity(request, despacho);

        despacho.setCourier(getCourierEntityById(request.getIdCourier()));
        despacho.setDireccionEnvio(getDireccionEntityById(request.getIdDireccion()));
        despacho.setEstadoLogistico(Despacho.EstadoLogistico.valueOf(request.getEstadoLogistico().toUpperCase()));

        return despachoMapper.toResponse(despachoRepository.save(despacho));
    }

    @Transactional
    public DespachoResponse updateDespacho(Integer id, DespachoRequest request) {
        Despacho despacho = getDespachoEntityById(id);

        if (request.getCodigoSeguimiento() != null && !request.getCodigoSeguimiento().isBlank()) {
            if (despacho.getCodigoSeguimiento() == null
                    || !despacho.getCodigoSeguimiento().equalsIgnoreCase(request.getCodigoSeguimiento())) {
                validateCodigoSeguimientoUnico(request.getCodigoSeguimiento());
            }
        }

        despachoMapper.updateEntity(request, despacho);

        despacho.setCourier(getCourierEntityById(request.getIdCourier()));
        despacho.setDireccionEnvio(getDireccionEntityById(request.getIdDireccion()));
        despacho.setEstadoLogistico(Despacho.EstadoLogistico.valueOf(request.getEstadoLogistico().toUpperCase()));

        return despachoMapper.toResponse(despachoRepository.save(despacho));
    }

    @Transactional
    public void deleteDespachoById(Integer id) {
        Despacho despacho = getDespachoEntityById(id);
        despachoRepository.delete(despacho);
    }

    private Despacho getDespachoEntityById(Integer id) {
        return despachoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despachos", "ID", id));
    }

    private void validateCodigoSeguimientoUnico(String codigoSeguimiento) {
        despachoRepository.findAll().stream()
                .filter(d -> d.getCodigoSeguimiento() != null
                        && d.getCodigoSeguimiento().equalsIgnoreCase(codigoSeguimiento))
                .findFirst()
                .ifPresent(d -> {
                    throw new DuplicateResourceException("Un Despacho", "Código Seguimiento", codigoSeguimiento,
                            d.getEstadoLogistico().toString());
                });
    }
}