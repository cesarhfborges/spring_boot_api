package br.com.chfb.api.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public record VotoRequest(

//        @NotNull(message = "ID da pauta é obrigatório")
//        Long pautaId,

//        @NotEmpty(message = "Ao menos uma opção deve ser selecionada")
//        List<Long> opcoesSelecionadasIds,

//        @NotNull(message = "Ao menos uma opção deve ser selecionada.")
//        List<Long> opcoesSelecionadas,

        @NotNull(message = "Voto é obrigatório")
        JsonNode voto,


        String codigoVoto
) {

        @JsonIgnore
        public List<Long> votosComoLista() {

                List<Long> votos = new ArrayList<>();

                if (voto.isArray()) {
                        voto.forEach(node -> votos.add(node.asLong()));
                } else {
                        votos.add(voto.asLong());
                }

                return votos;
        }
}
