package cl.pchardware.tasacion.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.tasacion.dto.SolicitudTasacionRequest;
import cl.pchardware.tasacion.dto.SolicitudTasacionResponse;
import cl.pchardware.tasacion.mapper.SolicitudTasacionMapper;
import cl.pchardware.tasacion.model.SolicitudTasacion;
import cl.pchardware.tasacion.model.EstadoSolicitud;
import cl.pchardware.tasacion.repository.SolicitudTasacionRepository;

// IMPORTS DE INTEGRACIÓN (FEIGN Y KAFKA)
import cl.pchardware.tasacion.client.UsuarioClient;
import cl.pchardware.tasacion.dto.UsuarioClientResponse;
import cl.pchardware.tasacion.event.TasacionEventProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudTasacionService {

    private final SolicitudTasacionRepository solicitudRepository;
    private final SolicitudTasacionMapper solicitudMapper;
    
    // INYECCIÓN DE LOS COMPONENTES DE ARQUITECTURA
    private final UsuarioClient usuarioClient;
    private final TasacionEventProducer tasacionEventProducer;

    @Transactional(readOnly = true)
    public List<SolicitudTasacionResponse> findAll() {
        return solicitudMapper.toResponseList(solicitudRepository.findAll());
    }

    @Transactional(readOnly = true)
    public SolicitudTasacionResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return solicitudMapper.toResponse(getSolicitudById(id));
    }

    @Transactional(readOnly = true)
    public List<SolicitudTasacionResponse> findByUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo");
        }
        return solicitudMapper.toResponseList(solicitudRepository.findByIdUsuario(idUsuario));
    }

    @Transactional
    public SolicitudTasacionResponse create(SolicitudTasacionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        // ----------------------------------------------------------------------
        // 1. VALIDACIÓN SÍNCRONA (FEIGN): Evitamos procesar a usuarios falsos
        // ----------------------------------------------------------------------
        try {
            UsuarioClientResponse usuario = usuarioClient.getUsuarioById(request.getIdUsuario());
            // Obtener estado desde el DTO (uso de getter clásico)
            if (usuario == null || !"ACTIVO".equalsIgnoreCase(usuario.getEstado())) { 
                throw new IllegalStateException("El usuario está inactivo o baneado. No puede solicitar tasaciones.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: El usuario ID " + request.getIdUsuario() + " no existe o el microservicio de usuarios no responde.", e);
        }

        // ----------------------------------------------------------------------
        // 2. PERSISTENCIA Y REGLA DE NEGOCIO: Bloquear inyecciones de estado
        // ----------------------------------------------------------------------
        SolicitudTasacion solicitud = solicitudMapper.toEntity(request);
        if (solicitud == null) {
            throw new IllegalStateException("No se pudo crear la solicitud desde el request");
        }
        
        // Todo hardware nuevo que entra, entra PENDIENTE. Ignoramos lo que mande el frontend.
        solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE);
        
        SolicitudTasacion solicitudGuardada = solicitudRepository.save(solicitud);

        // ----------------------------------------------------------------------
        // 3. EVENTO ASÍNCRONO (KAFKA): Avisamos al resto del sistema
        // ----------------------------------------------------------------------
        tasacionEventProducer.enviarEventoTasacionCreada(
            solicitudGuardada.getIdSolicitud(), 
            solicitudGuardada.getIdUsuario(), 
            solicitudGuardada.getHardwareDescripcion()
        );

        return solicitudMapper.toResponse(solicitudGuardada);
    }

    @Transactional
    public SolicitudTasacionResponse update(Integer id, SolicitudTasacionRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de actualización no puede ser nula");
        }

        SolicitudTasacion solicitud = getSolicitudById(id);
        
        // MapStruct se encarga automáticamente de transferir los campos del request a la entidad
        solicitudMapper.updateEntity(request, solicitud);

        SolicitudTasacion solicitudGuardada = solicitudRepository.save(solicitud);
        Objects.requireNonNull(solicitudGuardada, "La solicitud guardada no puede ser nula");
        return solicitudMapper.toResponse(solicitudGuardada);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
    
        SolicitudTasacion solicitud = getSolicitudById(id);
        if (solicitud != null) {
            solicitudRepository.delete(solicitud);
        }
    }
    
    private SolicitudTasacion getSolicitudById(Integer id) {
        Objects.requireNonNull(id, "El ID no puede ser nulo");
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SolicitudTasacion", "ID", id));
    }
}