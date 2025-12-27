package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.BloqueioVotoPautaRequest;
import br.com.chfb.api.dto.resp.BloqueioVotoPautaResponse;
import br.com.chfb.api.model.BloqueioVotoPauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BloqueioVotoPautaMapper {

    @Mapping(source = "funcionario.id", target = "funcionarioId")
    @Mapping(source = "pauta.id", target = "pautaId")
    BloqueioVotoPautaResponse toDTO(BloqueioVotoPauta bloqueio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "funcionario", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "incluidoPor", ignore = true)
    @Mapping(target = "dataInclusao", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    BloqueioVotoPauta toEntity(BloqueioVotoPautaRequest request);
}
