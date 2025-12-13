package br.com.chfb.api.dto;

public record LoginRequestDTO(
        String username,
        String password
) {
}
