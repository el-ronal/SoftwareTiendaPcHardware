package cl.pchardware.garantias.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.garantias.dto.TicketGarantiaRequest;
import cl.pchardware.garantias.dto.TicketGarantiaResponse;
import cl.pchardware.garantias.model.TicketGarantia;

@Mapper(componentModel = "spring")
public interface TicketGarantiaMapper {

    @Mapping(target = "idTicket", ignore = true)
    @Mapping(target = "inspeccionTecnica", ignore = true)
    TicketGarantia toEntity(TicketGarantiaRequest request);

    TicketGarantiaResponse toResponse(TicketGarantia ticket);

    List<TicketGarantiaResponse> toResponseList(List<TicketGarantia> tickets);

    @Mapping(target = "idTicket", ignore = true)
    @Mapping(target = "inspeccionTecnica", ignore = true)
    void updateEntity(TicketGarantiaRequest request, @MappingTarget TicketGarantia ticket);
}
