package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.PerfilRequest;
import br.com.chfb.api.dto.resp.PerfilResponse;
import br.com.chfb.api.model.Perfil;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {
                EnderecoMapper.class,
                ContatoMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PerfilMapper {

    PerfilResponse toDTO(Perfil perfil);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "contatos", ignore = true)
    Perfil toEntity(PerfilRequest request);


    @AfterMapping
    default void associarRelacionamentos(
            @MappingTarget Perfil perfil,
            PerfilRequest request,
            @Context EnderecoMapper enderecoMapper,
            @Context ContatoMapper contatoMapper
    ) {

        if (request.enderecos() != null) {
            perfil.setEnderecos(
                    request.enderecos()
                            .stream()
                            .map(e -> {
                                var endereco = enderecoMapper.toEntity(e);
                                endereco.setPerfil(perfil);
                                return endereco;
                            })
                            .collect(Collectors.toSet())
            );
        }


        if (request.contatos() != null) {
            perfil.setContatos(
                    request.contatos()
                            .stream()
                            .map(c -> {
                                var contato = contatoMapper.toEntity(c);
                                contato.setPerfil(perfil);
                                return contato;
                            })
                            .collect(Collectors.toSet())
            );
        }
    }
}
