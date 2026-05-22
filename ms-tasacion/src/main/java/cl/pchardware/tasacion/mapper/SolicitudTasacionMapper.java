package cl.pchardware.tasacion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.tasacion.dto.SolicitudTasacionRequest;
import cl.pchardware.tasacion.dto.SolicitudTasacionResponse;
import cl.pchardware.tasacion.model.SolicitudTasacion;

@Mapper(componentModel = "spring")
public interface SolicitudTasacionMapper {

    @Mapping(target = "idSolicitud", ignore = true)
    @Mapping(target = "fechaIngreso", ignore = true)
    @Mapping(target = "evaluacionTecnica", ignore = true)
    SolicitudTasacion toEntity(SolicitudTasacionRequest request);

    SolicitudTasacionResponse toResponse(SolicitudTasacion solicitud);

    List<SolicitudTasacionResponse> toResponseList(List<SolicitudTasacion> solicitudes);

    @Mapping(target = "idSolicitud", ignore = true)
    @Mapping(target = "fechaIngreso", ignore = true)
    @Mapping(target = "evaluacionTecnica", ignore = true)
    void updateEntity(SolicitudTasacionRequest request, @MappingTarget SolicitudTasacion solicitud);
}
