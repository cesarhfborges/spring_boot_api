package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.VotoRequest;
import br.com.chfb.api.dto.resp.VotoResponse;
import br.com.chfb.api.model.Voto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VotoMapper {

    @Mapping(target = "opcoesSelecionadas", ignore = true)
    VotoResponse toDTO(Voto voto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "funcionario", ignore = true)
    @Mapping(target = "dataHoraVoto", ignore = true)
    @Mapping(target = "itensVotados", ignore = true)
    Voto toEntity(VotoRequest request);
}
