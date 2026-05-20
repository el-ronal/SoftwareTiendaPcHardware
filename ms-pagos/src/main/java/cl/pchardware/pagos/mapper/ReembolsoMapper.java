package cl.pchardware.pagos.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pagos.dto.ReembolsoRequest;
import cl.pchardware.pagos.dto.ReembolsoResponse;
import cl.pchardware.pagos.model.Reembolso;

@Mapper(componentModel = "spring")
public interface ReembolsoMapper {

    // Transforma el Request a Entidad. Ignoramos campos autogenerados, auditoría (fechaProceso)
    // y relaciones directas que resolverá el service.
    @Mapping(target = "idReembolso", ignore = true)
    @Mapping(target = "transaccion", ignore = true)
    @Mapping(target = "fechaProceso", ignore = true)
    Reembolso toEntity(ReembolsoRequest request);

    // Transforma la Entidad a Response mapeando de forma explícita el id de la transacción relacionada.
    @Mapping(target = "idTransaccion", source = "transaccion.idTransaccion")
    ReembolsoResponse toResponse(Reembolso reembolso);

    List<ReembolsoResponse> toResponseList(List<Reembolso> reembolsos);

    // Actualización in-place para la entidad Reembolso.
    @Mapping(target = "idReembolso", ignore = true)
    @Mapping(target = "transaccion", ignore = true)
    @Mapping(target = "fechaProceso", ignore = true)
    void updateEntity(ReembolsoRequest request, @MappingTarget Reembolso reembolso);
}
