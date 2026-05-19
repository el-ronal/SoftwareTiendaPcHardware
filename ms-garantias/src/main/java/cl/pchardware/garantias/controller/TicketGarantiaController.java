package cl.pchardware.garantias.controller;

import java.util.List;

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
    public ResponseEntity<List<TicketGarantiaResponse>> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketGarantiaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<TicketGarantiaResponse>> findByPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(ticketService.findByPedido(idPedido));
    }

    @PostMapping
    public ResponseEntity<TicketGarantiaResponse> create(@Valid @RequestBody TicketGarantiaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketGarantiaResponse> update(@PathVariable Integer id, @Valid @RequestBody TicketGarantiaRequest request) {
        return ResponseEntity.ok(ticketService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
