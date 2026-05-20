package cl.pchardware.armado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import cl.pchardware.armado.dto.ResultadoTestingRequest;
import cl.pchardware.armado.dto.ResultadoTestingResponse;
import cl.pchardware.armado.model.ResultadoTesting;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResultadoTestingMapper {

    // Mapeamos desde la entidad hacia el DTO (Response)
    @Mapping(source = "orden.idOrden", target = "idOrden")
    ResultadoTestingResponse toResponse(ResultadoTesting entity);

    List<ResultadoTestingResponse> toResponseList(List<ResultadoTesting> entities);

    // Mapeamos desde el DTO (Request) hacia la entidad
    @Mapping(source = "idOrden", target = "orden.idOrden")
    ResultadoTesting toEntity(ResultadoTestingRequest request);

    // Actualizamos la entidad existente 
    @Mapping(source = "idOrden", target = "orden.idOrden")
    void updateEntity(ResultadoTestingRequest request, @MappingTarget ResultadoTesting entity);
}