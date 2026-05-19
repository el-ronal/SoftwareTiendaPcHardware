package cl.pchardware.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.mapper.InventarioMapper;
import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.model.Inventario;
import cl.pchardware.stock.repository.BodegaRepository;
import cl.pchardware.stock.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final BodegaRepository bodegaRepository;
    private final InventarioMapper inventarioMapper;

    @Transactional(readOnly = true)
    public List<InventarioResponse> findAll() {
        return inventarioMapper.toResponseList(inventarioRepository.findAll());
    }

    @Transactional(readOnly = true)
    public InventarioResponse findById(Integer id) {
        return inventarioMapper.toResponse(getInventarioById(id));
    }

    @Transactional(readOnly = true)
    public List<InventarioResponse> findBySku(String sku) {
        return inventarioMapper.toResponseList(inventarioRepository.findBySkuProducto(sku));
    }

    @Transactional
    public InventarioResponse create(InventarioRequest request) {
        inventarioRepository.findByBodega_IdBodegaAndSkuProducto(request.getIdBodega(), request.getSkuProducto())
                .ifPresent(i -> { throw new DuplicateResourceException("Inventario", "bodega+SKU",
                        request.getIdBodega() + "+" + request.getSkuProducto(), request.getSkuProducto()); });
        Bodega bodega = getBodegaById(request.getIdBodega());
        Inventario inventario = inventarioMapper.toEntity(request);
        inventario.setBodega(bodega);
        return inventarioMapper.toResponse(inventarioRepository.save(inventario));
    }

    @Transactional
    public InventarioResponse update(Integer id, InventarioRequest request) {
        Inventario inventario = getInventarioById(id);
        Bodega bodega = getBodegaById(request.getIdBodega());
        inventarioMapper.updateEntity(request, inventario);
        inventario.setBodega(bodega);
        return inventarioMapper.toResponse(inventarioRepository.save(inventario));
    }

    @Transactional
    public void deleteById(Integer id) {
        inventarioRepository.delete(getInventarioById(id));
    }

    private Inventario getInventarioById(Integer id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario", "ID", id));
    }

    private Bodega getBodegaById(Integer id) {
        return bodegaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bodega", "ID", id));
    }
}
