package cl.pchardware.pagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pagos.dto.MetodoPagoRequest;
import cl.pchardware.pagos.dto.MetodoPagoResponse;
import cl.pchardware.pagos.model.MetodoPago;

@Mapper(componentModel = "spring")
public interface MetodoPagoMapper {

    @Mapping(target = "idMetodo", ignore = true)
    @Mapping(target = "transacciones", ignore = true)
    MetodoPago toEntity(MetodoPagoRequest request);

    MetodoPagoResponse toResponse(MetodoPago metodoPago);

    List<MetodoPagoResponse> toResponseList(List<MetodoPago> metodosPago);

    @Mapping(target = "idMetodo", ignore = true)
    @Mapping(target = "transacciones", ignore = true)
    void updateEntity(MetodoPagoRequest request, @MappingTarget MetodoPago metodoPago);
}
