package cl.pchardware.armado.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.armado.dto.TecnicoArmadoRequest;
import cl.pchardware.armado.dto.TecnicoArmadoResponse;
import cl.pchardware.armado.mapper.TecnicoArmadoMapper;
import cl.pchardware.armado.model.TecnicoArmado;
import cl.pchardware.armado.repository.TecnicoArmadoRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TecnicoArmadoService {

    private final TecnicoArmadoRepository tecnicoRepository;
    private final TecnicoArmadoMapper tecnicoMapper;

    @Transactional(readOnly = true)
    public List<TecnicoArmadoResponse> findAll() {
        return tecnicoMapper.toResponseList(tecnicoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TecnicoArmadoResponse findById(Integer id) {
        return tecnicoMapper.toResponse(getTecnicoById(id));
    }

    @Transactional
    public TecnicoArmadoResponse create(TecnicoArmadoRequest request) {
        tecnicoRepository.findByIdUsuario(request.getIdUsuario())
                .ifPresent(t -> { throw new DuplicateResourceException("TecnicoArmado", "idUsuario",
                        request.getIdUsuario(), "usuario " + request.getIdUsuario()); });
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnicoMapper.toEntity(request)));
    }

    @Transactional
    public TecnicoArmadoResponse update(Integer id, TecnicoArmadoRequest request) {
        TecnicoArmado tecnico = getTecnicoById(id);
        tecnicoMapper.updateEntity(request, tecnico);
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnico));
    }

    @Transactional
    public void deleteById(Integer id) {
        TecnicoArmado tecnico = getTecnicoById(id);
        if (tecnico.getOrdenes() != null && !tecnico.getOrdenes().isEmpty()) {
            throw new ReferentialIntegrityException("TecnicoArmado", id, "OrdenesEnsamble");
        }
        tecnicoRepository.delete(tecnico);
    }

    private TecnicoArmado getTecnicoById(Integer id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TecnicoArmado", "ID", id));
    }
}
