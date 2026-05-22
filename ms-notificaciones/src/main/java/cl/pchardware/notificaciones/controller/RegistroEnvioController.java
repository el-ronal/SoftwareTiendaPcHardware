package cl.pchardware.notificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.notificaciones.dto.RegistroEnvioRequest;
import cl.pchardware.notificaciones.dto.RegistroEnvioResponse;
import cl.pchardware.notificaciones.service.RegistroEnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/registros-envio")
@RequiredArgsConstructor
public class RegistroEnvioController {

    private final RegistroEnvioService registroService;

    @GetMapping
    public ResponseEntity<List<RegistroEnvioResponse>> findAll() {
        List<RegistroEnvioResponse> respuestas = registroService.findAll();
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroEnvioResponse> findById(@PathVariable Integer id) {
        RegistroEnvioResponse respuesta = registroService.findById(id);
        return ResponseEntity.ok(respuesta);
    }

    // Endpoint adicional para buscar el historial de un mensaje específico
    @GetMapping("/mensaje/{idMensaje}")
    public ResponseEntity<List<RegistroEnvioResponse>> findByIdMensaje(@PathVariable Integer idMensaje) {
        List<RegistroEnvioResponse> respuestas = registroService.findByIdMensaje(idMensaje);
        return ResponseEntity.ok(respuestas);
    }

    @PostMapping
    public ResponseEntity<RegistroEnvioResponse> create(@Valid @RequestBody RegistroEnvioRequest request) {
        RegistroEnvioResponse respuesta = registroService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroEnvioResponse> update(
            @PathVariable Integer id, 
            @Valid @RequestBody RegistroEnvioRequest request) {
        
        RegistroEnvioResponse respuesta = registroService.update(id, request);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        registroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}