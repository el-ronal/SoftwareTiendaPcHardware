package cl.pchardware.tasacion.controller;

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

import cl.pchardware.tasacion.dto.EvaluacionTecnicaRequest;
import cl.pchardware.tasacion.dto.EvaluacionTecnicaResponse;
import cl.pchardware.tasacion.service.EvaluacionTecnicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/evaluaciones-tecnicas")
@RequiredArgsConstructor
public class EvaluacionTecnicaController {

    private final EvaluacionTecnicaService evaluacionService;

    @GetMapping
    public ResponseEntity<CollectionModel<EvaluacionTecnicaResponse>> findAll() {
        List<EvaluacionTecnicaResponse> respuestas = evaluacionService.findAll();
        respuestas.forEach(this::addLinks);
        
        CollectionModel<EvaluacionTecnicaResponse> collection = CollectionModel.of(
            respuestas,
            linkTo(methodOn(EvaluacionTecnicaController.class).findAll()).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionTecnicaResponse> findById(@PathVariable Integer id) {
        EvaluacionTecnicaResponse respuesta = evaluacionService.findById(id);
        return ResponseEntity.ok(addLinks(respuesta));
    }

    @PostMapping
    public ResponseEntity<EvaluacionTecnicaResponse> create(@Valid @RequestBody EvaluacionTecnicaRequest request) {
        EvaluacionTecnicaResponse respuesta = evaluacionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(respuesta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionTecnicaResponse> update(
            @PathVariable Integer id, 
            @Valid @RequestBody EvaluacionTecnicaRequest request) {
        
        EvaluacionTecnicaResponse respuesta = evaluacionService.update(id, request);
        return ResponseEntity.ok(addLinks(respuesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        evaluacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EvaluacionTecnicaResponse addLinks(EvaluacionTecnicaResponse respuesta) {
        Integer id = respuesta.getIdEvaluacion();

        respuesta.add(linkTo(methodOn(EvaluacionTecnicaController.class).findById(id)).withSelfRel());
        
        respuesta.add(linkTo(methodOn(EvaluacionTecnicaController.class).create(null))
                .withRel("create").withTitle("POST - Crear evaluación técnica"));
                
        respuesta.add(linkTo(methodOn(EvaluacionTecnicaController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar evaluación técnica"));
                
        respuesta.add(linkTo(methodOn(EvaluacionTecnicaController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar evaluación técnica"));
                
        respuesta.add(linkTo(methodOn(EvaluacionTecnicaController.class).findAll())
                .withRel("all").withTitle("GET - Listado de evaluaciones técnicas"));
                
        return respuesta;
    }
}