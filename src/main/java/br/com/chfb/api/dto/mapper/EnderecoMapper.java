package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.model.Endereco;
import br.com.chfb.api.model.Perfil;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EnderecoMapper {
    EnderecoResponse toDTO(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    Endereco toEntity(EnderecoRequest request);

    @AfterMapping
    default void associarPerfil(
            @MappingTarget Endereco endereco,
            @Context Perfil perfil
    ) {
        endereco.setPerfil(perfil);
    }
}
