package cl.pchardware.usuarios.controller;

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

import cl.pchardware.usuarios.dto.UsuarioRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioResponse>> findAll() {
        List<UsuarioResponse> usuarios = usuarioService.findAll();
        usuarios.forEach(this::addLinks);
        
        CollectionModel<UsuarioResponse> collection = CollectionModel.of(
            usuarios,
            linkTo(methodOn(UsuarioController.class).findAll()).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(usuarioService.findById(id)));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<CollectionModel<UsuarioResponse>> findByEstado(@PathVariable String estado) {
        List<UsuarioResponse> usuarios = usuarioService.findByEstado(estado);
        usuarios.forEach(this::addLinks);
        
        CollectionModel<UsuarioResponse> collection = CollectionModel.of(
            usuarios,
            linkTo(methodOn(UsuarioController.class).findByEstado(estado)).withSelfRel()
        );
        
        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse creado = usuarioService.create(request);
        // Retornamos HTTP 201 (Created) cuando se inserta un recurso exitosamente
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(addLinks(usuarioService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);
        // Retornamos HTTP 204 (No Content) para confirmar el borrado sin enviar un cuerpo
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponse addLinks(UsuarioResponse usuario) {
        Long id = usuario.getIdUsuario();
        String estado = usuario.getEstado();

        usuario.add(linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel());
        
        if (estado != null) {
            usuario.add(linkTo(methodOn(UsuarioController.class).findByEstado(estado))
                    .withRel("findByEstado").withTitle("GET - Buscar por estado"));
        }
        
        usuario.add(linkTo(methodOn(UsuarioController.class).create(null))
                .withRel("create").withTitle("POST - Crear usuario"));
                
        usuario.add(linkTo(methodOn(UsuarioController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar usuario"));
                
        usuario.add(linkTo(methodOn(UsuarioController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar usuario"));
                
        usuario.add(linkTo(methodOn(UsuarioController.class).findAll())
                .withRel("all").withTitle("GET - Listado de usuarios"));
                
        return usuario;
    }
}