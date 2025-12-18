package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.FuncionarioRequest;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.model.Funcionario;
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
public interface FuncionarioMapper {

    FuncionarioResponse toDTO(Funcionario funcionario);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "contatos", ignore = true)
    Funcionario toEntity(FuncionarioRequest request);


    @AfterMapping
    default void associarRelacionamentos(
            @MappingTarget Funcionario funcionario,
            FuncionarioRequest request,
            @Context EnderecoMapper enderecoMapper,
            @Context ContatoMapper contatoMapper
    ) {

        if (request.enderecos() != null) {
            funcionario.setEnderecos(
                    request.enderecos()
                            .stream()
                            .map(e -> {
                                var endereco = enderecoMapper.toEntity(e);
                                endereco.setFuncionario(funcionario);
                                return endereco;
                            })
                            .collect(Collectors.toSet())
            );
        }


        if (request.contatos() != null) {
            funcionario.setContatos(
                    request.contatos()
                            .stream()
                            .map(c -> {
                                var contato = contatoMapper.toEntity(c);
                                contato.setFuncionario(funcionario);
                                return contato;
                            })
                            .collect(Collectors.toSet())
            );
        }
    }
}
