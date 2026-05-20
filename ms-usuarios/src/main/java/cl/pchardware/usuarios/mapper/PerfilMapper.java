package cl.pchardware.usuarios.mapper;

import cl.pchardware.usuarios.dto.PerfilRequest;
import cl.pchardware.usuarios.dto.PerfilResponse;
import cl.pchardware.usuarios.model.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    // Ignoramos el ID y la relación con Usuario (el Service enlazará ambas tablas)
    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Perfil toEntity(PerfilRequest request);

    PerfilResponse toResponse(Perfil perfil);

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(PerfilRequest request, @MappingTarget Perfil perfil);
}