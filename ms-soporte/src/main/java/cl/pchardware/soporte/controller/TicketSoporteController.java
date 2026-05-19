package cl.pchardware.soporte.controller;

import java.util.List;

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
    public ResponseEntity<List<TicketSoporteResponse>> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketSoporteResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<TicketSoporteResponse>> findByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(ticketService.findByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<TicketSoporteResponse> create(@Valid @RequestBody TicketSoporteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketSoporteResponse> update(@PathVariable Integer id, @Valid @RequestBody TicketSoporteRequest request) {
        return ResponseEntity.ok(ticketService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
