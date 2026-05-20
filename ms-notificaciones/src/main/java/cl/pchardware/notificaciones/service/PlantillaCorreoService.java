package cl.pchardware.notificaciones.service;

import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.notificaciones.dto.PlantillaCorreoRequest;
import cl.pchardware.notificaciones.dto.PlantillaCorreoResponse;
import cl.pchardware.notificaciones.mapper.PlantillaCorreoMapper;
import cl.pchardware.notificaciones.model.PlantillaCorreo;
import cl.pchardware.notificaciones.repository.PlantillaCorreoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantillaCorreoService {

    private final PlantillaCorreoRepository plantillaRepository;
    private final PlantillaCorreoMapper plantillaMapper;

    @Transactional(readOnly = true)
    public List<PlantillaCorreoResponse> findAll() {
        List<PlantillaCorreo> plantillas = plantillaRepository.findAll();
        return plantillaMapper.toResponseList(plantillas);
    }

    @Transactional(readOnly = true)
    public PlantillaCorreoResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        PlantillaCorreo plantilla = getPlantillaById(id);
        return plantillaMapper.toResponse(plantilla);
    }

    @Transactional
    public PlantillaCorreoResponse create(PlantillaCorreoRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        if (plantillaRepository.existsByCodigoEvento(request.getCodigoEvento())) {
            throw new DuplicateResourceException("PlantillaCorreo", "codigoEvento", request.getCodigoEvento(), request.getAsunto());
        }
        PlantillaCorreo entity = plantillaMapper.toEntity(request);
        
        PlantillaCorreo saved = plantillaRepository.save(Objects.requireNonNull(entity));
        return plantillaMapper.toResponse(saved);
    }

    @Transactional
    public PlantillaCorreoResponse update(Integer id, PlantillaCorreoRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        PlantillaCorreo plantilla = getPlantillaById(id);
        
        plantillaRepository.findByCodigoEvento(request.getCodigoEvento())
                .filter(p -> !p.getIdPlantilla().equals(id))
                .ifPresent(p -> { 
                    throw new DuplicateResourceException("PlantillaCorreo", "codigoEvento", request.getCodigoEvento(), request.getAsunto()); 
                });
                
        plantillaMapper.updateEntity(request, plantilla);
        
        PlantillaCorreo saved = plantillaRepository.save(Objects.requireNonNull(plantilla));
        return plantillaMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        PlantillaCorreo plantilla = getPlantillaById(id);
        
        plantillaRepository.delete(Objects.requireNonNull(plantilla));
    }

    @NonNull
    private PlantillaCorreo getPlantillaById(Integer id) {
        Objects.requireNonNull(id, "ID no puede ser nulo");
        return plantillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlantillaCorreo", "ID", id));
    }
}