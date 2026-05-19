package cl.pchardware.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.notificaciones.dto.PlantillaCorreoRequest;
import cl.pchardware.notificaciones.dto.PlantillaCorreoResponse;
import cl.pchardware.notificaciones.model.PlantillaCorreo;

@Mapper(componentModel = "spring")
public interface PlantillaCorreoMapper {

    @Mapping(target = "idPlantilla", ignore = true)
    @Mapping(target = "mensajes", ignore = true)
    PlantillaCorreo toEntity(PlantillaCorreoRequest request);

    PlantillaCorreoResponse toResponse(PlantillaCorreo plantilla);

    List<PlantillaCorreoResponse> toResponseList(List<PlantillaCorreo> plantillas);

    @Mapping(target = "idPlantilla", ignore = true)
    @Mapping(target = "mensajes", ignore = true)
    void updateEntity(PlantillaCorreoRequest request, @MappingTarget PlantillaCorreo plantilla);
}
