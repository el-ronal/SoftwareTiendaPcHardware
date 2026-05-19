package cl.pchardware.garantias.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.garantias.dto.TicketGarantiaRequest;
import cl.pchardware.garantias.dto.TicketGarantiaResponse;
import cl.pchardware.garantias.mapper.TicketGarantiaMapper;
import cl.pchardware.garantias.model.TicketGarantia;
import cl.pchardware.garantias.repository.TicketGarantiaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketGarantiaService {

    private final TicketGarantiaRepository ticketRepository;
    private final TicketGarantiaMapper ticketMapper;

    @Transactional(readOnly = true)
    public List<TicketGarantiaResponse> findAll() {
        return ticketMapper.toResponseList(ticketRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TicketGarantiaResponse findById(Integer id) {
        return ticketMapper.toResponse(getTicketById(id));
    }

    @Transactional(readOnly = true)
    public List<TicketGarantiaResponse> findByPedido(Integer idPedido) {
        return ticketMapper.toResponseList(ticketRepository.findByIdPedido(idPedido));
    }

    @Transactional
    public TicketGarantiaResponse create(TicketGarantiaRequest request) {
        return ticketMapper.toResponse(ticketRepository.save(ticketMapper.toEntity(request)));
    }

    @Transactional
    public TicketGarantiaResponse update(Integer id, TicketGarantiaRequest request) {
        TicketGarantia ticket = getTicketById(id);
        ticketMapper.updateEntity(request, ticket);
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Transactional
    public void deleteById(Integer id) {
        ticketRepository.delete(getTicketById(id));
    }

    private TicketGarantia getTicketById(Integer id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TicketGarantia", "ID", id));
    }
}
