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

    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "metodoPago", ignore = true) // Se gestiona en el Service buscando la entidad por idMetodo
    @Mapping(target = "reembolso", ignore = true)
    Transaccion toEntity(TransaccionRequest request);

    @Mapping(source = "metodoPago.idMetodo", target = "idMetodo")
    TransaccionResponse toResponse(Transaccion transaccion);

    List<TransaccionResponse> toResponseList(List<Transaccion> transacciones);

    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "metodoPago", ignore = true)
    @Mapping(target = "reembolso", ignore = true)
    void updateEntity(TransaccionRequest request, @MappingTarget Transaccion transaccion);
}
