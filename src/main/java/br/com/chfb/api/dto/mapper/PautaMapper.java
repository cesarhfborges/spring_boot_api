package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.PautaRequest;
import br.com.chfb.api.dto.resp.PautaResponse;
import br.com.chfb.api.model.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PautaMapper {

    PautaResponse toDTO(Pauta pauta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reuniao", ignore = true)
    @Mapping(target = "opcoes", ignore = true)
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "tipoVoto", source = "tipoVoto")
    @Mapping(target = "tipoVotacao", source = "tipoVotacao")
    @Mapping(target = "limiteSelecoes", source = "limiteSelecoes")
    Pauta toEntity(PautaRequest request);
}
