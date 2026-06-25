package cl.pchardware.devoluciones.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import cl.pchardware.devoluciones.dto.RecepcionLogisticaRequest;
import cl.pchardware.devoluciones.dto.RecepcionLogisticaResponse;
import cl.pchardware.devoluciones.model.RecepcionLogistica;

@Mapper(componentModel = "spring")
public interface RecepcionLogisticaMapper {

    @Mapping(source = "solicitudDevolucion.idDevolucion", target = "idDevolucion")
    @Mapping(source = "notaCredito.idNota", target = "idNotaCredito")
    RecepcionLogisticaResponse toResponse(RecepcionLogistica entity);

    List<RecepcionLogisticaResponse> toResponseList(List<RecepcionLogistica> entities);

    @Mapping(target = "idRecepcion", ignore = true)
    @Mapping(target = "solicitudDevolucion", ignore = true)
    @Mapping(target = "notaCredito", ignore = true)
    RecepcionLogistica toEntity(RecepcionLogisticaRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idRecepcion", ignore = true)
    @Mapping(target = "solicitudDevolucion", ignore = true)
    @Mapping(target = "notaCredito", ignore = true)
    void updateEntity(
            RecepcionLogisticaRequest request,
            @MappingTarget RecepcionLogistica entity
    );
}