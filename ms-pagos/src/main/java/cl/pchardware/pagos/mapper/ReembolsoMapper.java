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

    @Mapping(target = "idReembolso", ignore = true)
    @Mapping(target = "transaccion", ignore = true) // Se gestiona en el Service buscando la entidad por idTransaccion
    @Mapping(target = "fechaProceso", ignore = true) // Generado automáticamente por @CreatedDate
    Reembolso toEntity(ReembolsoRequest request);

    @Mapping(source = "transaccion.idTransaccion", target = "idTransaccion")
    ReembolsoResponse toResponse(Reembolso reembolso);

    List<ReembolsoResponse> toResponseList(List<Reembolso> reembolsos);

    @Mapping(target = "idReembolso", ignore = true)
    @Mapping(target = "transaccion", ignore = true)
    @Mapping(target = "fechaProceso", ignore = true)
    void updateEntity(ReembolsoRequest request, @MappingTarget Reembolso reembolso);
}
