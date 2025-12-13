package br.com.chfb.api.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultHeadersFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;

        // Segurança básica
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("X-Frame-Options", "DENY");
        res.setHeader("X-XSS-Protection", "0");
        res.setHeader("default-src", "none");
        res.setHeader("Referrer-Policy", "no-referrer");

        // API REST
        res.setHeader("Cache-Control", "no-store");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Expires", "0");

        // Identificação da API
        res.setHeader("Content-Language", "pt-BR");
        res.setHeader("Vary", "Accept");

        chain.doFilter(request, response);
    }
}
