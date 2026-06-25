package cl.pchardware.armado.controller;

import java.util.List;


import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import cl.pchardware.armado.dto.TecnicoArmadoRequest;
import cl.pchardware.armado.dto.TecnicoArmadoResponse;
import cl.pchardware.armado.service.TecnicoArmadoService;
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
@RequestMapping("/api/v1/tecnicos-armado")
@Tag(name = "Técnicos de Armado", description = "API para la gestión del catálogo de técnicos de armado")
public class TecnicoArmadoController {

    private final TecnicoArmadoService tecnicoService;

    @Operation(summary = "Obtener todos los técnicos de armado", description = "Retorna la lista completa de técnicos de armado del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TecnicoArmadoResponse.class))))
    })

    @GetMapping
    public ResponseEntity<CollectionModel<TecnicoArmadoResponse>> findAll() {
        List<TecnicoArmadoResponse> tecnicos = tecnicoService.findAll();

        // Agrega links a cada elemento de la lista
        tecnicos.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<TecnicoArmadoResponse> collection = CollectionModel.of(
            tecnicos,
            linkTo(methodOn(TecnicoArmadoController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener técnico de armado por ID", description = "Retorna un técnico de armado según su identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Técnico encontrado", content = @Content(schema = @Schema(implementation = TecnicoArmadoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TecnicoArmadoResponse> findById(
            @Parameter(description = "ID del técnico de armado", required = true, example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(tecnicoService.findById(id)));
    }

    @Operation(summary = "Crear un nuevo técnico de armado", description = "Registra un nuevo técnico de armado en el catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Técnico creado exitosamente", content = @Content(schema = @Schema(implementation = TecnicoArmadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TecnicoArmadoResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del técnico de armado a crear", required = true, content = @Content(schema = @Schema(implementation = TecnicoArmadoRequest.class))) @Valid @RequestBody TecnicoArmadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(tecnicoService.create(request)));
    }

    @Operation(summary = "Actualizar un técnico de armado", description = "Actualiza los datos de un técnico de armado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Técnico actualizado exitosamente", content = @Content(schema = @Schema(implementation = TecnicoArmadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TecnicoArmadoResponse> update(
            @Parameter(description = "ID del técnico de armado a actualizar", required = true, example = "1") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del técnico de armado", required = true, content = @Content(schema = @Schema(implementation = TecnicoArmadoRequest.class))) @Valid @RequestBody TecnicoArmadoRequest request) {
        return ResponseEntity.ok(addLinks(tecnicoService.update(id, request)));
    }

    @Operation(summary = "Eliminar un técnico de armado", description = "Elimina un técnico de armado del catálogo por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Técnico eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del técnico de armado a eliminar", required = true, example = "1") 
            @PathVariable Integer id) {

        tecnicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TecnicoArmadoResponse addLinks(TecnicoArmadoResponse tecnico) {
        Integer id = tecnico.getIdTecnico();

        tecnico.add(linkTo(methodOn(TecnicoArmadoController.class).findById(id)).withSelfRel());
        
        tecnico.add(linkTo(methodOn(TecnicoArmadoController.class).create(null))
                .withRel("create").withTitle("POST - Crear tecnico"));

        tecnico.add(linkTo(methodOn(TecnicoArmadoController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar tecnico"));

        tecnico.add(linkTo(methodOn(TecnicoArmadoController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar tecnico"));
        
        tecnico.add(linkTo(methodOn(TecnicoArmadoController.class).findAll())
                .withRel("all").withTitle("GET - Obtener todos los tecnicos"));

        return tecnico;
    }
}
