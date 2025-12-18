package br.com.chfb.api.seeds;

import br.com.chfb.api.model.*;
import br.com.chfb.api.repository.RoleRepository;
import br.com.chfb.api.repository.UsuarioRepository;
import br.com.chfb.api.seeds.contract.Seed;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class UsuarioSeed implements Seed {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker(new Locale("pt-BR"));

    @Override
    public void run() {

        if (usuarioRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        Role gestorRole = roleRepository.findByName("ROLE_GESTOR").orElseThrow();
        Role auditorRole = roleRepository.findByName("ROLE_AUDITOR").orElseThrow();
        Role votanteRole = roleRepository.findByName("ROLE_VOTANTE").orElseThrow();

        // ======================================================
        // 1️⃣ ADMIN (ID = 1)
        // ======================================================
        usuarioRepository.save(
                criarUsuarioFixo(
                        "admin",
                        "admin123",
                        "Administrador",
                        "Sistema",
                        "admin@sistema.com",
                        adminRole
                )
        );

        // ======================================================
        // 2️⃣ GESTOR (ID = 2)
        // ======================================================
        usuarioRepository.save(criarUsuarioAleatorio(gestorRole));

        // ======================================================
        // 3️⃣ AUDITOR (ID = 3)
        // ======================================================
        usuarioRepository.save(criarUsuarioAleatorio(auditorRole));

        // ======================================================
        // 4️⃣–20️⃣ VOTANTES
        // ======================================================
        for (int i = 0; i < 17; i++) {
            usuarioRepository.save(criarUsuarioAleatorio(votanteRole));
        }
    }

    // ======================================================
    // USUÁRIO FIXO (ADMIN)
    // ======================================================
    private Usuario criarUsuarioFixo(
            String username,
            String senha,
            String nome,
            String sobrenome,
            String email,
            Role role
    ) {

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode(senha))
                .roles(Set.of(role))
                .enabled(true)
                .accountConfirmed(true)
                .build();

        Funcionario funcionario = criarFuncionario(usuario, nome, sobrenome, email);
        usuario.setFuncionario(funcionario);

        return usuario;
    }

    // ======================================================
    // USUÁRIO ALEATÓRIO (FAKER)
    // ======================================================
    private Usuario criarUsuarioAleatorio(Role role) {

        String username = faker.name().username() + faker.number().randomDigit();
        String nome = faker.name().firstName();
        String sobrenome = faker.name().lastName();
        String email = faker.internet().emailAddress();

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode("123456"))
                .roles(Set.of(role))
                .enabled(true)
                .accountConfirmed(true)
                .build();

        Funcionario funcionario = criarFuncionario(usuario, nome, sobrenome, email);
        usuario.setFuncionario(funcionario);

        return usuario;
    }

    // ======================================================
    // FUNCIONÁRIO + ENDEREÇOS + CONTATOS
    // ======================================================
    private Funcionario criarFuncionario(
            Usuario usuario,
            String nome,
            String sobrenome,
            String email
    ) {

        Funcionario funcionario = Funcionario.builder()
                .nome(nome)
                .sobrenome(sobrenome)
                .cpf(faker.number().digits(11))
                .rg(faker.number().digits(9))
                .dataNascimento(
                        faker.timeAndDate().birthday(18, 65)
                )
                .usuario(usuario)
                .enderecos(new HashSet<>())
                .contatos(new HashSet<>())
                .build();

        // ----------------------
        // ENDEREÇOS (5)
        // ----------------------
        for (int i = 0; i < 5; i++) {
            funcionario.getEnderecos().add(
                    Endereco.builder()
                            .logradouro(faker.address().streetName())
                            .numero(faker.address().buildingNumber())
                            .bairro(faker.address().cityName())
                            .cidade(faker.address().city())
                            .uf(UF.values()[faker.random().nextInt(UF.values().length)])
                            .cep(faker.address().zipCode().replaceAll("\\D", ""))
                            .funcionario(funcionario)
                            .build()
            );
        }

        // ----------------------
        // CONTATOS (5)
        // ----------------------
        funcionario.getContatos().add(
                Contato.builder()
                        .tipo(TipoContato.EMAIL)
                        .valor(email)
                        .funcionario(funcionario)
                        .build()
        );

        for (int i = 0; i < 4; i++) {
            funcionario.getContatos().add(
                    Contato.builder()
                            .tipo(TipoContato.TELEFONE)
                            .valor(
                                    faker.phoneNumber()
                                            .cellPhone()
                                            .replaceAll("\\D", "")
                            )
                            .funcionario(funcionario)
                            .build()
            );
        }

        return funcionario;
    }
}
