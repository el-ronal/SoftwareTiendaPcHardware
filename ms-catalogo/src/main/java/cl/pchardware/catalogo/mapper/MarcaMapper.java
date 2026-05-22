package cl.pchardware.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.catalogo.dto.MarcaRequest;
import cl.pchardware.catalogo.dto.MarcaResponse;
import cl.pchardware.catalogo.model.Marca;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

    @Mapping(target = "idMarca", ignore = true)
    @Mapping(target = "productos", ignore = true)
    Marca toEntity(MarcaRequest request);

    MarcaResponse toResponse(Marca marca);

    List<MarcaResponse> toResponseList(List<Marca> marcas);

    @Mapping(target = "idMarca", ignore = true)
    @Mapping(target = "productos", ignore = true)
    void updateEntity(MarcaRequest request, @MappingTarget Marca marca);
}
