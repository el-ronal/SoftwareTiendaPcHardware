package cl.pchardware.devoluciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.devoluciones.dto.NotaCreditoRequest;
import cl.pchardware.devoluciones.dto.NotaCreditoResponse;
import cl.pchardware.devoluciones.mapper.NotaCreditoMapper;
import cl.pchardware.devoluciones.model.NotaCredito;
import cl.pchardware.devoluciones.repository.NotaCreditoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaCreditoService {

    private final NotaCreditoRepository notaRepository;
    private final NotaCreditoMapper notaMapper;

    @Transactional(readOnly = true)
    public List<NotaCreditoResponse> findAll() {

        return notaMapper.toResponseList(
                notaRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public NotaCreditoResponse findById(Integer id) {

        return notaMapper.toResponse(
                getNotaById(id)
        );
    }

    @Transactional(readOnly = true)
    public NotaCreditoResponse findByRecepcion(
            Integer idRecepcion
    ) {

        NotaCredito nota = notaRepository
                .findByRecepcionLogistica_IdRecepcion(idRecepcion)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "NotaCredito",
                                "idRecepcion",
                                idRecepcion
                        )
                );

        return notaMapper.toResponse(nota);
    }

    @Transactional
    public NotaCreditoResponse create(
            NotaCreditoRequest request
    ) {

        NotaCredito nota =
                notaMapper.toEntity(request);

        return notaMapper.toResponse(
                notaRepository.save(nota)
        );
    }

    @Transactional
    public NotaCreditoResponse update(
            Integer id,
            NotaCreditoRequest request
    ) {

        NotaCredito nota =
                getNotaById(id);

        notaMapper.updateEntity(request, nota);

        return notaMapper.toResponse(
                notaRepository.save(nota)
        );
    }

    @Transactional
    public void deleteById(Integer id) {

        notaRepository.delete(
                getNotaById(id)
        );
    }

    private NotaCredito getNotaById(Integer id) {

        return notaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "NotaCredito",
                                "ID",
                                id
                        )
                );
    }
}