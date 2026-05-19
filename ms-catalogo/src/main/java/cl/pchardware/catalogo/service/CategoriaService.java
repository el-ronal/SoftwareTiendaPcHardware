package cl.pchardware.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.catalogo.dto.CategoriaRequest;
import cl.pchardware.catalogo.dto.CategoriaResponse;
import cl.pchardware.catalogo.mapper.CategoriaMapper;
import cl.pchardware.catalogo.model.Categoria;
import cl.pchardware.catalogo.repository.CategoriaRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public List<CategoriaResponse> findAll() {
        return categoriaMapper.toResponseList(categoriaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CategoriaResponse findById(Integer id) {
        return categoriaMapper.toResponse(getCategoriaById(id));
    }

    @Transactional
    public CategoriaResponse create(CategoriaRequest request) {
        if (categoriaRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Categoría", "slug", request.getSlug(), request.getNombre());
        }
        return categoriaMapper.toResponse(categoriaRepository.save(categoriaMapper.toEntity(request)));
    }

    @Transactional
    public CategoriaResponse update(Integer id, CategoriaRequest request) {
        Categoria categoria = getCategoriaById(id);
        categoriaRepository.findBySlug(request.getSlug())
                .filter(c -> !c.getIdCategoria().equals(id))
                .ifPresent(c -> { throw new DuplicateResourceException("Categoría", "slug", request.getSlug(), request.getNombre()); });
        categoriaMapper.updateEntity(request, categoria);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public void deleteById(Integer id) {
        Categoria categoria = getCategoriaById(id);
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new ReferentialIntegrityException("Categoría", id, "Productos");
        }
        categoriaRepository.delete(categoria);
    }

    private Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría", "ID", id));
    }
}
