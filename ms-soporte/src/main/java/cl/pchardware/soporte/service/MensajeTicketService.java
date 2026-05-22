package cl.pchardware.soporte.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.soporte.dto.MensajeTicketRequest;
import cl.pchardware.soporte.dto.MensajeTicketResponse;
import cl.pchardware.soporte.mapper.MensajeTicketMapper;
import cl.pchardware.soporte.model.MensajeTicket;
import cl.pchardware.soporte.model.TicketSoporte;
import cl.pchardware.soporte.repository.MensajeTicketRepository;
import cl.pchardware.soporte.repository.TicketSoporteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MensajeTicketService {

    private final MensajeTicketRepository mensajeRepository;
    private final TicketSoporteRepository ticketRepository;
    private final MensajeTicketMapper mensajeMapper;

    @Transactional(readOnly = true)
    public List<MensajeTicketResponse> findByTicket(Integer idTicket) {
        return mensajeMapper.toResponseList(mensajeRepository.findByTicketSoporte_IdTicket(idTicket));
    }

    @Transactional
    public MensajeTicketResponse create(MensajeTicketRequest request) {
        TicketSoporte ticket = ticketRepository.findById(request.getIdTicket())
                .orElseThrow(() -> new EntityNotFoundException("TicketSoporte", "ID", request.getIdTicket()));
        MensajeTicket mensaje = mensajeMapper.toEntity(request);
        mensaje.setTicketSoporte(ticket);
        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }
}
