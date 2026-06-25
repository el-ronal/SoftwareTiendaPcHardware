package cl.pchardware.garantias.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import cl.pchardware.garantias.dto.ResolucionRequest;
import cl.pchardware.garantias.dto.ResolucionResponse;
import cl.pchardware.garantias.model.Resolucion;

@Mapper(componentModel = "spring")
public interface ResolucionMapper {

    @Mapping(source = "inspeccionTecnica.idInspeccion", target = "idInspeccion")
    ResolucionResponse toResponse(Resolucion entity);

    List<ResolucionResponse> toResponseList(List<Resolucion> entities);

    @Mapping(target = "idResolucion", ignore = true)
    @Mapping(target = "inspeccionTecnica", ignore = true)
    @Mapping(target = "fechaCierre", ignore = true)
    Resolucion toEntity(ResolucionRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idResolucion", ignore = true)
    @Mapping(target = "inspeccionTecnica", ignore = true)
    @Mapping(target = "fechaCierre", ignore = true)
    void updateEntity(
            ResolucionRequest request,
            @MappingTarget Resolucion entity
    );
}