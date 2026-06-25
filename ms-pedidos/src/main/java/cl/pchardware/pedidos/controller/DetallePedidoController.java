package cl.pchardware.pedidos.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import cl.pchardware.pedidos.dto.DetallePedidoRequest;
import cl.pchardware.pedidos.dto.DetallePedidoResponse;
import cl.pchardware.pedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/detalles-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<CollectionModel<DetallePedidoResponse>> findAll() {
        List<DetallePedidoResponse> detalles = detallePedidoService.findAll();
        detalles.forEach(this::addLinks);
        
        CollectionModel<DetallePedidoResponse> collection = CollectionModel.of(
            detalles,
            linkTo(methodOn(DetallePedidoController.class).findAll()).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(detallePedidoService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoResponse> create(@Valid @RequestBody DetallePedidoRequest request) {
        DetallePedidoResponse creado = detallePedidoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody DetallePedidoRequest request) {
        return ResponseEntity.ok(addLinks(detallePedidoService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        detallePedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private DetallePedidoResponse addLinks(DetallePedidoResponse detalle) {
        Integer id = detalle.getIdDetalle();
        
        detalle.add(linkTo(methodOn(DetallePedidoController.class).findById(id)).withSelfRel());
        
        detalle.add(linkTo(methodOn(DetallePedidoController.class).create(null))
                .withRel("create").withTitle("POST - Crear detalle de pedido"));
                
        detalle.add(linkTo(methodOn(DetallePedidoController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar detalle de pedido"));
                
        detalle.add(linkTo(methodOn(DetallePedidoController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar detalle de pedido"));
                
        detalle.add(linkTo(methodOn(DetallePedidoController.class).findAll())
                .withRel("all").withTitle("GET - Listado de detalles de pedido"));
                
        return detalle;
    }
}
