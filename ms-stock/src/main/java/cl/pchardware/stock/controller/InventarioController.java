package cl.pchardware.stock.controller;

import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping("/sku/{sku}")
    public ResponseEntity<CollectionModel<InventarioResponse>> findBySku(@PathVariable String sku) {
        List<InventarioResponse> inventarios = inventarioService.findBySku(sku);
        inventarios.forEach(this::addLinks);
        
        CollectionModel<InventarioResponse> collection = CollectionModel.of(
            inventarios,
            linkTo(methodOn(InventarioController.class).findBySku(sku)).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<InventarioResponse> create(@Valid @RequestBody InventarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(inventarioService.create(request)));
    }

    private InventarioResponse addLinks(InventarioResponse inventario) {
        String sku = inventario.getSkuProducto();

        if (sku != null) {
            inventario.add(linkTo(methodOn(InventarioController.class).findBySku(sku)).withSelfRel());
        }

        inventario.add(linkTo(methodOn(InventarioController.class).create(null))
                .withRel("create").withTitle("POST - Crear inventario"));

        return inventario;
    }
}