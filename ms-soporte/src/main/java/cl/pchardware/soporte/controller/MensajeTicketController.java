package cl.pchardware.soporte.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.soporte.dto.MensajeTicketRequest;
import cl.pchardware.soporte.dto.MensajeTicketResponse;
import cl.pchardware.soporte.service.MensajeTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mensajes-ticket")
public class MensajeTicketController {

    private final MensajeTicketService mensajeService;

    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<MensajeTicketResponse>> findByTicket(@PathVariable Integer idTicket) {
        return ResponseEntity.ok(mensajeService.findByTicket(idTicket));
    }

    @PostMapping
    public ResponseEntity<MensajeTicketResponse> create(@Valid @RequestBody MensajeTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.create(request));
    }
}
