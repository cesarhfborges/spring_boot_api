package br.com.chfb.api.seeds;

import br.com.chfb.api.model.*;
import br.com.chfb.api.repository.RoleRepository;
import br.com.chfb.api.repository.UsuarioRepository;
import br.com.chfb.api.seeds.contract.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UsuarioSeed implements Seed {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run() {

        if (usuarioRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        Role votanteRole = roleRepository.findByName("ROLE_VOTANTE").orElseThrow();

        // ======================
        // ADMIN
        // ======================
        Usuario admin = criarUsuarioComProfile(
                "admin",
                "admin123",
                adminRole,
                "Administrador",
                "Sistema",
                "admin@votacao.com"
        );

        // ======================
        // USER
        // ======================
        Usuario user = criarUsuarioComProfile(
                "user",
                "user123",
                votanteRole,
                "Usuário",
                "Padrão",
                "user@votacao.com"
        );

        usuarioRepository.saveAll(Set.of(admin, user));
    }

    private Usuario criarUsuarioComProfile(
            String username,
            String senha,
            Role role,
            String nome,
            String sobrenome,
            String email
    ) {

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode(senha))
                .roles(Set.of(role))
                .build();

        Perfil profile = Perfil.builder()
                .nome(nome)
                .sobrenome(sobrenome)
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .cpf(null)
                .rg(null)
                .usuario(usuario)
                .build();

        Endereco endereco = Endereco.builder()
                .logradouro("Rua Exemplo")
                .numero("123")
                .bairro("Centro")
                .cidade("São Paulo")
//                .uf("SP")
                .cep("01001000")
                .perfil(profile)
                .build();

        Contato contato = Contato.builder()
                .tipo(TipoContato.EMAIL)
                .valor(email)
                .perfil(profile)
                .build();

        profile.setEnderecos(Set.of(endereco));
        profile.setContatos(Set.of(contato));
        usuario.setPerfil(profile);

        return usuario;
    }
}
