package cl.pchardware.tasacion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import cl.pchardware.tasacion.dto.EvaluacionTecnicaRequest;
import cl.pchardware.tasacion.dto.EvaluacionTecnicaResponse;
import cl.pchardware.tasacion.model.EvaluacionTecnica;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvaluacionTecnicaMapper {

    @Mapping(source = "solicitudTasacion.idSolicitud", target = "idSolicitud")
    @Mapping(source = "ofertaCompra.idOferta", target = "idOfertaCompra")
    EvaluacionTecnicaResponse toResponse(EvaluacionTecnica entity);

    List<EvaluacionTecnicaResponse> toResponseList(List<EvaluacionTecnica> entities);

    @Mapping(source = "idSolicitud", target = "solicitudTasacion.idSolicitud")
    @Mapping(target = "ofertaCompra", ignore = true)
    EvaluacionTecnica toEntity(EvaluacionTecnicaRequest request);

    @Mapping(source = "idSolicitud", target = "solicitudTasacion.idSolicitud")
    @Mapping(target = "ofertaCompra", ignore = true)
    void updateEntity(EvaluacionTecnicaRequest request, @MappingTarget EvaluacionTecnica entity);
}