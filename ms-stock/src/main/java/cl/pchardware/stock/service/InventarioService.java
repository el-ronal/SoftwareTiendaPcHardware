package cl.pchardware.stock.service;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.mapper.InventarioMapper;
import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.model.Inventario;
import cl.pchardware.stock.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final InventarioMapper inventarioMapper;
    private final BodegaService bodegaService;

    public List<InventarioResponse> findBySku(String sku) {
        return inventarioMapper.toResponseList(inventarioRepository.findBySkuProducto(sku));
    }

    public Inventario getInventarioById(Long id) {
        Long idInventario = Objects.requireNonNull(id, "El ID del inventario no puede ser nulo");
        return inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new EntityNotFoundException("Inventario", "ID", idInventario));
    }

    public InventarioResponse create(InventarioRequest request) {
        Bodega bodega = bodegaService.getBodegaByCodigo(request.getCodigoBodega());

        inventarioRepository.findByBodegaIdAndSkuProducto(bodega.getIdBodega(), request.getSkuProducto())
            .ifPresent(i -> {
                throw new DuplicateResourceException("Inventario", "SKU", request.getSkuProducto(), "Bodega " + bodega.getCodigo());
            });

        Inventario inventario = inventarioMapper.toEntity(request);
        inventario.setBodega(bodega);

        return inventarioMapper.toResponse(inventarioRepository.save(inventario));
    }
}