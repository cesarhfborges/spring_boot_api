package br.com.chfb.api.dto.mapper;

import br.com.chfb.api.dto.req.ReuniaoRequest;
import br.com.chfb.api.dto.resp.ReuniaoResponse;
import br.com.chfb.api.model.Reuniao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReuniaoMapper {

    ReuniaoResponse toDTO(Reuniao reuniao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pautas", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "status", ignore = true)
    Reuniao toEntity(ReuniaoRequest request);
}
