package cl.pchardware.envios.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import cl.pchardware.envios.model.Despacho;

@Mapper(componentModel = "spring")
public interface DespachoMapper {

    // Transforma el Request a Entidad. Ignoramos las relaciones tipadas que el Service resolverá por ID.
    @Mapping(target = "idDespacho", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "courier", ignore = true)
    Despacho toEntity(DespachoRequest request);

    // Transforma la Entidad a Response mapeando explícitamente las claves externas provenientes de los objetos relacionados.
    @Mapping(target = "idDireccion", source = "direccionEnvio.idDireccion")
    @Mapping(target = "idCourier", source = "courier.idCourier")
    DespachoResponse toResponse(Despacho despacho);

    List<DespachoResponse> toResponseList(List<Despacho> despachos);

    // Realiza una actualización en destino sobre la entidad Despacho cargada de la BD.
    @Mapping(target = "idDespacho", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "courier", ignore = true)
    void updateEntity(DespachoRequest request, @MappingTarget Despacho despacho);
}
