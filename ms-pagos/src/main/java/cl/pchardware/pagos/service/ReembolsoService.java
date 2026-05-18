package cl.pchardware.pagos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.pchardware.pagos.dto.ReembolsoRequest;
import cl.pchardware.pagos.dto.ReembolsoResponse;
import cl.pchardware.pagos.mapper.ReembolsoMapper;
import cl.pchardware.pagos.model.Reembolso;
import cl.pchardware.pagos.model.Transaccion;
import cl.pchardware.pagos.repository.ReembolsoRepository;
import cl.pchardware.pagos.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de reembolsos.
 */
@Service
@RequiredArgsConstructor
public class ReembolsoService {

    private final ReembolsoRepository reembolsoRepository;
    private final TransaccionRepository transaccionRepository;
    private final ReembolsoMapper reembolsoMapper;

    public List<ReembolsoResponse> findAll() {
        return reembolsoMapper.toResponseList(reembolsoRepository.findAll());
    }

    public ReembolsoResponse findById(Integer id) {
        return reembolsoMapper.toResponse(getReembolsoById(id));
    }

    @Transactional
    public ReembolsoResponse create(ReembolsoRequest request) {
        Transaccion transaccion = transaccionRepository.findById(request.getIdTransaccion())
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Transacción no encontrada con ID: " + request.getIdTransaccion()));
        
        if (transaccion.getReembolso() != null) {
            throw new RuntimeException("DuplicateResourceException: La transacción con ID " + request.getIdTransaccion() + " ya cuenta con un reembolso asociado.");
        }

        Reembolso reembolso = new Reembolso();
        reembolsoMapper.updateEntity(request, reembolso);
        reembolso.setTransaccion(transaccion);
        
        return reembolsoMapper.toResponse(reembolsoRepository.save(reembolso));
    }

    @Transactional
    public ReembolsoResponse update(Integer id, ReembolsoRequest request) {
        Reembolso reembolso = getReembolsoById(id);
        
        Transaccion transaccion = transaccionRepository.findById(request.getIdTransaccion())
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Transacción no encontrada con ID: " + request.getIdTransaccion()));
        
        reembolsoMapper.updateEntity(request, reembolso);
        reembolso.setTransaccion(transaccion);
        
        return reembolsoMapper.toResponse(reembolsoRepository.save(reembolso));
    }

    @Transactional
    public void deleteById(Integer id) {
        Reembolso reembolso = getReembolsoById(id);
        reembolsoRepository.delete(reembolso);
    }

    private Reembolso getReembolsoById(Integer id) {
        return reembolsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EntityNotFoundException: Reembolso no encontrado con ID: " + id));
    }
}
