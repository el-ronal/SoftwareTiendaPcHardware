package cl.pchardware.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import cl.pchardware.notificaciones.dto.RegistroEnvioRequest;
import cl.pchardware.notificaciones.dto.RegistroEnvioResponse;
import cl.pchardware.notificaciones.model.RegistroEnvio;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistroEnvioMapper {

    // Mapeamos el idMensaje desde la entidad Mensaje hacia el Response
    @Mapping(source = "mensaje.idMensaje", target = "idMensaje")
    RegistroEnvioResponse toResponse(RegistroEnvio entity);

    List<RegistroEnvioResponse> toResponseList(List<RegistroEnvio> entities);

    // Mapeamos el idMensaje desde el Request hacia la entidad anidada Mensaje
    @Mapping(source = "idMensaje", target = "mensaje.idMensaje")
    RegistroEnvio toEntity(RegistroEnvioRequest request);

    // Actualizamos la entidad existente ignorando nulos (opcional, pero recomendado)
    @Mapping(source = "idMensaje", target = "mensaje.idMensaje")
    void updateEntity(RegistroEnvioRequest request, @MappingTarget RegistroEnvio entity);
}