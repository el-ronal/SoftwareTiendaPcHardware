package cl.pchardware.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.stock.dto.BodegaRequest;
import cl.pchardware.stock.dto.BodegaResponse;
import cl.pchardware.stock.mapper.BodegaMapper;
import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.model.TipoBodega;
import cl.pchardware.stock.repository.BodegaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BodegaService {

    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    @Transactional(readOnly = true)
    public List<BodegaResponse> findAll() {
        return bodegaMapper.toResponseList(bodegaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BodegaResponse findById(Integer id) {
        return bodegaMapper.toResponse(getBodegaById(id));
    }

    @Transactional
    public BodegaResponse create(BodegaRequest request) {
        if (bodegaRepository.existsByCodigo(request.getCodigo())) {
            throw new DuplicateResourceException("Bodega", "código", request.getCodigo(), request.getNombre());
        }
        Bodega bodega = bodegaMapper.toEntity(request);
        bodega.setTipo(TipoBodega.valueOf(request.getTipo()));
        return bodegaMapper.toResponse(bodegaRepository.save(bodega));
    }

    @Transactional
    public BodegaResponse update(Integer id, BodegaRequest request) {
        Bodega bodega = getBodegaById(id);
        bodegaRepository.findByCodigo(request.getCodigo())
                .filter(b -> !b.getIdBodega().equals(id))
                .ifPresent(b -> { throw new DuplicateResourceException("Bodega", "código", request.getCodigo(), request.getNombre()); });
        bodegaMapper.updateEntity(request, bodega);
        bodega.setTipo(TipoBodega.valueOf(request.getTipo()));
        return bodegaMapper.toResponse(bodegaRepository.save(bodega));
    }

    @Transactional
    public void deleteById(Integer id) {
        Bodega bodega = getBodegaById(id);
        if (bodega.getInventarios() != null && !bodega.getInventarios().isEmpty()) {
            throw new ReferentialIntegrityException("Bodega", id, "Inventarios");
        }
        bodegaRepository.delete(bodega);
    }

    private Bodega getBodegaById(Integer id) {
        return bodegaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bodega", "ID", id));
    }
}
