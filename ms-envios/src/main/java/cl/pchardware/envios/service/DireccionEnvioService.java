package cl.pchardware.envios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.envios.dto.DireccionEnvioRequest;
import cl.pchardware.envios.dto.DireccionEnvioResponse;
import cl.pchardware.envios.mapper.DireccionEnvioMapper;
import cl.pchardware.envios.model.DireccionEnvio;
import cl.pchardware.envios.repository.DireccionEnvioRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de las Reglas de negocio de Direcciones de Envío:
 * - Valida la unicidad del idPedido (una dirección única por pedido).
 * - Proteje la integridad referencial impidiendo eliminar direcciones con despachos en tránsito/historial.
 */
@Service
@RequiredArgsConstructor
public class DireccionEnvioService {

    private final DireccionEnvioRepository direccionEnvioRepository;
    private final DireccionEnvioMapper direccionEnvioMapper;

    public List<DireccionEnvioResponse> findAll() {
        return direccionEnvioMapper.toResponseList(direccionEnvioRepository.findAll());
    }

    public DireccionEnvioResponse findById(Integer id) {
        return direccionEnvioMapper.toResponse(getDireccionEnvioById(Objects.requireNonNull(id, "id")));
    }

    public DireccionEnvioResponse findByIdPedido(Integer idPedido) {
        return direccionEnvioMapper.toResponse(getDireccionEnvioByIdPedido(Objects.requireNonNull(idPedido, "idPedido")));
    }

    @Transactional
    public DireccionEnvioResponse create(DireccionEnvioRequest request) {
        Integer idPedido = Objects.requireNonNull(request.getIdPedido(), "idPedido");
        validateIdPedidoUnico(idPedido);
        DireccionEnvio direccionEnvio = Objects.requireNonNull(new DireccionEnvio(), "direccionEnvio");
        direccionEnvioMapper.updateEntity(request, direccionEnvio);
        return direccionEnvioMapper.toResponse(direccionEnvioRepository.save(Objects.requireNonNull(direccionEnvio, "direccionEnvio")));
    }

    @Transactional
    public DireccionEnvioResponse update(Integer id, DireccionEnvioRequest request) {
        Integer idPedido = Objects.requireNonNull(request.getIdPedido(), "idPedido");
        if (!checkMismoIdPedido(id, idPedido)) {
            validateIdPedidoUnico(idPedido);
        }
        DireccionEnvio direccionEnvio = getDireccionEnvioById(id);
        direccionEnvioMapper.updateEntity(request, direccionEnvio);
        return direccionEnvioMapper.toResponse(direccionEnvioRepository.save(Objects.requireNonNull(direccionEnvio, "direccionEnvio")));
    }

    @Transactional
    public void deleteById(Integer id) {
        DireccionEnvio direccionEnvio = getDireccionEnvioById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (direccionEnvio.getDespachos() != null && !direccionEnvio.getDespachos().isEmpty()) {
            tablasAsociadas.add("Despachos");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Dirección de Envío", id, String.join(", ", tablasAsociadas));
        }
        
        direccionEnvioRepository.delete(Objects.requireNonNull(direccionEnvio, "direccionEnvio"));
    }

    private void validateIdPedidoUnico(Integer idPedido) {
        Objects.requireNonNull(idPedido, "idPedido");
        direccionEnvioRepository.findByIdPedido(idPedido).ifPresent(d -> {
            throw new DuplicateResourceException("Una Dirección de Envío", "ID Pedido", idPedido.toString(), d.getCalleNumero());
        });
    }

    private DireccionEnvio getDireccionEnvioById(Integer id) {
        Objects.requireNonNull(id, "id");
        return direccionEnvioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección de Envío", "ID", id));
    }

    private DireccionEnvio getDireccionEnvioByIdPedido(Integer idPedido) {
        Objects.requireNonNull(idPedido, "idPedido");
        return direccionEnvioRepository.findByIdPedido(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Dirección de Envío", "ID Pedido", idPedido));
    }

    private boolean checkMismoIdPedido(Integer id, Integer idPedido) {
        DireccionEnvio direccionEnvio = getDireccionEnvioById(id);
        Objects.requireNonNull(idPedido, "idPedido");
        return idPedido.equals(direccionEnvio.getIdPedido());
    }
}
