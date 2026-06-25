package cl.pchardware.devoluciones.controller;

import java.util.List;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.devoluciones.dto.SolicitudDevolucionRequest;
import cl.pchardware.devoluciones.dto.SolicitudDevolucionResponse;
import cl.pchardware.devoluciones.service.SolicitudDevolucionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devoluciones")
public class SolicitudDevolucionController {

    private final SolicitudDevolucionService solicitudService;

    @GetMapping
    public ResponseEntity<CollectionModel<SolicitudDevolucionResponse>> findAll() {
        List<SolicitudDevolucionResponse> devoluciones = solicitudService.findAll();

        // Agrega links a cada elemento de la lista
        devoluciones.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<SolicitudDevolucionResponse> collection = CollectionModel.of(
            devoluciones,
            linkTo(methodOn(SolicitudDevolucionController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(solicitudService.findById(id)));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<CollectionModel<SolicitudDevolucionResponse>> findByPedido(@PathVariable Integer idPedido) {
        List<SolicitudDevolucionResponse> devoluciones = solicitudService.findByPedido(idPedido);

        // Agrega links a cada elemento de la lista
        devoluciones.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<SolicitudDevolucionResponse> collection = CollectionModel.of(
            devoluciones,
            linkTo(methodOn(SolicitudDevolucionController.class).findByPedido(idPedido)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<SolicitudDevolucionResponse> create(@Valid @RequestBody SolicitudDevolucionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(solicitudService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> update(@PathVariable Integer id, @Valid @RequestBody SolicitudDevolucionRequest request) {
        return ResponseEntity.ok(addLinks(solicitudService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        solicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private SolicitudDevolucionResponse addLinks(SolicitudDevolucionResponse devolucion) {
        Integer id = devolucion.getIdDevolucion();
        Integer idPedido = devolucion.getIdPedido();

        devolucion.add(linkTo(methodOn(SolicitudDevolucionController.class).findById(id)).withSelfRel());
        
        if (idPedido != null) {
            devolucion.add(linkTo(methodOn(SolicitudDevolucionController.class).findByPedido(idPedido))
                    .withRel("findByPedido").withTitle("GET - Buscar por pedido"));
        }
        
        devolucion.add(linkTo(methodOn(SolicitudDevolucionController.class).create(null))
                .withRel("create").withTitle("POST - Crear devolucion"));

        devolucion.add(linkTo(methodOn(SolicitudDevolucionController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar devolucion"));

        devolucion.add(linkTo(methodOn(SolicitudDevolucionController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar devolucion"));

        devolucion.add(linkTo(methodOn(SolicitudDevolucionController.class).findAll())
                .withRel("all").withTitle("GET - Listado de devoluciones"));

        return devolucion;
    }
}
