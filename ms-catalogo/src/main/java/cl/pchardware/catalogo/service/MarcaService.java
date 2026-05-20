package cl.pchardware.catalogo.service;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.catalogo.dto.MarcaRequest;
import cl.pchardware.catalogo.dto.MarcaResponse;
import cl.pchardware.catalogo.mapper.MarcaMapper;
import cl.pchardware.catalogo.model.Marca;
import cl.pchardware.catalogo.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    public List<MarcaResponse> findAll() {
        return marcaMapper.toResponseList(marcaRepository.findAll());
    }

    public Marca getMarcaByCodigo(String codigo) {
        return marcaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Marca", "código", codigo));
    }

    public MarcaResponse create(MarcaRequest request) {
        marcaRepository.findByCodigo(request.getCodigo()).ifPresent(m -> {
            throw new DuplicateResourceException("Marca", "código", request.getCodigo(), m.getNombre());
        });
        Marca marca = Objects.requireNonNull(marcaMapper.toEntity(request), "La marca no puede ser nula");
        marca = marcaRepository.save(marca);
        return marcaMapper.toResponse(marca);
    }
}