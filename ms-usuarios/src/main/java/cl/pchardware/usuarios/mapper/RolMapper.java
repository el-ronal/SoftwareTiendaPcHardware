package cl.pchardware.usuarios.mapper;

import cl.pchardware.usuarios.dto.RolRequest;
import cl.pchardware.usuarios.dto.RolResponse;
import cl.pchardware.usuarios.model.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    // Ignoramos el ID (lo genera Postgres) y la lista de usuarios (no la creamos desde aquí)
    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Rol toEntity(RolRequest request);
    
    RolResponse toResponse(Rol rol);
    
    List<RolResponse> toResponseList(List<Rol> roles);

    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    void updateEntity(RolRequest request, @MappingTarget Rol rol);
}