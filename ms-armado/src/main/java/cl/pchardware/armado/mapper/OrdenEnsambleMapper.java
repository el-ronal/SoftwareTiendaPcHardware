package cl.pchardware.armado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.armado.dto.OrdenEnsambleRequest;
import cl.pchardware.armado.dto.OrdenEnsambleResponse;
import cl.pchardware.armado.model.OrdenEnsamble;

@Mapper(componentModel = "spring")
public interface OrdenEnsambleMapper {

    @Mapping(target = "idOrden", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "resultadoTesting", ignore = true)
    OrdenEnsamble toEntity(OrdenEnsambleRequest request);

    @Mapping(source = "tecnico.idTecnico", target = "idTecnico")
    OrdenEnsambleResponse toResponse(OrdenEnsamble orden);

    List<OrdenEnsambleResponse> toResponseList(List<OrdenEnsamble> ordenes);

    @Mapping(target = "idOrden", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "resultadoTesting", ignore = true)
    void updateEntity(OrdenEnsambleRequest request, @MappingTarget OrdenEnsamble orden);
}
