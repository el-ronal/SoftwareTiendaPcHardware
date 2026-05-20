package cl.pchardware.stock.service;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.stock.dto.BodegaRequest;
import cl.pchardware.stock.dto.BodegaResponse;
import cl.pchardware.stock.mapper.BodegaMapper;
import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.repository.BodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BodegaService {

    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    public List<BodegaResponse> findAll() {
        return bodegaMapper.toResponseList(bodegaRepository.findAll());
    }

    public Bodega getBodegaById(Long id) {
        long idBodega = Objects.requireNonNull(id, "El ID de la bodega no puede ser nulo");
        return bodegaRepository.findById(idBodega)
                .orElseThrow(() -> new EntityNotFoundException("Bodega", "ID", idBodega));
    }

    public Bodega getBodegaByCodigo(String codigo) {
        return bodegaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Bodega", "Código", codigo));
    }

    public BodegaResponse create(BodegaRequest request) {
        bodegaRepository.findByCodigo(request.getCodigo()).ifPresent(b -> {
            throw new DuplicateResourceException("Bodega", "Código", request.getCodigo(), b.getNombre());
        });
        Bodega bodega = Objects.requireNonNull(bodegaMapper.toEntity(request), "La bodega no puede ser nula");
        bodega = bodegaRepository.save(bodega);
        return bodegaMapper.toResponse(bodega);
    }
}