package cl.pchardware.catalogo.controller;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<CollectionModel<ProductoResponse>> findAll() {
        List<ProductoResponse> productos = productoService.findAll();

        // Agrega links a cada elemento de la lista
        productos.forEach(this::addLinks);

        // CollectionModel envuelve la lista y le agrega un link "self" al coleccion completa
        CollectionModel<ProductoResponse> collection = CollectionModel.of(
            productos,
            linkTo(methodOn(ProductoController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductoResponse> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(addLinks(productoService.findBySku(sku)));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(productoService.create(request)));
    }

    @PutMapping("/{sku}")
    public ResponseEntity<ProductoResponse> update(
            @PathVariable String sku, 
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(addLinks(productoService.update(sku, request)));
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteBySku(@PathVariable String sku) {
        productoService.deleteBySku(sku);
        return ResponseEntity.noContent().build();
    }

    private ProductoResponse addLinks(ProductoResponse producto) {
        String sku = producto.getSku();

        producto.add(linkTo(methodOn(ProductoController.class).findBySku(sku)).withSelfRel());
        
        producto.add(linkTo(methodOn(ProductoController.class).create(null))
                .withRel("create").withTitle("POST - Crear producto"));

        producto.add(linkTo(methodOn(ProductoController.class).update(sku, null))
                .withRel("update").withTitle("PUT - Actualizar producto"));

        producto.add(linkTo(methodOn(ProductoController.class).deleteBySku(sku))
                .withRel("delete").withTitle("DELETE - Eliminar producto"));

        // Para el link de categoría no existe un método exacto con param fijo,
        // se construye el template manualmente para mostrar que acepta {categoriaId}.
        Link categoriaLink = Link.of(
            linkTo(ProductoController.class).toUri() + "/" + sku + "/categoria/{categoriaId}",
            "agregar-categoria"
        ).withTitle("POST - Asociar categoría al producto");
        producto.add(categoriaLink);

        producto.add(linkTo(methodOn(ProductoController.class).findAll())
                .withRel("all").withTitle("GET - Listado de productos"));

        return producto;
    }
}