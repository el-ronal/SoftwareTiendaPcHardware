package cl.pchardware.pagos.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pagos.dto.TransaccionRequest;
import cl.pchardware.pagos.dto.TransaccionResponse;
import cl.pchardware.pagos.model.Transaccion;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {

    // Transforma el Request a Entidad. Ignoramos la relación 'metodoPago' ya que el service
    // la resolverá buscando el MetodoPago correspondiente en la BD, e ignoramos 'reembolso'.
    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "metodoPago", ignore = true)
    @Mapping(target = "reembolso", ignore = true)
    Transaccion toEntity(TransaccionRequest request);

    // Transforma la Entidad a Response, extrayendo el idMetodo de la entidad relacionada.
    @Mapping(target = "idMetodo", source = "metodoPago.idMetodo")
    TransaccionResponse toResponse(Transaccion transaccion);

    List<TransaccionResponse> toResponseList(List<Transaccion> transacciones);

    // Actualiza la entidad existente a partir de los datos modificados del request.
    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "metodoPago", ignore = true)
    @Mapping(target = "reembolso", ignore = true)
    void updateEntity(TransaccionRequest request, @MappingTarget Transaccion transaccion);
}
