package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.FuncionarioRequest;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.model.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FuncionarioMapper {

    FuncionarioResponse toDTO(Funcionario funcionario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "contatos", ignore = true)
    Funcionario toEntity(FuncionarioRequest request);
}
