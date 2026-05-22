package cl.pchardware.soporte.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.soporte.dto.TicketSoporteRequest;
import cl.pchardware.soporte.dto.TicketSoporteResponse;
import cl.pchardware.soporte.model.TicketSoporte;

@Mapper(componentModel = "spring")
public interface TicketSoporteMapper {

    @Mapping(target = "idTicket", ignore = true)
    @Mapping(target = "fechaApertura", ignore = true)
    @Mapping(target = "mensajes", ignore = true)
    @Mapping(target = "encuesta", ignore = true)
    TicketSoporte toEntity(TicketSoporteRequest request);

    TicketSoporteResponse toResponse(TicketSoporte ticket);

    List<TicketSoporteResponse> toResponseList(List<TicketSoporte> tickets);

    @Mapping(target = "idTicket", ignore = true)
    @Mapping(target = "fechaApertura", ignore = true)
    @Mapping(target = "mensajes", ignore = true)
    @Mapping(target = "encuesta", ignore = true)
    void updateEntity(TicketSoporteRequest request, @MappingTarget TicketSoporte ticket);
}
