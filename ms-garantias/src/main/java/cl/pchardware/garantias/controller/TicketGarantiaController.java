package cl.pchardware.garantias.controller;

import java.util.List;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.garantias.dto.TicketGarantiaRequest;
import cl.pchardware.garantias.dto.TicketGarantiaResponse;
import cl.pchardware.garantias.service.TicketGarantiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets-garantia")
public class TicketGarantiaController {

    private final TicketGarantiaService ticketService;

    @GetMapping
    public ResponseEntity<CollectionModel<TicketGarantiaResponse>> findAll() {
        List<TicketGarantiaResponse> tickets = ticketService.findAll();

        // Agrega links a cada elemento de la lista
        tickets.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<TicketGarantiaResponse> collection = CollectionModel.of(
            tickets,
            linkTo(methodOn(TicketGarantiaController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketGarantiaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(ticketService.findById(id)));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<CollectionModel<TicketGarantiaResponse>> findByPedido(@PathVariable Integer idPedido) {
        List<TicketGarantiaResponse> tickets = ticketService.findByPedido(idPedido);

        // Agrega links a cada elemento de la lista
        tickets.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<TicketGarantiaResponse> collection = CollectionModel.of(
            tickets,
            linkTo(methodOn(TicketGarantiaController.class).findByPedido(idPedido)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<TicketGarantiaResponse> create(@Valid @RequestBody TicketGarantiaRequest request) {
        TicketGarantiaResponse creado = ticketService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketGarantiaResponse> update(@PathVariable Integer id, @Valid @RequestBody TicketGarantiaRequest request) {
        return ResponseEntity.ok(addLinks(ticketService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TicketGarantiaResponse addLinks(TicketGarantiaResponse ticket) {
        Integer id = ticket.getIdTicket();
        Integer idPedido = ticket.getIdPedido();

        ticket.add(linkTo(methodOn(TicketGarantiaController.class).findById(id)).withSelfRel());
        
        if (idPedido != null) {
            ticket.add(linkTo(methodOn(TicketGarantiaController.class).findByPedido(idPedido))
                    .withRel("findByPedido").withTitle("GET - Buscar por pedido"));
        }
        
        ticket.add(linkTo(methodOn(TicketGarantiaController.class).create(null))
                .withRel("create").withTitle("POST - Crear ticket"));

        ticket.add(linkTo(methodOn(TicketGarantiaController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar ticket"));

        ticket.add(linkTo(methodOn(TicketGarantiaController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar ticket"));

        ticket.add(linkTo(methodOn(TicketGarantiaController.class).findAll())
                .withRel("all").withTitle("GET - Listado de tickets"));

        return ticket;
    }
}
