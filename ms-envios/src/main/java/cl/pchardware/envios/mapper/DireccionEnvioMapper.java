package cl.pchardware.envios.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.envios.dto.DireccionEnvioRequest;
import cl.pchardware.envios.dto.DireccionEnvioResponse;
import cl.pchardware.envios.model.DireccionEnvio;

@Mapper(componentModel = "spring")
public interface DireccionEnvioMapper {

    // Transforma el Request a Entidad. Ignoramos la clave primaria e hijos de la relación bidireccional.
    @Mapping(target = "idDireccion", ignore = true)
    @Mapping(target = "despachos", ignore = true)
    DireccionEnvio toEntity(DireccionEnvioRequest request);

    // Transforma la Entidad a Response.
    DireccionEnvioResponse toResponse(DireccionEnvio direccionEnvio);

    List<DireccionEnvioResponse> toResponseList(List<DireccionEnvio> direccionesEnvio);

    // Actualiza la entidad existente a partir de los datos modificados del request.
    @Mapping(target = "idDireccion", ignore = true)
    @Mapping(target = "despachos", ignore = true)
    void updateEntity(DireccionEnvioRequest request, @MappingTarget DireccionEnvio direccionEnvio);
}
