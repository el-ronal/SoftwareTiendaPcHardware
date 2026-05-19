package cl.pchardware.devoluciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.devoluciones.dto.SolicitudDevolucionRequest;
import cl.pchardware.devoluciones.dto.SolicitudDevolucionResponse;
import cl.pchardware.devoluciones.mapper.SolicitudDevolucionMapper;
import cl.pchardware.devoluciones.model.SolicitudDevolucion;
import cl.pchardware.devoluciones.repository.SolicitudDevolucionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudDevolucionService {

    private final SolicitudDevolucionRepository solicitudRepository;
    private final SolicitudDevolucionMapper solicitudMapper;

    @Transactional(readOnly = true)
    public List<SolicitudDevolucionResponse> findAll() {
        return solicitudMapper.toResponseList(solicitudRepository.findAll());
    }

    @Transactional(readOnly = true)
    public SolicitudDevolucionResponse findById(Integer id) {
        return solicitudMapper.toResponse(getSolicitudById(id));
    }

    @Transactional(readOnly = true)
    public List<SolicitudDevolucionResponse> findByPedido(Integer idPedido) {
        return solicitudMapper.toResponseList(solicitudRepository.findByIdPedido(idPedido));
    }

    @Transactional
    public SolicitudDevolucionResponse create(SolicitudDevolucionRequest request) {
        return solicitudMapper.toResponse(solicitudRepository.save(solicitudMapper.toEntity(request)));
    }

    @Transactional
    public SolicitudDevolucionResponse update(Integer id, SolicitudDevolucionRequest request) {
        SolicitudDevolucion solicitud = getSolicitudById(id);
        solicitudMapper.updateEntity(request, solicitud);
        return solicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    @Transactional
    public void deleteById(Integer id) {
        solicitudRepository.delete(getSolicitudById(id));
    }

    private SolicitudDevolucion getSolicitudById(Integer id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SolicitudDevolucion", "ID", id));
    }
}
