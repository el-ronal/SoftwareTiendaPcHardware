package cl.pchardware.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.mapper.ProductoMapper;
import cl.pchardware.catalogo.model.Categoria;
import cl.pchardware.catalogo.model.Marca;
import cl.pchardware.catalogo.model.Producto;
import cl.pchardware.catalogo.repository.CategoriaRepository;
import cl.pchardware.catalogo.repository.MarcaRepository;
import cl.pchardware.catalogo.repository.ProductoRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Transactional(readOnly = true)
    public List<ProductoResponse> findAll() {
        return productoMapper.toResponseList(productoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductoResponse findById(Integer id) {
        return productoMapper.toResponse(getProductoById(id));
    }

    @Transactional(readOnly = true)
    public ProductoResponse findBySku(String sku) {
        return productoMapper.toResponse(productoRepository.findBySku(sku)
                .orElseThrow(() -> new EntityNotFoundException("Producto", "SKU", sku)));
    }

    @Transactional
    public ProductoResponse create(ProductoRequest request) {
        if (productoRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Producto", "SKU", request.getSku(), request.getSku());
        }
        Marca marca = getMarcaById(request.getIdMarca());
        Categoria categoria = getCategoriaById(request.getIdCategoria());
        Producto producto = productoMapper.toEntity(request);
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponse update(Integer id, ProductoRequest request) {
        Producto producto = getProductoById(id);
        productoRepository.findBySku(request.getSku())
                .filter(p -> !p.getIdProducto().equals(id))
                .ifPresent(p -> { throw new DuplicateResourceException("Producto", "SKU", request.getSku(), request.getSku()); });
        Marca marca = getMarcaById(request.getIdMarca());
        Categoria categoria = getCategoriaById(request.getIdCategoria());
        productoMapper.updateEntity(request, producto);
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public void deleteById(Integer id) {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
    }

    private Producto getProductoById(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto", "ID", id));
    }

    private Marca getMarcaById(Integer id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca", "ID", id));
    }

    private Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría", "ID", id));
    }
}
