package cl.pchardware.envios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import cl.pchardware.envios.model.Courier;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    // Transforma el Request (DTO) a la Entidad para guardarla en la BD.
    // Ignoramos 'idCourier' porque lo genera la BD y 'despachos' porque se gestiona por el Service.
    @Mapping(target = "idCourier", ignore = true)
    @Mapping(target = "despachos", ignore = true)
    Courier toEntity(CourierRequest request);

    // Transforma la Entidad a Response para devolver al cliente.
    CourierResponse toResponse(Courier courier);

    List<CourierResponse> toResponseList(List<Courier> couriers);

    // Realiza una actualización sobre una entidad existente sin perder su identidad ni alterar el ID.
    @Mapping(target = "idCourier", ignore = true)
    @Mapping(target = "despachos", ignore = true)
    void updateEntity(CourierRequest request, @MappingTarget Courier courier);
}
