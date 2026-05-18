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

    @Mapping(target = "idCourier", ignore = true)
    @Mapping(target = "despachos", ignore = true)
    Courier toEntity(CourierRequest request);

    CourierResponse toResponse(Courier courier);

    List<CourierResponse> toResponseList(List<Courier> couriers);

    @Mapping(target = "idCourier", ignore = true)
    @Mapping(target = "despachos", ignore = true)
    void updateEntity(CourierRequest request, @MappingTarget Courier courier);
}
