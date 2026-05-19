package cl.pchardware.usuarios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.usuarios.dto.RolRequest;
import cl.pchardware.usuarios.dto.RolResponse;
import cl.pchardware.usuarios.model.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Rol toEntity(RolRequest request);

    RolResponse toResponse(Rol rol);

    List<RolResponse> toResponseList(List<Rol> roles);

    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    void updateEntity(RolRequest request, @MappingTarget Rol rol);
}
