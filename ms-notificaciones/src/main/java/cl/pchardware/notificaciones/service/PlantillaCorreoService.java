package cl.pchardware.notificaciones.service;

import java.util.List;

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
        return plantillaMapper.toResponseList(plantillaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PlantillaCorreoResponse findById(Integer id) {
        return plantillaMapper.toResponse(getPlantillaById(id));
    }

    @Transactional
    public PlantillaCorreoResponse create(PlantillaCorreoRequest request) {
        if (plantillaRepository.existsByCodigoEvento(request.getCodigoEvento())) {
            throw new DuplicateResourceException("PlantillaCorreo", "codigoEvento", request.getCodigoEvento(), request.getAsunto());
        }
        return plantillaMapper.toResponse(plantillaRepository.save(plantillaMapper.toEntity(request)));
    }

    @Transactional
    public PlantillaCorreoResponse update(Integer id, PlantillaCorreoRequest request) {
        PlantillaCorreo plantilla = getPlantillaById(id);
        plantillaRepository.findByCodigoEvento(request.getCodigoEvento())
                .filter(p -> !p.getIdPlantilla().equals(id))
                .ifPresent(p -> { throw new DuplicateResourceException("PlantillaCorreo", "codigoEvento", request.getCodigoEvento(), request.getAsunto()); });
        plantillaMapper.updateEntity(request, plantilla);
        return plantillaMapper.toResponse(plantillaRepository.save(plantilla));
    }

    @Transactional
    public void deleteById(Integer id) {
        plantillaRepository.delete(getPlantillaById(id));
    }

    private PlantillaCorreo getPlantillaById(Integer id) {
        return plantillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlantillaCorreo", "ID", id));
    }
}
