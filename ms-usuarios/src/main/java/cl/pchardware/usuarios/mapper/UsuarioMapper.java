package cl.pchardware.usuarios.mapper;

import cl.pchardware.usuarios.dto.UsuarioRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring", uses = {RolMapper.class, PerfilMapper.class})
public interface UsuarioMapper {

    // Ignoramos: 
    // 1. ID (Generado automáticamente)
    // 2. Rol y Perfil (El Request trae un String y un DTO, el Service debe buscar/crear las entidades reales)
    // 3. PasswordHash (El Service tomará la contraseña del Request y la asignará de forma segura)
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    Usuario toEntity(UsuarioRequest request);

    UsuarioResponse toResponse(Usuario usuario);
    
    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);

    // Para la actualización directa sobre el objeto recuperado de la BD
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget Usuario usuario);
}