package cl.pchardware.devoluciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.devoluciones.dto.SolicitudDevolucionRequest;
import cl.pchardware.devoluciones.dto.SolicitudDevolucionResponse;
import cl.pchardware.devoluciones.model.SolicitudDevolucion;

@Mapper(componentModel = "spring")
public interface SolicitudDevolucionMapper {

    @Mapping(target = "idDevolucion", ignore = true)
    @Mapping(target = "fechaSolicitud", ignore = true)
    @Mapping(target = "recepcionLogistica", ignore = true)
    SolicitudDevolucion toEntity(SolicitudDevolucionRequest request);

    SolicitudDevolucionResponse toResponse(SolicitudDevolucion solicitud);

    List<SolicitudDevolucionResponse> toResponseList(List<SolicitudDevolucion> solicitudes);

    @Mapping(target = "idDevolucion", ignore = true)
    @Mapping(target = "fechaSolicitud", ignore = true)
    @Mapping(target = "recepcionLogistica", ignore = true)
    void updateEntity(SolicitudDevolucionRequest request, @MappingTarget SolicitudDevolucion solicitud);
}
