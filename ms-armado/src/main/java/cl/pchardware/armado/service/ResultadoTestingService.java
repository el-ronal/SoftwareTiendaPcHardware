package cl.pchardware.armado.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.armado.dto.ResultadoTestingRequest;
import cl.pchardware.armado.dto.ResultadoTestingResponse;
import cl.pchardware.armado.mapper.ResultadoTestingMapper;
import cl.pchardware.armado.model.ResultadoTesting;
import cl.pchardware.armado.repository.ResultadoTestingRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null") // Evita las alertas estrictas de nulos del IDE
public class ResultadoTestingService {

    private final ResultadoTestingRepository resultadoRepository;
    private final ResultadoTestingMapper resultadoMapper;

    @Transactional(readOnly = true)
    public List<ResultadoTestingResponse> findAll() {
        List<ResultadoTesting> resultados = resultadoRepository.findAll();
        return resultadoMapper.toResponseList(resultados);
    }

    @Transactional(readOnly = true)
    public ResultadoTestingResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        ResultadoTesting resultado = getResultadoById(id);
        return resultadoMapper.toResponse(resultado);
    }

    @Transactional
    public ResultadoTestingResponse create(ResultadoTestingRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        
        // Validación de restricción única: 1 Orden = 1 Resultado
        if (resultadoRepository.existsByOrden_IdOrden(request.getIdOrden())) {
            throw new DuplicateResourceException(
                "ResultadoTesting", 
                "idOrden", 
                request.getIdOrden().toString(), 
                "Ya existe un resultado de testing registrado para esta orden de ensamble"
            );
        }

        ResultadoTesting entity = resultadoMapper.toEntity(request);
        ResultadoTesting saved = resultadoRepository.save(entity);

        return resultadoMapper.toResponse(saved);
    }

    @Transactional
    public ResultadoTestingResponse update(Integer id, ResultadoTestingRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }

        ResultadoTesting resultado = getResultadoById(id);

        // Validamos que si el usuario cambia el ID de la orden, no asigne una orden que ya tiene un testing
        resultadoRepository.findByOrden_IdOrden(request.getIdOrden())
                .filter(r -> !r.getIdResultado().equals(id)) // Ignoramos si es el mismo registro
                .ifPresent(r -> {
                    throw new DuplicateResourceException(
                        "ResultadoTesting", 
                        "idOrden", 
                        request.getIdOrden().toString(), 
                        "Ya existe otro resultado de testing asociado a esta orden de ensamble"
                    );
                });

        resultadoMapper.updateEntity(request, resultado);
        ResultadoTesting saved = resultadoRepository.save(resultado);

        return resultadoMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        ResultadoTesting resultado = getResultadoById(id);
        resultadoRepository.delete(resultado);
    }

    private ResultadoTesting getResultadoById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return resultadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ResultadoTesting", "ID", id));
    }
}