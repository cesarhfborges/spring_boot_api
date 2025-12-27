package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.OpcaoVotoRequest;
import br.com.chfb.api.dto.resp.OpcaoVotoResponse;
import br.com.chfb.api.model.OpcaoVoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OpcaoVotoMapper {

    OpcaoVotoResponse toDTO(OpcaoVoto opcao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "titulo", source = "titulo")
    OpcaoVoto toEntity(OpcaoVotoRequest request);
}
