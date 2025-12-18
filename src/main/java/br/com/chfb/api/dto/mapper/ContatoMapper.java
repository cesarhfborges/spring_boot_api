package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.model.Contato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ContatoMapper {

    ContatoResponse toDTO(Contato contato);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "funcionario", ignore = true)
    Contato toEntity(ContatoRequest request);
}
