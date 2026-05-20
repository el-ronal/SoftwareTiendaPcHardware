package cl.pchardware.tasacion.service;

import java.util.List;
import java.util.Objects;

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
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return solicitudMapper.toResponse(getSolicitudById(id));
    }

    @Transactional(readOnly = true)
    public List<SolicitudTasacionResponse> findByUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID de usuario no puede ser nulo");
        }
        return solicitudMapper.toResponseList(solicitudRepository.findByIdUsuario(idUsuario));
    }

    @Transactional
    public SolicitudTasacionResponse create(SolicitudTasacionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        SolicitudTasacion solicitud = solicitudMapper.toEntity(request);
        if (solicitud == null) {
            throw new IllegalStateException("No se pudo crear la solicitud desde el request");
        }

        String estadoStr = request.getEstadoSolicitud();
        if (estadoStr == null || estadoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de la solicitud no puede ser nulo o vacío");
        }

        try {
            solicitud.setEstadoSolicitud(EstadoSolicitud.valueOf(estadoStr));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de solicitud inválido: " + estadoStr, e);
        }

        return solicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    @Transactional
    public SolicitudTasacionResponse update(Integer id, SolicitudTasacionRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de actualización no puede ser nula");
        }

        SolicitudTasacion solicitud = getSolicitudById(id);
        solicitudMapper.updateEntity(request, solicitud);

        String estadoStr = request.getEstadoSolicitud();
        if (estadoStr == null || estadoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de la solicitud no puede ser nulo o vacío");
        }

        try {
            solicitud.setEstadoSolicitud(EstadoSolicitud.valueOf(estadoStr));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de solicitud inválido: " + estadoStr, e);
        }

        return solicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
        throw new IllegalArgumentException("ID no puede ser nulo");
        }
    
        SolicitudTasacion solicitud = getSolicitudById(id);
        if (solicitud != null) {
            solicitudRepository.delete(solicitud);
        }
}
    private SolicitudTasacion getSolicitudById(Integer id) {
        Objects.requireNonNull(id, "ID no puede ser nulo");
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SolicitudTasacion", "ID", id));
    }
}
