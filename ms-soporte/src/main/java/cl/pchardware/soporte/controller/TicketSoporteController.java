package cl.pchardware.soporte.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.soporte.dto.TicketSoporteRequest;
import cl.pchardware.soporte.dto.TicketSoporteResponse;
import cl.pchardware.soporte.service.TicketSoporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets-soporte")
public class TicketSoporteController {

    private final TicketSoporteService ticketService;

    @GetMapping
    public ResponseEntity<CollectionModel<TicketSoporteResponse>> findAll() {
        List<TicketSoporteResponse> tickets = ticketService.findAll();
        tickets.forEach(this::addLinks);
        
        CollectionModel<TicketSoporteResponse> collection = CollectionModel.of(
            tickets,
            linkTo(methodOn(TicketSoporteController.class).findAll()).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketSoporteResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(ticketService.findById(id)));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CollectionModel<TicketSoporteResponse>> findByUsuario(@PathVariable Integer idUsuario) {
        List<TicketSoporteResponse> tickets = ticketService.findByUsuario(idUsuario);
        tickets.forEach(this::addLinks);
        
        CollectionModel<TicketSoporteResponse> collection = CollectionModel.of(
            tickets,
            linkTo(methodOn(TicketSoporteController.class).findByUsuario(idUsuario)).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    public ResponseEntity<List<TicketSoporteResponse>> findByUsuario(@PathVariable Integer idUsuario) {
        List<TicketSoporteResponse> tickets = ticketService.findAll().stream()
            .filter(ticket -> ticket.getIdUsuario().equals(idUsuario))
            .collect(Collectors.toList());
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<TicketSoporteResponse> create(@Valid @RequestBody TicketSoporteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(ticketService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketSoporteResponse> update(@PathVariable Integer id, @Valid @RequestBody TicketSoporteRequest request) {
        return ResponseEntity.ok(addLinks(ticketService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TicketSoporteResponse addLinks(TicketSoporteResponse ticket) {
        Integer id = ticket.getIdTicket();
        Integer idUsuario = ticket.getIdUsuario();

        ticket.add(linkTo(methodOn(TicketSoporteController.class).findById(id)).withSelfRel());
        
        if (idUsuario != null) {
            ticket.add(linkTo(methodOn(TicketSoporteController.class).findByUsuario(idUsuario))
                    .withRel("findByUsuario").withTitle("GET - Buscar por usuario"));
        }
        
        ticket.add(linkTo(methodOn(TicketSoporteController.class).create(null))
                .withRel("create").withTitle("POST - Crear ticket soporte"));
                
        ticket.add(linkTo(methodOn(TicketSoporteController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar ticket soporte"));
                
        ticket.add(linkTo(methodOn(TicketSoporteController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar ticket soporte"));
                
        ticket.add(linkTo(methodOn(TicketSoporteController.class).findAll())
                .withRel("all").withTitle("GET - Listado de tickets soporte"));
                
        return ticket;
    }
}
