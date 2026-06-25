package cl.pchardware.armado.controller;

import java.util.List;


import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.armado.dto.TecnicoArmadoRequest;
import cl.pchardware.armado.dto.TecnicoArmadoResponse;
import cl.pchardware.armado.service.TecnicoArmadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tecnicos-armado")
public class TecnicoArmadoController {

    private final TecnicoArmadoService tecnicoService;

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

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoArmadoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(tecnicoService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<TecnicoArmadoResponse> create(@Valid @RequestBody TecnicoArmadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(tecnicoService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoArmadoResponse> update(@PathVariable Integer id, @Valid @RequestBody TecnicoArmadoRequest request) {
        return ResponseEntity.ok(addLinks(tecnicoService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
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
