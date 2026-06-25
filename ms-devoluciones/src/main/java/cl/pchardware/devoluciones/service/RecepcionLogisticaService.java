package cl.pchardware.devoluciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.devoluciones.dto.RecepcionLogisticaRequest;
import cl.pchardware.devoluciones.dto.RecepcionLogisticaResponse;
import cl.pchardware.devoluciones.mapper.RecepcionLogisticaMapper;
import cl.pchardware.devoluciones.model.RecepcionLogistica;
import cl.pchardware.devoluciones.repository.RecepcionLogisticaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecepcionLogisticaService {

    private final RecepcionLogisticaRepository recepcionRepository;
    private final RecepcionLogisticaMapper recepcionMapper;

    @Transactional(readOnly = true)
    public List<RecepcionLogisticaResponse> findAll() {

        return recepcionMapper.toResponseList(
                recepcionRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public RecepcionLogisticaResponse findById(Integer id) {

        return recepcionMapper.toResponse(
                getRecepcionById(id)
        );
    }

    @Transactional(readOnly = true)
    public RecepcionLogisticaResponse findByDevolucion(
            Integer idDevolucion
    ) {

        RecepcionLogistica recepcion = recepcionRepository
                .findBySolicitudDevolucion_IdDevolucion(idDevolucion)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "RecepcionLogistica",
                                "idDevolucion",
                                idDevolucion
                        )
                );

        return recepcionMapper.toResponse(recepcion);
    }

    @Transactional
    public RecepcionLogisticaResponse create(
            RecepcionLogisticaRequest request
    ) {

        RecepcionLogistica recepcion =
                recepcionMapper.toEntity(request);

        return recepcionMapper.toResponse(
                recepcionRepository.save(recepcion)
        );
    }

    @Transactional
    public RecepcionLogisticaResponse update(
            Integer id,
            RecepcionLogisticaRequest request
    ) {

        RecepcionLogistica recepcion =
                getRecepcionById(id);

        recepcionMapper.updateEntity(request, recepcion);

        return recepcionMapper.toResponse(
                recepcionRepository.save(recepcion)
        );
    }

    @Transactional
    public void deleteById(Integer id) {

        recepcionRepository.delete(
                getRecepcionById(id)
        );
    }

    private RecepcionLogistica getRecepcionById(Integer id) {

        return recepcionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "RecepcionLogistica",
                                "ID",
                                id
                        )
                );
    }
}