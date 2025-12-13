package br.com.chfb.api.configuration;

import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeed implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario admin = Usuario.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .build();

        Usuario user = Usuario.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .build();

        usuarioRepository.save(admin);
        usuarioRepository.save(user);
    }
}
