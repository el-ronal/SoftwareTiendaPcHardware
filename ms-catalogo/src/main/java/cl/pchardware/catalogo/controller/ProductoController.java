package cl.pchardware.catalogo.controller;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "API para la gestión del catálogo de productos")

public class ProductoController {

    private final ProductoService productoService;

    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista completa de productos del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @Operation(summary = "Obtener producto por sku", description = "Retorna un producto según su identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = ProductoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{sku}")
    public ResponseEntity<ProductoResponse> findBySku(
            @Parameter(description = "SKU del producto", required = true, example = "PROD001") @PathVariable String sku) {
        return ResponseEntity.ok(productoService.findBySku(sku));
    }

    @Operation(summary = "Crear un  producto", description = "Registra un nuevo producto en el catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente", content = @Content(schema = @Schema(implementation = ProductoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductoResponse> create(
         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del producto a crear", required = true,
                content = @Content(schema = @Schema(implementation = ProductoRequest.class)))
        @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(request));
    }
     @Operation(summary = "Actualizar un producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("/{sku}")
    public ResponseEntity<ProductoResponse> update(
            @Parameter(description = "SKU del producto a actualizar", required = true, example = "PROD001")
            @PathVariable String sku,
             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del producto", required = true,
                content = @Content(schema = @Schema(implementation = ProductoRequest.class)))
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.update(sku, request));
    }
     @Operation(summary = "Eliminar un producto", description = "Elimina un producto del catálogo por su SKU")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteBySku(
        @Parameter(description = "SKU del producto a eliminar", required = true, example = "PROD001")
        @PathVariable String sku) {
        productoService.deleteBySku(sku);
        return ResponseEntity.noContent().build();
    }
}