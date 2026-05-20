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

    // Transforma el Request (DTO) a la Entidad para guardarla en la BD.
    // Ignoramos 'idMetodo' porque lo genera la BD y 'transacciones' porque se gestiona por el Service.
    @Mapping(target = "idMetodo", ignore = true)
    @Mapping(target = "transacciones", ignore = true)
    MetodoPago toEntity(MetodoPagoRequest request);

    // Transforma la Entidad a Response para devolver al cliente.
    MetodoPagoResponse toResponse(MetodoPago metodoPago);

    List<MetodoPagoResponse> toResponseList(List<MetodoPago> metodosPago);

    // Realiza una actualización sobre una entidad existente sin perder su identidad ni alterar el ID.
    @Mapping(target = "idMetodo", ignore = true)
    @Mapping(target = "transacciones", ignore = true)
    void updateEntity(MetodoPagoRequest request, @MappingTarget MetodoPago metodoPago);
}
