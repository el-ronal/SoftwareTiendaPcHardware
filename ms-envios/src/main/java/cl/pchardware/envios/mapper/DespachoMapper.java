package cl.pchardware.envios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import cl.pchardware.envios.model.Despacho;

@Mapper(componentModel = "spring", uses = {DireccionEnvioMapper.class, CourierMapper.class})

public interface DespachoMapper {

    @Mapping(target = "idDespacho", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "courier", ignore = true)
    Despacho toEntity(DespachoRequest request);

    DespachoResponse toResponse(Despacho despacho);

    List<DespachoResponse> toResponseList(List<Despacho> despachos);

    @Mapping(target = "idDespacho", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "courier", ignore = true)
    void updateEntity(DespachoRequest request, @MappingTarget Despacho despacho);
}
