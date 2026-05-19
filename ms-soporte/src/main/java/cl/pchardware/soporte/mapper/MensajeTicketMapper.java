package cl.pchardware.soporte.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.pchardware.soporte.dto.MensajeTicketRequest;
import cl.pchardware.soporte.dto.MensajeTicketResponse;
import cl.pchardware.soporte.model.MensajeTicket;

@Mapper(componentModel = "spring")
public interface MensajeTicketMapper {

    @Mapping(target = "idMensaje", ignore = true)
    @Mapping(target = "ticketSoporte", ignore = true)
    @Mapping(target = "fechaEnvio", ignore = true)
    MensajeTicket toEntity(MensajeTicketRequest request);

    @Mapping(source = "ticketSoporte.idTicket", target = "idTicket")
    MensajeTicketResponse toResponse(MensajeTicket mensaje);

    List<MensajeTicketResponse> toResponseList(List<MensajeTicket> mensajes);
}
