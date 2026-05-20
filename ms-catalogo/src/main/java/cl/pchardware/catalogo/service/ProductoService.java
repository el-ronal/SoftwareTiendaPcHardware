package cl.pchardware.catalogo.service;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.mapper.ProductoMapper;
import cl.pchardware.catalogo.model.Producto;
import cl.pchardware.catalogo.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    
    private final MarcaService marcaService;
    private final CategoriaService categoriaService;

    public List<ProductoResponse> findAll() {
        return productoMapper.toResponseList(productoRepository.findAll());
    }

    public ProductoResponse findBySku(String sku) {
        return productoMapper.toResponse(getProductoBySku(sku));
    }

    @Transactional
    public ProductoResponse create(ProductoRequest request) {
        validateSkuUnico(request.getSku());

        Producto producto = Objects.requireNonNull(productoMapper.toEntity(request), "El producto no puede ser nulo");
        
        producto.setMarca(marcaService.getMarcaByCodigo(request.getCodigoMarca()));
        producto.setCategoria(categoriaService.getCategoriaBySlug(request.getSlugCategoria()));

        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponse update(String sku, ProductoRequest request) {
        Producto producto = getProductoBySku(sku);

        if (!producto.getSku().equalsIgnoreCase(request.getSku())) {
            validateSkuUnico(request.getSku());
        }

        productoMapper.updateEntity(request, producto);
        
        // Actualizar relaciones si cambiaron en el request
        if (!producto.getMarca().getCodigo().equalsIgnoreCase(request.getCodigoMarca())) {
            producto.setMarca(marcaService.getMarcaByCodigo(request.getCodigoMarca()));
        }
        
        if (!producto.getCategoria().getSlug().equalsIgnoreCase(request.getSlugCategoria())) {
            producto.setCategoria(categoriaService.getCategoriaBySlug(request.getSlugCategoria()));
        }

        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public void deleteBySku(String sku) {
        Producto producto = Objects.requireNonNull(getProductoBySku(sku), "El producto no puede ser nulo");
        productoRepository.delete(producto);
    }

    private Producto getProductoBySku(String sku) {
        return productoRepository.findBySku(sku)
                .orElseThrow(() -> new EntityNotFoundException("Producto", "SKU", sku));
    }

    private void validateSkuUnico(String sku) {
        productoRepository.findBySku(sku).ifPresent(p -> {
            throw new DuplicateResourceException("Producto", "SKU", sku, "Marca ID: " + p.getMarca().getIdMarca());
        });
    }
}