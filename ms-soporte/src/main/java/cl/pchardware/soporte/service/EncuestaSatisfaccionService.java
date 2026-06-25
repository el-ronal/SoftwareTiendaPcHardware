package cl.pchardware.soporte.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.soporte.dto.EncuestaSatisfaccionRequest;
import cl.pchardware.soporte.dto.EncuestaSatisfaccionResponse;
import cl.pchardware.soporte.mapper.EncuestaSatisfaccionMapper;
import cl.pchardware.soporte.model.EncuestaSatisfaccion;
import cl.pchardware.soporte.repository.EncuestaSatisfaccionRepository;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncuestaSatisfaccionService {

    private final EncuestaSatisfaccionRepository encuestaRepository;
    private final EncuestaSatisfaccionMapper encuestaMapper;

    @Transactional(readOnly = true)
    public List<EncuestaSatisfaccionResponse> findAll() {
        return encuestaMapper.toResponseList(
                encuestaRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public EncuestaSatisfaccionResponse findById(Integer id) {
        return encuestaMapper.toResponse(
                getEncuestaById(id)
        );
    }

    @Transactional(readOnly = true)
    public EncuestaSatisfaccionResponse findByTicket(Integer idTicket) {

        EncuestaSatisfaccion encuesta = encuestaRepository
                .findByTicketSoporte_IdTicket(idTicket)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "EncuestaSatisfaccion",
                                "idTicket",
                                idTicket
                        )
                );

        return encuestaMapper.toResponse(encuesta);
    }

    @Transactional
    public EncuestaSatisfaccionResponse create(
            EncuestaSatisfaccionRequest request
    ) {

        EncuestaSatisfaccion encuesta =
                encuestaMapper.toEntity(request);

        if (encuesta == null) {
            throw new IllegalArgumentException("Failed to map EncuestaSatisfaccion from request");
        }

        return encuestaMapper.toResponse(
                encuestaRepository.save(encuesta)
        );
    }

    @Transactional
    public EncuestaSatisfaccionResponse update(
            Integer id,
            EncuestaSatisfaccionRequest request
    ) {

        EncuestaSatisfaccion encuesta =
                getEncuestaById(id);

        encuestaMapper.updateEntity(request, encuesta);

        if (encuesta == null) {
            throw new IllegalArgumentException("Failed to update EncuestaSatisfaccion");
        }

        return encuestaMapper.toResponse(
                encuestaRepository.save(encuesta)
        );
    }

    @Transactional
    public void deleteById(Integer id) {
        encuestaRepository.delete(
                getEncuestaById(id)
        );
    }

    private EncuestaSatisfaccion getEncuestaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        EncuestaSatisfaccion encuesta = encuestaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "EncuestaSatisfaccion",
                                "ID",
                                id
                        )
                );

        return Objects.requireNonNull(encuesta, "EncuestaSatisfaccion must not be null");
    }
}