package cl.pchardware.usuarios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.usuarios.dto.UsuarioRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    Usuario toEntity(UsuarioRequest request);

    @Mapping(source = "rol.idRol", target = "idRol")
    @Mapping(source = "rol.nombre", target = "nombreRol")
    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget Usuario usuario);
}
