package cl.pchardware.notificaciones.controller;

import java.util.List;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.notificaciones.dto.PlantillaCorreoRequest;
import cl.pchardware.notificaciones.dto.PlantillaCorreoResponse;
import cl.pchardware.notificaciones.service.PlantillaCorreoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plantillas-correo")
public class PlantillaCorreoController {

    private final PlantillaCorreoService plantillaService;

    @GetMapping
    public ResponseEntity<CollectionModel<PlantillaCorreoResponse>> findAll() {
        List<PlantillaCorreoResponse> plantillas = plantillaService.findAll();

        // Agrega links a cada elemento de la lista
        plantillas.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<PlantillaCorreoResponse> collection = CollectionModel.of(
            plantillas,
            linkTo(methodOn(PlantillaCorreoController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaCorreoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(plantillaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<PlantillaCorreoResponse> create(@Valid @RequestBody PlantillaCorreoRequest request) {
        PlantillaCorreoResponse creado = plantillaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaCorreoResponse> update(@PathVariable Integer id, @Valid @RequestBody PlantillaCorreoRequest request) {
        return ResponseEntity.ok(addLinks(plantillaService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        plantillaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private PlantillaCorreoResponse addLinks(PlantillaCorreoResponse plantilla) {
        Integer id = plantilla.getIdPlantilla();

        plantilla.add(linkTo(methodOn(PlantillaCorreoController.class).findById(id)).withSelfRel());
        
        plantilla.add(linkTo(methodOn(PlantillaCorreoController.class).create(null))
                .withRel("create").withTitle("POST - Crear plantilla"));

        plantilla.add(linkTo(methodOn(PlantillaCorreoController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar plantilla"));

        plantilla.add(linkTo(methodOn(PlantillaCorreoController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar plantilla"));

        plantilla.add(linkTo(methodOn(PlantillaCorreoController.class).findAll())
                .withRel("all").withTitle("GET - Listado de plantillas"));

        return plantilla;
    }
}
