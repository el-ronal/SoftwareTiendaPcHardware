package cl.pchardware.soporte.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.soporte.dto.EncuestaSatisfaccionRequest;
import cl.pchardware.soporte.dto.EncuestaSatisfaccionResponse;
import cl.pchardware.soporte.service.EncuestaSatisfaccionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/encuestas-satisfaccion")
@RequiredArgsConstructor
public class EncuestaSatisfaccionController {

    private final EncuestaSatisfaccionService encuestaService;

    @GetMapping
    public List<EncuestaSatisfaccionResponse> findAll() {
        return encuestaService.findAll();
    }

    @GetMapping("/{id}")
    public EncuestaSatisfaccionResponse findById(
            @PathVariable Integer id
    ) {
        return encuestaService.findById(id);
    }

    @GetMapping("/ticket/{idTicket}")
    public EncuestaSatisfaccionResponse findByTicket(
            @PathVariable Integer idTicket
    ) {
        return encuestaService.findByTicket(idTicket);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EncuestaSatisfaccionResponse create(
            @Valid @RequestBody EncuestaSatisfaccionRequest request
    ) {
        return encuestaService.create(request);
    }

    @PutMapping("/{id}")
    public EncuestaSatisfaccionResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody EncuestaSatisfaccionRequest request
    ) {
        return encuestaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Integer id
    ) {
        encuestaService.deleteById(id);
    }
}