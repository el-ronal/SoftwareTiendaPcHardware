package cl.pchardware.soporte.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.soporte.dto.TicketSoporteRequest;
import cl.pchardware.soporte.dto.TicketSoporteResponse;
import cl.pchardware.soporte.mapper.TicketSoporteMapper;
import cl.pchardware.soporte.model.TicketSoporte;
import cl.pchardware.soporte.repository.TicketSoporteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketSoporteService {

    private final TicketSoporteRepository ticketRepository;
    private final TicketSoporteMapper ticketMapper;

    @Transactional(readOnly = true)
    public List<TicketSoporteResponse> findAll() {
        return ticketMapper.toResponseList(ticketRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TicketSoporteResponse findById(Integer id) {
        return ticketMapper.toResponse(getTicketById(id));
    }

    @Transactional(readOnly = true)
    public List<TicketSoporteResponse> findByUsuario(Integer idUsuario) {
        return ticketMapper.toResponseList(ticketRepository.findByIdUsuario(idUsuario));
    }

    @Transactional
    public TicketSoporteResponse create(TicketSoporteRequest request) {
        return ticketMapper.toResponse(ticketRepository.save(ticketMapper.toEntity(request)));
    }

    @Transactional
    public TicketSoporteResponse update(Integer id, TicketSoporteRequest request) {
        TicketSoporte ticket = getTicketById(id);
        ticketMapper.updateEntity(request, ticket);
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Transactional
    public void deleteById(Integer id) {
        ticketRepository.delete(getTicketById(id));
    }

    private TicketSoporte getTicketById(Integer id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TicketSoporte", "ID", id));
    }
}
