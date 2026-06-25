package cl.pchardware.garantias.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.garantias.dto.InspeccionTecnicaRequest;
import cl.pchardware.garantias.dto.InspeccionTecnicaResponse;
import cl.pchardware.garantias.mapper.InspeccionTecnicaMapper;
import cl.pchardware.garantias.model.InspeccionTecnica;
import cl.pchardware.garantias.repository.InspeccionTecnicaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InspeccionTecnicaService {

    private final InspeccionTecnicaRepository inspeccionRepository;
    private final InspeccionTecnicaMapper inspeccionMapper;

    @Transactional(readOnly = true)
    public List<InspeccionTecnicaResponse> findAll() {

        return inspeccionMapper.toResponseList(
                inspeccionRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public InspeccionTecnicaResponse findById(Integer id) {

        return inspeccionMapper.toResponse(
                getInspeccionById(id)
        );
    }

    @Transactional(readOnly = true)
    public InspeccionTecnicaResponse findByTicket(
            Integer idTicket
    ) {

        InspeccionTecnica inspeccion = inspeccionRepository
                .findByTicketGarantia_IdTicket(idTicket)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "InspeccionTecnica",
                                "idTicket",
                                idTicket
                        )
                );

        return inspeccionMapper.toResponse(inspeccion);
    }

    @Transactional
    public InspeccionTecnicaResponse create(
            InspeccionTecnicaRequest request
    ) {

        InspeccionTecnica inspeccion =
                inspeccionMapper.toEntity(request);

        return inspeccionMapper.toResponse(
                inspeccionRepository.save(inspeccion)
        );
    }

    @Transactional
    public InspeccionTecnicaResponse update(
            Integer id,
            InspeccionTecnicaRequest request
    ) {

        InspeccionTecnica inspeccion =
                getInspeccionById(id);

        inspeccionMapper.updateEntity(request, inspeccion);

        return inspeccionMapper.toResponse(
                inspeccionRepository.save(inspeccion)
        );
    }

    @Transactional
    public void deleteById(Integer id) {

        inspeccionRepository.delete(
                getInspeccionById(id)
        );
    }

    private InspeccionTecnica getInspeccionById(Integer id) {

        return inspeccionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "InspeccionTecnica",
                                "ID",
                                id
                        )
                );
    }
}