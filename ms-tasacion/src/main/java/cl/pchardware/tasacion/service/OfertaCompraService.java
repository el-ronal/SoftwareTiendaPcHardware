package cl.pchardware.tasacion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.tasacion.dto.OfertaCompraRequest;
import cl.pchardware.tasacion.dto.OfertaCompraResponse;
import cl.pchardware.tasacion.mapper.OfertaCompraMapper;
import cl.pchardware.tasacion.model.OfertaCompra;
import cl.pchardware.tasacion.repository.OfertaCompraRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null") // Mantenemos tu IDE feliz sin el analizador estricto de nulos
public class OfertaCompraService {

    private final OfertaCompraRepository ofertaRepository;
    private final OfertaCompraMapper ofertaMapper;

    @Transactional(readOnly = true)
    public List<OfertaCompraResponse> findAll() {
        List<OfertaCompra> ofertas = ofertaRepository.findAll();
        return ofertaMapper.toResponseList(ofertas);
    }

    @Transactional(readOnly = true)
    public OfertaCompraResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        OfertaCompra oferta = getOfertaById(id);
        return ofertaMapper.toResponse(oferta);
    }

    @Transactional
    public OfertaCompraResponse create(OfertaCompraRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        
        // Validación de restricción única: 1 Evaluación = 1 Oferta
        if (ofertaRepository.existsByEvaluacionTecnica_IdEvaluacion(request.getIdEvaluacion())) {
            throw new DuplicateResourceException(
                "OfertaCompra", 
                "idEvaluacion", 
                request.getIdEvaluacion().toString(), 
                "Ya existe una oferta generada para esta evaluación técnica"
            );
        }

        OfertaCompra entity = ofertaMapper.toEntity(request);
        OfertaCompra saved = ofertaRepository.save(entity);

        return ofertaMapper.toResponse(saved);
    }

    @Transactional
    public OfertaCompraResponse update(Integer id, OfertaCompraRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }

        OfertaCompra oferta = getOfertaById(id);

        // Si por alguna razón intentan cambiar la evaluación asignada, validamos que no colisione
        ofertaRepository.findByEvaluacionTecnica_IdEvaluacion(request.getIdEvaluacion())
                .filter(o -> !o.getIdOferta().equals(id)) // Ignoramos si es la misma oferta que estamos actualizando
                .ifPresent(o -> {
                    throw new DuplicateResourceException(
                        "OfertaCompra", 
                        "idEvaluacion", 
                        request.getIdEvaluacion().toString(), 
                        "Ya existe otra oferta asociada a esta evaluación técnica"
                    );
                });

        ofertaMapper.updateEntity(request, oferta);
        OfertaCompra saved = ofertaRepository.save(oferta);

        return ofertaMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        OfertaCompra oferta = getOfertaById(id);
        ofertaRepository.delete(oferta);
    }

    private OfertaCompra getOfertaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OfertaCompra", "ID", id));
    }
}