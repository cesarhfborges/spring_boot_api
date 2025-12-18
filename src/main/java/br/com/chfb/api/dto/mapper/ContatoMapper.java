package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.model.Contato;
import br.com.chfb.api.model.Funcionario;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ContatoMapper {

    ContatoResponse toDTO(Contato contato);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "funcionario", ignore = true)
    Contato toEntity(ContatoRequest request);

    @AfterMapping
    default void associarFuncionario(
            @MappingTarget Contato contato,
            @Context Funcionario funcionario
    ) {
        contato.setFuncionario(funcionario);
    }
}
