package cl.pchardware.notificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.notificaciones.dto.RegistroEnvioRequest;
import cl.pchardware.notificaciones.dto.RegistroEnvioResponse;
import cl.pchardware.notificaciones.mapper.RegistroEnvioMapper;
import cl.pchardware.notificaciones.model.RegistroEnvio;
import cl.pchardware.notificaciones.repository.RegistroEnvioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null") // Evita falsos positivos del analizador estricto de nulos del IDE
public class RegistroEnvioService {

    private final RegistroEnvioRepository registroRepository;
    private final RegistroEnvioMapper registroMapper;

    @Transactional(readOnly = true)
    public List<RegistroEnvioResponse> findAll() {
        List<RegistroEnvio> registros = registroRepository.findAll();
        return registroMapper.toResponseList(registros);
    }

    @Transactional(readOnly = true)
    public RegistroEnvioResponse findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        RegistroEnvio registro = getRegistroById(id);
        return registroMapper.toResponse(registro);
    }
    
    @Transactional(readOnly = true)
    public List<RegistroEnvioResponse> findByIdMensaje(Integer idMensaje) {
        if (idMensaje == null) {
            throw new IllegalArgumentException("ID de mensaje no puede ser nulo");
        }
        List<RegistroEnvio> registros = registroRepository.findByMensaje_IdMensaje(idMensaje);
        return registroMapper.toResponseList(registros);
    }

    @Transactional
    public RegistroEnvioResponse create(RegistroEnvioRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        
        RegistroEnvio entity = registroMapper.toEntity(request);
        RegistroEnvio saved = registroRepository.save(entity);

        return registroMapper.toResponse(saved);
    }

    @Transactional
    public RegistroEnvioResponse update(Integer id, RegistroEnvioRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }

        RegistroEnvio registro = getRegistroById(id);

        registroMapper.updateEntity(request, registro);
        RegistroEnvio saved = registroRepository.save(registro);

        return registroMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        RegistroEnvio registro = getRegistroById(id);
        registroRepository.delete(registro);
    }

    private RegistroEnvio getRegistroById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return registroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RegistroEnvio", "ID", id));
    }
}