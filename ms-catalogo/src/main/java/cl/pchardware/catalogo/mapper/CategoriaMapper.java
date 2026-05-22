package cl.pchardware.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.catalogo.dto.CategoriaRequest;
import cl.pchardware.catalogo.dto.CategoriaResponse;
import cl.pchardware.catalogo.model.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "productos", ignore = true)
    Categoria toEntity(CategoriaRequest request);

    CategoriaResponse toResponse(Categoria categoria);

    List<CategoriaResponse> toResponseList(List<Categoria> categorias);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "productos", ignore = true)
    void updateEntity(CategoriaRequest request, @MappingTarget Categoria categoria);
}
