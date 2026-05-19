package cl.pchardware.stock.mapper;

import cl.pchardware.dto.BodegaRequest;
import cl.pchardware.dto.BodegaResponse;
import cl.pchardware.model.Bodega;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BodegaMapper {

    @Mapping(target = "idBodega", ignore = true)
    @Mapping(target = "inventarios", ignore = true)
    Bodega toEntity(BodegaRequest request);

    BodegaResponse toResponse(Bodega bodega);
    List<BodegaResponse> toResponseList(List<Bodega> bodegas);

    @Mapping(target = "idBodega", ignore = true)
    @Mapping(target = "inventarios", ignore = true)
    void updateEntity(BodegaRequest request, @MappingTarget Bodega bodega);
}
