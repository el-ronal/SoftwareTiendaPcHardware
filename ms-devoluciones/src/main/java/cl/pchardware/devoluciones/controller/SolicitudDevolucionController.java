package cl.pchardware.devoluciones.controller;

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

import cl.pchardware.devoluciones.dto.SolicitudDevolucionRequest;
import cl.pchardware.devoluciones.dto.SolicitudDevolucionResponse;
import cl.pchardware.devoluciones.service.SolicitudDevolucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devoluciones")
@Tag(name = "Solicitudes de Devolución", description = "API para la gestión del catálogo de solicitudes de devolución")
public class SolicitudDevolucionController {

    private final SolicitudDevolucionService solicitudService;

    @Operation(summary = "Obtener todas las solicitudes de devolución", description = "Retorna la lista completa de solicitudes de devolución del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SolicitudDevolucionResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<SolicitudDevolucionResponse>> findAll() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @Operation(summary = "Obtener solicitud de devolución por ID", description = "Retorna una solicitud de devolución según su identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = SolicitudDevolucionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> findById(
            @Parameter(description = "ID de la solicitud de devolución", required = true, example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.findById(id));
    }

    @Operation(summary = "Obtener solicitud de devolución por ISBN", description = "Retorna una solicitud de devolución según su código ISBN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = SolicitudDevolucionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content)
    })
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<SolicitudDevolucionResponse>> findByPedido(
            @Parameter(description = "ID del pedido", required = true, example = "1") @PathVariable Integer idPedido) {
        return ResponseEntity.ok(solicitudService.findByPedido(idPedido));
    }

    @Operation(summary = "Crear un nuevo solicitud de devolución", description = "Registra un nuevo solicitud de devolución en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Solicitud de devolución creada exitosamente", content = @Content(schema = @Schema(implementation = SolicitudDevolucionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SolicitudDevolucionResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la solicitud de devolución a crear", required = true, content = @Content(schema = @Schema(implementation = SolicitudDevolucionRequest.class))) @Valid @RequestBody SolicitudDevolucionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.create(request));
    }

    @Operation(summary = "Actualizar un libro", description = "Actualiza los datos de un libro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud de devolución actualizada exitosamente", content = @Content(schema = @Schema(implementation = SolicitudDevolucionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Solicitud de devolución no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> update(
            @Parameter(description = "ID de la solicitud de devolución a actualizar", required = true, example = "1") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos de la solicitud de devolución", required = true, content = @Content(schema = @Schema(implementation = SolicitudDevolucionRequest.class))) @Valid @RequestBody SolicitudDevolucionRequest request) {
        return ResponseEntity.ok(solicitudService.update(id, request));
    }

    @Operation(summary = "Eliminar un libro", description = "Elimina un libro del catálogo por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Libro eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del libro a eliminar", required = true, example = "1") @PathVariable Integer id) {
        solicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
