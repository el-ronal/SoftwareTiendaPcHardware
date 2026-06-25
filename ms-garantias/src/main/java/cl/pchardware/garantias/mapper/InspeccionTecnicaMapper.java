package cl.pchardware.garantias.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import cl.pchardware.garantias.dto.InspeccionTecnicaRequest;
import cl.pchardware.garantias.dto.InspeccionTecnicaResponse;
import cl.pchardware.garantias.model.InspeccionTecnica;

@Mapper(componentModel = "spring")
public interface InspeccionTecnicaMapper {

    @Mapping(source = "ticketGarantia.idTicket", target = "idTicket")
    @Mapping(source = "resolucion.idResolucion", target = "idResolucion")
    InspeccionTecnicaResponse toResponse(InspeccionTecnica entity);

    List<InspeccionTecnicaResponse> toResponseList(List<InspeccionTecnica> entities);

    @Mapping(target = "idInspeccion", ignore = true)
    @Mapping(target = "ticketGarantia", ignore = true)
    @Mapping(target = "resolucion", ignore = true)
    InspeccionTecnica toEntity(InspeccionTecnicaRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idInspeccion", ignore = true)
    @Mapping(target = "ticketGarantia", ignore = true)
    @Mapping(target = "resolucion", ignore = true)
    void updateEntity(
            InspeccionTecnicaRequest request,
            @MappingTarget InspeccionTecnica entity
    );
}