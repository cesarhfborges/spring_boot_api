package br.com.chfb.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarLinkRedefinicao(String paraEmail, String token) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom("nao-responda@chfb.com.br");
        mensagem.setTo(paraEmail);
        mensagem.setSubject("Recuperação de Senha");

        String url = "https://seuapp.chfb.com.br/redefinir?token=" + token;
        mensagem.setText("Para redefinir sua senha, clique no link: " + url);

        mailSender.send(mensagem);
    }
}
