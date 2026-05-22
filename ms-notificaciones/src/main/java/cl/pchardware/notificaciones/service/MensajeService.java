package cl.pchardware.notificaciones.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.notificaciones.dto.MensajeRequest;
import cl.pchardware.notificaciones.dto.MensajeResponse;
import cl.pchardware.notificaciones.mapper.MensajeMapper;
import cl.pchardware.notificaciones.model.Mensaje;
import cl.pchardware.notificaciones.model.PlantillaCorreo;
import cl.pchardware.notificaciones.repository.MensajeRepository;
import cl.pchardware.notificaciones.repository.PlantillaCorreoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final PlantillaCorreoRepository plantillaRepository;
    private final MensajeMapper mensajeMapper;

    @Transactional(readOnly = true)
    public List<MensajeResponse> findAll() {
        return mensajeMapper.toResponseList(mensajeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public MensajeResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return mensajeMapper.toResponse(getMensajeById(id));
    }

    @Transactional(readOnly = true)
    public List<MensajeResponse> findByUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID de usuario no puede ser nulo");
        }
        return mensajeMapper.toResponseList(mensajeRepository.findByIdUsuario(idUsuario));
    }

    @Transactional
    public MensajeResponse create(MensajeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de mensaje no puede ser nula");
        }
        
        Integer idPlantilla = request.getIdPlantilla();
        if (idPlantilla == null) {
            throw new IllegalArgumentException("ID de plantilla no puede ser nulo");
        }
        
        PlantillaCorreo plantilla = plantillaRepository.findById(idPlantilla)
                .orElseThrow(() -> new EntityNotFoundException("PlantillaCorreo", "ID", idPlantilla));
        
        Mensaje mensaje = mensajeMapper.toEntity(request);
        if (mensaje == null) {
            throw new IllegalStateException("No se pudo crear el mensaje desde el request");
        }
        
        mensaje.setPlantilla(plantilla);
        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }

    @Transactional
public void deleteById(Integer id) {
    if (id == null) {
        throw new IllegalArgumentException("ID no puede ser nulo");
    }
    
    Mensaje mensaje = getMensajeById(id);
    if (mensaje != null) {
        mensajeRepository.delete(mensaje);
    }
}

private Mensaje getMensajeById(Integer id) {
    Objects.requireNonNull(id, "ID no puede ser nulo");
    return mensajeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Mensaje", "ID", id));
}
}
