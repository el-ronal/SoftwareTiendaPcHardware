package cl.pchardware.soporte.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.soporte.dto.TicketSoporteRequest;
import cl.pchardware.soporte.dto.TicketSoporteResponse;
import cl.pchardware.soporte.mapper.TicketSoporteMapper;
import cl.pchardware.soporte.model.TicketSoporte;
import cl.pchardware.soporte.repository.TicketSoporteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
public class TicketSoporteService {

    private final TicketSoporteRepository ticketRepository;
    private final TicketSoporteMapper ticketMapper;

    @Transactional(readOnly = true)
    public List<TicketSoporteResponse> findAll() {

        return ticketMapper.toResponseList(
                ticketRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public TicketSoporteResponse findById(Integer id) {

        return ticketMapper.toResponse(
                getTicketById(id)
        );
    }

        @Transactional(readOnly = true)
        public List<TicketSoporteResponse> findByIdUsuario(Integer idUsuario) {

                Objects.requireNonNull(idUsuario, "ID Usuario no puede ser nulo");

                List<TicketSoporte> tickets = ticketRepository.findByIdUsuario(idUsuario);

                return ticketMapper.toResponseList(tickets);
        }

    @Transactional
    public TicketSoporteResponse create(
            TicketSoporteRequest request
    ) {

        TicketSoporte ticket =
                ticketMapper.toEntity(request);

        Objects.requireNonNull(ticket, "Ticket Soporte no puede ser nulo");

        return ticketMapper.toResponse(
                ticketRepository.save(ticket)
        );
    }

    @Transactional
    public TicketSoporteResponse update(
            Integer id,
            TicketSoporteRequest request
    ) {

        TicketSoporte ticket =
                getTicketById(id);

        ticketMapper.updateEntity(request, ticket);

        return ticketMapper.toResponse(
                ticketRepository.save(ticket)
        );
    }

    @Transactional
    public void deleteById(Integer id) {

        ticketRepository.delete(
                getTicketById(id)
        );
    }

        private @NonNull TicketSoporte getTicketById(Integer id) {

        Objects.requireNonNull(id, "ID no puede ser nulo");

        TicketSoporte ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "TicketSoporte",
                                "ID",
                                id
                        )
                );

        return Objects.requireNonNull(ticket);
    }
}