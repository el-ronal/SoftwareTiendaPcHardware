package cl.pchardware.pagos.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.pagos.dto.MetodoPagoRequest;
import cl.pchardware.pagos.dto.MetodoPagoResponse;
import cl.pchardware.pagos.service.MetodoPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<CollectionModel<MetodoPagoResponse>> findAll() {
        List<MetodoPagoResponse> metodos = metodoPagoService.findAll();
        metodos.forEach(this::addLinks);
        
        CollectionModel<MetodoPagoResponse> collection = CollectionModel.of(
            metodos,
            linkTo(methodOn(MetodoPagoController.class).findAll()).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(metodoPagoService.findById(id)));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<MetodoPagoResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(addLinks(metodoPagoService.findByCodigo(codigo)));
    }

    @PostMapping
    public ResponseEntity<MetodoPagoResponse> create(@Valid @RequestBody MetodoPagoRequest request) {
        MetodoPagoResponse creado = metodoPagoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody MetodoPagoRequest request) {
        return ResponseEntity.ok(addLinks(metodoPagoService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        metodoPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MetodoPagoResponse addLinks(MetodoPagoResponse metodo) {
        Integer id = metodo.getIdMetodo();
        String codigo = metodo.getCodigo();
        
        metodo.add(linkTo(methodOn(MetodoPagoController.class).findById(id)).withSelfRel());
        
        if (codigo != null) {
            metodo.add(linkTo(methodOn(MetodoPagoController.class).findByCodigo(codigo))
                    .withRel("findByCodigo").withTitle("GET - Buscar por código"));
        }
        
        metodo.add(linkTo(methodOn(MetodoPagoController.class).create(null))
                .withRel("create").withTitle("POST - Crear método de pago"));
        
        metodo.add(linkTo(methodOn(MetodoPagoController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar método de pago"));
                
        metodo.add(linkTo(methodOn(MetodoPagoController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar método de pago"));
                
        metodo.add(linkTo(methodOn(MetodoPagoController.class).findAll())
                .withRel("all").withTitle("GET - Listado de métodos de pago"));
                
        return metodo;
    }
}
