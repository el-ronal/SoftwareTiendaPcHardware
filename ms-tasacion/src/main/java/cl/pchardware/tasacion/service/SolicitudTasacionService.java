package cl.pchardware.tasacion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.tasacion.dto.SolicitudTasacionRequest;
import cl.pchardware.tasacion.dto.SolicitudTasacionResponse;
import cl.pchardware.tasacion.mapper.SolicitudTasacionMapper;
import cl.pchardware.tasacion.model.EstadoSolicitud;
import cl.pchardware.tasacion.model.SolicitudTasacion;
import cl.pchardware.tasacion.repository.SolicitudTasacionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudTasacionService {

    private final SolicitudTasacionRepository solicitudRepository;
    private final SolicitudTasacionMapper solicitudMapper;

    @Transactional(readOnly = true)
    public List<SolicitudTasacionResponse> findAll() {
        return solicitudMapper.toResponseList(solicitudRepository.findAll());
    }

    @Transactional(readOnly = true)
    public SolicitudTasacionResponse findById(Integer id) {
        return solicitudMapper.toResponse(getSolicitudById(id));
    }

    @Transactional(readOnly = true)
    public List<SolicitudTasacionResponse> findByUsuario(Integer idUsuario) {
        return solicitudMapper.toResponseList(solicitudRepository.findByIdUsuario(idUsuario));
    }

    @Transactional
    public SolicitudTasacionResponse create(SolicitudTasacionRequest request) {
        SolicitudTasacion solicitud = solicitudMapper.toEntity(request);
        solicitud.setEstadoSolicitud(EstadoSolicitud.valueOf(request.getEstadoSolicitud()));
        return solicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    @Transactional
    public SolicitudTasacionResponse update(Integer id, SolicitudTasacionRequest request) {
        SolicitudTasacion solicitud = getSolicitudById(id);
        solicitudMapper.updateEntity(request, solicitud);
        solicitud.setEstadoSolicitud(EstadoSolicitud.valueOf(request.getEstadoSolicitud()));
        return solicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    @Transactional
    public void deleteById(Integer id) {
        solicitudRepository.delete(getSolicitudById(id));
    }

    private SolicitudTasacion getSolicitudById(Integer id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SolicitudTasacion", "ID", id));
    }
}
