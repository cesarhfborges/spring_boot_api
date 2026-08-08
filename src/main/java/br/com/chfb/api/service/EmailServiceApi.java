package br.com.chfb.api.service;

import br.com.chfb.api.dto.mailpit.MailpitPayload;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceApi {

    private final RestClient restClient;
    private final String mailpitUrl = "https://mailpit.chfb.com.br/api/v1/send";

    // Gera o valor do cabeçalho "Basic app:senhaSmtp456" em Base64
    private final String authHeader = "Basic " + Base64.getEncoder()
            .encodeToString("admin:senhaPainel123".getBytes());

    public EmailServiceApi() {
        this.restClient = RestClient.create();
    }

    private static @NonNull MailpitPayload getPayload(String urlRedefinicao, MailpitPayload.From de, MailpitPayload.To para) {
        String corpoHtml = String.format(
                "<div style='font-family: Arial; font-size: 16px;'>" +
                        "<h2>Recuperação de Senha</h2>" +
                        "<p>Para redefinir sua senha, clique no link abaixo:</p>" +
                        "<p><a href='%s' style='padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px;'>Redefinir Senha</a></p>" +
                        "</div>",
                urlRedefinicao
        );

        return new MailpitPayload(
                de,
                List.of(para),
                "Recuperação de Senha",
                "Para redefinir sua senha, acesse: " + urlRedefinicao,
                corpoHtml,
                List.of(),
                Map.of("X-IP", "1.2.3.4")
        );
    }

    public void enviarLinkRedefinicao(String paraEmail, String token) {
        String urlRedefinicao = "https://seuapp.chfb.com.br/redefinir?token=" + token;

        var de = new MailpitPayload.From("nao-responda@chfb.com.br", "CHFB Sistemas");
        var para = new MailpitPayload.To(paraEmail, "");

        var payload = getPayload(urlRedefinicao, de, para);

        this.restClient.post()
                .uri(mailpitUrl)
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }
}
