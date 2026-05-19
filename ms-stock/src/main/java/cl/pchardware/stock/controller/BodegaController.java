package cl.pchardware.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.stock.dto.BodegaRequest;
import cl.pchardware.stock.dto.BodegaResponse;
import cl.pchardware.stock.mapper.BodegaMapper;
import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.repository.BodegaRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bodegas")
@RequiredArgsConstructor
public class BodegaController {

    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    /**
     * Lista todas las bodegas registradas en el sistema.
     * GET /api/v1/bodegas
     */
    @GetMapping
    public ResponseEntity<List<BodegaResponse>> listarBodegas() {
        List<Bodega> bodegas = bodegaRepository.findAll();
        return ResponseEntity.ok(bodegaMapper.toResponseList(bodegas));
    }

    /**
     * Registra una nueva bodega (Central, Tasaciones, Merma).
     * POST /api/v1/bodegas
     */
    @PostMapping
    @Transactional
    public ResponseEntity<BodegaResponse> crearBodega(@Valid @RequestBody BodegaRequest request) {
        if (bodegaRepository.existsByCodigo(request.getCodigo())) {
            throw new DuplicateResourceException("Una Bodega", "código", request.getCodigo(), request.getNombre());
        }
        
        Bodega bodega = bodegaMapper.toEntity(request);
        Bodega guardada = bodegaRepository.save(bodega);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaMapper.toResponse(guardada));
    }
}