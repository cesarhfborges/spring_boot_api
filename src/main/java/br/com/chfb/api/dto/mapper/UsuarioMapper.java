package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.UsuarioRequest;
import br.com.chfb.api.dto.resp.UsuarioResponse;
import br.com.chfb.api.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UsuarioMapper {

    UsuarioResponse toDTO(Usuario value);

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequest value);
}
