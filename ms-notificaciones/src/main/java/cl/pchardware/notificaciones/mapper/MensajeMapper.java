package cl.pchardware.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.pchardware.notificaciones.dto.MensajeRequest;
import cl.pchardware.notificaciones.dto.MensajeResponse;
import cl.pchardware.notificaciones.model.Mensaje;

@Mapper(componentModel = "spring")
public interface MensajeMapper {

    @Mapping(target = "idMensaje", ignore = true)
    @Mapping(target = "plantilla", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    @Mapping(target = "registrosEnvio", ignore = true)
    Mensaje toEntity(MensajeRequest request);

    @Mapping(source = "plantilla.idPlantilla", target = "idPlantilla")
    @Mapping(source = "plantilla.codigoEvento", target = "codigoEvento")
    MensajeResponse toResponse(Mensaje mensaje);

    List<MensajeResponse> toResponseList(List<Mensaje> mensajes);
}
