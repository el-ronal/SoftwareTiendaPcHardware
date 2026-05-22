package cl.pchardware.armado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.armado.dto.TecnicoArmadoRequest;
import cl.pchardware.armado.dto.TecnicoArmadoResponse;
import cl.pchardware.armado.model.TecnicoArmado;

@Mapper(componentModel = "spring")
public interface TecnicoArmadoMapper {

    @Mapping(target = "idTecnico", ignore = true)
    @Mapping(target = "ordenes", ignore = true)
    TecnicoArmado toEntity(TecnicoArmadoRequest request);

    TecnicoArmadoResponse toResponse(TecnicoArmado tecnico);

    List<TecnicoArmadoResponse> toResponseList(List<TecnicoArmado> tecnicos);

    @Mapping(target = "idTecnico", ignore = true)
    @Mapping(target = "ordenes", ignore = true)
    void updateEntity(TecnicoArmadoRequest request, @MappingTarget TecnicoArmado tecnico);
}
