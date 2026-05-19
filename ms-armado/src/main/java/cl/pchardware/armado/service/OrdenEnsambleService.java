package cl.pchardware.armado.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.armado.dto.OrdenEnsambleRequest;
import cl.pchardware.armado.dto.OrdenEnsambleResponse;
import cl.pchardware.armado.mapper.OrdenEnsambleMapper;
import cl.pchardware.armado.model.OrdenEnsamble;
import cl.pchardware.armado.model.TecnicoArmado;
import cl.pchardware.armado.repository.OrdenEnsambleRepository;
import cl.pchardware.armado.repository.TecnicoArmadoRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenEnsambleService {

    private final OrdenEnsambleRepository ordenRepository;
    private final TecnicoArmadoRepository tecnicoRepository;
    private final OrdenEnsambleMapper ordenMapper;

    @Transactional(readOnly = true)
    public List<OrdenEnsambleResponse> findAll() {
        return ordenMapper.toResponseList(ordenRepository.findAll());
    }

    @Transactional(readOnly = true)
    public OrdenEnsambleResponse findById(Integer id) {
        return ordenMapper.toResponse(getOrdenById(id));
    }

    @Transactional(readOnly = true)
    public List<OrdenEnsambleResponse> findByTecnico(Integer idTecnico) {
        return ordenMapper.toResponseList(ordenRepository.findByTecnico_IdTecnico(idTecnico));
    }

    @Transactional
    public OrdenEnsambleResponse create(OrdenEnsambleRequest request) {
        ordenRepository.findByIdPedido(request.getIdPedido())
                .ifPresent(o -> { throw new DuplicateResourceException("OrdenEnsamble", "idPedido",
                        request.getIdPedido(), "pedido " + request.getIdPedido()); });
        Integer idTecnico = Objects.requireNonNull(request.getIdTecnico(), "El Id del técnico es obligatorio");
        TecnicoArmado tecnico = tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new EntityNotFoundException("TecnicoArmado", "ID", request.getIdTecnico()));
        OrdenEnsamble orden = ordenMapper.toEntity(request);
        orden.setTecnico(tecnico);
        return ordenMapper.toResponse(ordenRepository.save(orden));
    }

    @Transactional
    public OrdenEnsambleResponse update(Integer id, OrdenEnsambleRequest request) {
        OrdenEnsamble orden = getOrdenById(id);
        Integer idTecnico = Objects.requireNonNull(request.getIdTecnico(), "El Id del técnico es obligatorio");    
        TecnicoArmado tecnico = tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new EntityNotFoundException("TecnicoArmado", "ID", request.getIdTecnico()));
        ordenMapper.updateEntity(request, orden);
        orden.setTecnico(tecnico);
        return ordenMapper.toResponse(ordenRepository.save(orden));
    }

    @Transactional
    public void deleteById(Integer id) {
        OrdenEnsamble orden = Objects.requireNonNull(getOrdenById(id), "No se encontró la orden de ensamble con ID: " + id);
        ordenRepository.delete(orden);
    }

    private OrdenEnsamble getOrdenById(Integer id) {
        Integer idOrden = Objects.requireNonNull(id, "El ID de la orden de ensamble es obligatorio");
        return ordenRepository.findById(idOrden)
                .orElseThrow(() -> new EntityNotFoundException("OrdenEnsamble", "ID", id));
    }
}
