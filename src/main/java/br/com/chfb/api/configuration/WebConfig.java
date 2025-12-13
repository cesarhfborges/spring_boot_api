package br.com.chfb.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        configurer
                // Se não vier Accept, usa JSON
                .defaultContentType(MediaType.APPLICATION_JSON)

                // NÃO usar extensão .json / .xml
                .favorPathExtension(false)

                // NÃO usar parâmetro ?format=
                .favorParameter(false)

                // Usa Accept header se existir
                .ignoreAcceptHeader(false)

                // Mapeamento explícito
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
