package cl.pchardware.soporte.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import cl.pchardware.soporte.dto.EncuestaSatisfaccionRequest;
import cl.pchardware.soporte.dto.EncuestaSatisfaccionResponse;
import cl.pchardware.soporte.model.EncuestaSatisfaccion;

@Mapper(componentModel = "spring")
public interface EncuestaSatisfaccionMapper {

    @Mapping(source = "ticketSoporte.idTicket", target = "idTicket")
    EncuestaSatisfaccionResponse toResponse(EncuestaSatisfaccion entity);

    List<EncuestaSatisfaccionResponse> toResponseList(List<EncuestaSatisfaccion> entities);

    @Mapping(target = "idEncuesta", ignore = true)
    @Mapping(target = "ticketSoporte", ignore = true)
    @Mapping(target = "fechaRespuesta", ignore = true)
    EncuestaSatisfaccion toEntity(EncuestaSatisfaccionRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idEncuesta", ignore = true)
    @Mapping(target = "ticketSoporte", ignore = true)
    @Mapping(target = "fechaRespuesta", ignore = true)
    void updateEntity(
            EncuestaSatisfaccionRequest request,
            @MappingTarget EncuestaSatisfaccion entity
    );
}