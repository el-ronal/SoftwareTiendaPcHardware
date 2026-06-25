package cl.pchardware.envios.controller;

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

import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import cl.pchardware.envios.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierController {

    private final CourierService courierService;

    @GetMapping
    public ResponseEntity<CollectionModel<CourierResponse>> findAll() {
        List<CourierResponse> couriers = courierService.findAll();

        // Agrega links a cada elemento de la lista
        couriers.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<CourierResponse> collection = CollectionModel.of(
            couriers,
            linkTo(methodOn(CourierController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(courierService.findById(id)));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CourierResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(addLinks(courierService.findByCodigo(codigo)));
    }

    @PostMapping
    public ResponseEntity<CourierResponse> create(@Valid @RequestBody CourierRequest request) {
        CourierResponse creado = courierService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourierResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody CourierRequest request) {
        return ResponseEntity.ok(addLinks(courierService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        courierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CourierResponse addLinks(CourierResponse courier) {
        Integer id = courier.getIdCourier();
        String codigo = courier.getCodigo();

        courier.add(linkTo(methodOn(CourierController.class).findById(id)).withSelfRel());
        
        if (codigo != null) {
            courier.add(linkTo(methodOn(CourierController.class).findByCodigo(codigo))
                    .withRel("findByCodigo").withTitle("GET - Buscar por código"));
        }
        
        courier.add(linkTo(methodOn(CourierController.class).create(null))
                .withRel("create").withTitle("POST - Crear courier"));

        courier.add(linkTo(methodOn(CourierController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar courier"));

        courier.add(linkTo(methodOn(CourierController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar courier"));
        
        courier.add(linkTo(methodOn(CourierController.class).findAll())
                .withRel("all").withTitle("GET - Listado de couriers"));

        return courier;
    }
}
