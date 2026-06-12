package br.com.chfb.api.dto.resp;

public record EventoReuniao (
        String type,
        Long reuniaoId,
        Long pautaId,
        String message
){}
