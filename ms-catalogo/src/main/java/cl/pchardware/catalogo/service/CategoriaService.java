package cl.pchardware.catalogo.service;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.catalogo.dto.CategoriaRequest;
import cl.pchardware.catalogo.dto.CategoriaResponse;
import cl.pchardware.catalogo.mapper.CategoriaMapper;
import cl.pchardware.catalogo.model.Categoria;
import cl.pchardware.catalogo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public List<CategoriaResponse> findAll() {
        return categoriaMapper.toResponseList(categoriaRepository.findAll());
    }

    public Categoria getCategoriaBySlug(String slug) {
        return categoriaRepository.findBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException("Categoría", "slug", slug));
    }

    public CategoriaResponse create(CategoriaRequest request) {
        categoriaRepository.findBySlug(request.getSlug()).ifPresent(c -> {
            throw new DuplicateResourceException("Categoría", "slug", request.getSlug(), c.getNombre());
        });
        Categoria categoria = Objects.requireNonNull(categoriaMapper.toEntity(request), "La categoría no puede ser nula");
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toResponse(categoria);
    }
}