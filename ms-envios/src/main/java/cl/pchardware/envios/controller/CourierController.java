package cl.pchardware.envios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import cl.pchardware.envios.service.CourierService;
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
@RequestMapping("/api/v1/couriers")
@Tag(name = "Couriers", description = "API para la gestión de couriers")
public class CourierController {

    private final CourierService courierService;

    @Operation(summary = "Obtener  todos los couriers", description = "Retorna la lista completa de couriers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourierResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<CourierResponse>> findAll() {
        return ResponseEntity.ok(courierService.findAll());
    }

    @Operation(summary = "Obtener courier por ID", description = "Retorna un courier según su identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courier encontrado", content = @Content(schema = @Schema(implementation = CourierResponse.class))),
            @ApiResponse(responseCode = "404", description = "Courier no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourierResponse> findById(
            @Parameter(description = "ID del courier", required = true, example = "1") @PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(courierService.findById(id));
    }

    @Operation(summary = "Obtener courier por código", description = "Retorna un courier según su código único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courier encontrado", content = @Content(schema = @Schema(implementation = CourierResponse.class))),
            @ApiResponse(responseCode = "404", description = "Courier no encontrado", content = @Content)
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CourierResponse> findByCodigo(
            @Parameter(description = "Código del courier", required = true, example = "COUR123") @PathVariable String codigo) {
        return ResponseEntity.ok(courierService.findByCodigo(codigo));
    }

    @Operation(summary = "Crear un nuevo courier", description = "Registra un nuevo courier en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Courier creado exitosamente", content = @Content(schema = @Schema(implementation = CourierResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CourierResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del courier a crear", required = true, content = @Content(schema = @Schema(implementation = CourierRequest.class))) @Valid @RequestBody CourierRequest request) {
        CourierResponse creado = courierService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un courier", description = "Actualiza los datos de un courier existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courier actualizado exitosamente", content = @Content(schema = @Schema(implementation = CourierResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Courier no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CourierResponse> update(
            @Parameter(description = "ID del courier a actualizar", required = true, example = "1") @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del courier", required = true, content = @Content(schema = @Schema(implementation = CourierRequest.class))) @PathVariable @NonNull Integer id,
            @Valid @RequestBody CourierRequest request) {
        return ResponseEntity.ok(courierService.update(id, request));
    }

    @Operation(summary = "Eliminar un courier", description = "Elimina un courier del sistema por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Courier eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Courier no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del courier a eliminar", required = true, example = "1") @PathVariable @NonNull Integer id) {
        courierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
