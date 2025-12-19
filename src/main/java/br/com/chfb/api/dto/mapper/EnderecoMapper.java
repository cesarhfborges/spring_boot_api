package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EnderecoMapper {

    EnderecoResponse toDTO(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "funcionario", ignore = true)
    @Mapping(target = "uf", expression = "java(UF.fromString(request.uf()))")
    Endereco toEntity(EnderecoRequest request);
}
