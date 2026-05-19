package cl.pchardware.notificaciones.service;

import java.util.List;

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
        return mensajeMapper.toResponse(getMensajeById(id));
    }

    @Transactional(readOnly = true)
    public List<MensajeResponse> findByUsuario(Integer idUsuario) {
        return mensajeMapper.toResponseList(mensajeRepository.findByIdUsuario(idUsuario));
    }

    @Transactional
    public MensajeResponse create(MensajeRequest request) {
        PlantillaCorreo plantilla = plantillaRepository.findById(request.getIdPlantilla())
                .orElseThrow(() -> new EntityNotFoundException("PlantillaCorreo", "ID", request.getIdPlantilla()));
        Mensaje mensaje = mensajeMapper.toEntity(request);
        mensaje.setPlantilla(plantilla);
        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }

    @Transactional
    public void deleteById(Integer id) {
        mensajeRepository.delete(getMensajeById(id));
    }

    private Mensaje getMensajeById(Integer id) {
        return mensajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje", "ID", id));
    }
}
