package cl.pchardware.tasacion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import cl.pchardware.tasacion.dto.OfertaCompraRequest;
import cl.pchardware.tasacion.dto.OfertaCompraResponse;
import cl.pchardware.tasacion.model.OfertaCompra;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OfertaCompraMapper {

    @Mapping(source = "evaluacionTecnica.idEvaluacion", target = "idEvaluacion")
    OfertaCompraResponse toResponse(OfertaCompra entity);

    List<OfertaCompraResponse> toResponseList(List<OfertaCompra> entities);

    @Mapping(source = "idEvaluacion", target = "evaluacionTecnica.idEvaluacion")
    OfertaCompra toEntity(OfertaCompraRequest request);

    @Mapping(source = "idEvaluacion", target = "evaluacionTecnica.idEvaluacion")
    void updateEntity(OfertaCompraRequest request, @MappingTarget OfertaCompra entity);
}