package cl.pchardware.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.catalogo.dto.MarcaRequest;
import cl.pchardware.catalogo.dto.MarcaResponse;
import cl.pchardware.catalogo.mapper.MarcaMapper;
import cl.pchardware.catalogo.model.Marca;
import cl.pchardware.catalogo.repository.MarcaRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Transactional(readOnly = true)
    public List<MarcaResponse> findAll() {
        return marcaMapper.toResponseList(marcaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public MarcaResponse findById(Integer id) {
        return marcaMapper.toResponse(getMarcaById(id));
    }

    @Transactional
    public MarcaResponse create(MarcaRequest request) {
        if (marcaRepository.existsByCodigo(request.getCodigo())) {
            throw new DuplicateResourceException("Marca", "código", request.getCodigo(), request.getNombre());
        }
        return marcaMapper.toResponse(marcaRepository.save(marcaMapper.toEntity(request)));
    }

    @Transactional
    public MarcaResponse update(Integer id, MarcaRequest request) {
        Marca marca = getMarcaById(id);
        marcaRepository.findByCodigo(request.getCodigo())
                .filter(m -> !m.getIdMarca().equals(id))
                .ifPresent(m -> { throw new DuplicateResourceException("Marca", "código", request.getCodigo(), request.getNombre()); });
        marcaMapper.updateEntity(request, marca);
        return marcaMapper.toResponse(marcaRepository.save(marca));
    }

    @Transactional
    public void deleteById(Integer id) {
        Marca marca = getMarcaById(id);
        if (marca.getProductos() != null && !marca.getProductos().isEmpty()) {
            throw new ReferentialIntegrityException("Marca", id, "Productos");
        }
        marcaRepository.delete(marca);
    }

    private Marca getMarcaById(Integer id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca", "ID", id));
    }
}
