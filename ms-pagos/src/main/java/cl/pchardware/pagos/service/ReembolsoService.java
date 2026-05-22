package cl.pchardware.pagos.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.pagos.dto.ReembolsoRequest;
import cl.pchardware.pagos.dto.ReembolsoResponse;
import cl.pchardware.pagos.mapper.ReembolsoMapper;
import cl.pchardware.pagos.model.Reembolso;
import cl.pchardware.pagos.model.Transaccion;
import cl.pchardware.pagos.repository.ReembolsoRepository;
import cl.pchardware.pagos.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de las devoluciones y reembolsos financieros.
 * Asegura que el reembolso esté vinculado a una transacción existente real.
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
        Integer idTransaccion = Objects.requireNonNull(request.getIdTransaccion(), "idTransaccion");
        Transaccion transaccion = getTransaccionById(idTransaccion);

        Reembolso reembolso = Objects.requireNonNull(new Reembolso(), "reembolso");
        reembolsoMapper.updateEntity(request, reembolso);
        reembolso.setTransaccion(transaccion);

        return reembolsoMapper.toResponse(reembolsoRepository.save(Objects.requireNonNull(reembolso, "reembolso")));
    }

    @Transactional
    public ReembolsoResponse update(Integer id, ReembolsoRequest request) {
        Reembolso reembolso = getReembolsoById(id);
        Integer idTransaccion = Objects.requireNonNull(request.getIdTransaccion(), "idTransaccion");
        Transaccion transaccion = getTransaccionById(idTransaccion);

        reembolsoMapper.updateEntity(request, reembolso);
        reembolso.setTransaccion(transaccion);

        return reembolsoMapper.toResponse(reembolsoRepository.save(Objects.requireNonNull(reembolso, "reembolso")));
    }

    @Transactional
    public void deleteById(Integer id) {
        Reembolso reembolso = getReembolsoById(id);
        reembolsoRepository.delete(Objects.requireNonNull(reembolso, "reembolso"));
    }

    private Reembolso getReembolsoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return reembolsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reembolso", "ID", id));
    }

    private Transaccion getTransaccionById(Integer id) {
        Objects.requireNonNull(id, "id");
        return transaccionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transacción", "ID", id));
    }
}
