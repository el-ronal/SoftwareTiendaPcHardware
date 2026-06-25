package cl.pchardware.devoluciones.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import cl.pchardware.devoluciones.dto.NotaCreditoRequest;
import cl.pchardware.devoluciones.dto.NotaCreditoResponse;
import cl.pchardware.devoluciones.model.NotaCredito;

@Mapper(componentModel = "spring")
public interface NotaCreditoMapper {

    @Mapping(source = "recepcionLogistica.idRecepcion", target = "idRecepcion")
    NotaCreditoResponse toResponse(NotaCredito entity);

    List<NotaCreditoResponse> toResponseList(List<NotaCredito> entities);

    @Mapping(target = "idNota", ignore = true)
    @Mapping(target = "recepcionLogistica", ignore = true)
    NotaCredito toEntity(NotaCreditoRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idNota", ignore = true)
    @Mapping(target = "recepcionLogistica", ignore = true)
    void updateEntity(
            NotaCreditoRequest request,
            @MappingTarget NotaCredito entity
    );
}