package cl.pchardware.tasacion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.tasacion.dto.EvaluacionTecnicaRequest;
import cl.pchardware.tasacion.dto.EvaluacionTecnicaResponse;
import cl.pchardware.tasacion.mapper.EvaluacionTecnicaMapper;
import cl.pchardware.tasacion.model.EvaluacionTecnica;
import cl.pchardware.tasacion.repository.EvaluacionTecnicaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null") // Evita falsos positivos del analizador estricto de nulos del IDE
public class EvaluacionTecnicaService {

    private final EvaluacionTecnicaRepository evaluacionRepository;
    private final EvaluacionTecnicaMapper evaluacionMapper;

    @Transactional(readOnly = true)
    public List<EvaluacionTecnicaResponse> findAll() {
        List<EvaluacionTecnica> evaluaciones = evaluacionRepository.findAll();
        return evaluacionMapper.toResponseList(evaluaciones);
    }

    @Transactional(readOnly = true)
    public EvaluacionTecnicaResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        EvaluacionTecnica evaluacion = getEvaluacionById(id);
        return evaluacionMapper.toResponse(evaluacion);
    }

    @Transactional
    public EvaluacionTecnicaResponse create(EvaluacionTecnicaRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        
        // Validación de restricción única: 1 Solicitud = 1 Evaluación
        if (evaluacionRepository.existsBySolicitudTasacion_IdSolicitud(request.getIdSolicitud())) {
            throw new DuplicateResourceException(
                "EvaluacionTecnica", 
                "idSolicitud", 
                request.getIdSolicitud().toString(), 
                "Ya existe una evaluación técnica para esta solicitud"
            );
        }

        EvaluacionTecnica entity = evaluacionMapper.toEntity(request);
        EvaluacionTecnica saved = evaluacionRepository.save(entity);

        return evaluacionMapper.toResponse(saved);
    }

    @Transactional
    public EvaluacionTecnicaResponse update(Integer id, EvaluacionTecnicaRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }

        EvaluacionTecnica evaluacion = getEvaluacionById(id);

        // Validamos que, si cambian el ID de la solicitud, no colisione con una existente
        evaluacionRepository.findBySolicitudTasacion_IdSolicitud(request.getIdSolicitud())
                .filter(e -> !e.getIdEvaluacion().equals(id)) // Ignoramos si es la misma evaluación
                .ifPresent(e -> {
                    throw new DuplicateResourceException(
                        "EvaluacionTecnica", 
                        "idSolicitud", 
                        request.getIdSolicitud().toString(), 
                        "Ya existe otra evaluación técnica asociada a esta solicitud"
                    );
                });

        evaluacionMapper.updateEntity(request, evaluacion);
        EvaluacionTecnica saved = evaluacionRepository.save(evaluacion);

        return evaluacionMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        EvaluacionTecnica evaluacion = getEvaluacionById(id);
        evaluacionRepository.delete(evaluacion);
    }

    private EvaluacionTecnica getEvaluacionById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return evaluacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EvaluacionTecnica", "ID", id));
    }
}