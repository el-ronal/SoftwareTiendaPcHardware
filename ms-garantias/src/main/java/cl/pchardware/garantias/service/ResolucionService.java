package cl.pchardware.garantias.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.garantias.dto.ResolucionRequest;
import cl.pchardware.garantias.dto.ResolucionResponse;
import cl.pchardware.garantias.mapper.ResolucionMapper;
import cl.pchardware.garantias.model.Resolucion;
import cl.pchardware.garantias.repository.ResolucionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResolucionService {

    private final ResolucionRepository resolucionRepository;
    private final ResolucionMapper resolucionMapper;

    @Transactional(readOnly = true)
    public List<ResolucionResponse> findAll() {

        return resolucionMapper.toResponseList(
                resolucionRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public ResolucionResponse findById(Integer id) {

        return resolucionMapper.toResponse(
                getResolucionById(id)
        );
    }

    @Transactional(readOnly = true)
    public ResolucionResponse findByInspeccion(
            Integer idInspeccion
    ) {

        Resolucion resolucion = resolucionRepository
                .findByInspeccionTecnica_IdInspeccion(idInspeccion)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Resolucion",
                                "idInspeccion",
                                idInspeccion
                        )
                );

        return resolucionMapper.toResponse(resolucion);
    }

    @Transactional
    public ResolucionResponse create(
            ResolucionRequest request
    ) {

        Resolucion resolucion =
                resolucionMapper.toEntity(request);

        return resolucionMapper.toResponse(
                resolucionRepository.save(resolucion)
        );
    }

    @Transactional
    public ResolucionResponse update(
            Integer id,
            ResolucionRequest request
    ) {

        Resolucion resolucion =
                getResolucionById(id);

        resolucionMapper.updateEntity(request, resolucion);

        return resolucionMapper.toResponse(
                resolucionRepository.save(resolucion)
        );
    }

    @Transactional
    public void deleteById(Integer id) {

        resolucionRepository.delete(
                getResolucionById(id)
        );
    }

    private Resolucion getResolucionById(Integer id) {

        return resolucionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Resolucion",
                                "ID",
                                id
                        )
                );
    }
}