package br.com.chfb.api.seeds;

import br.com.chfb.api.seeds.contract.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PautaSeed implements Seed {

//    private final UsuarioRepository usuarioRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public void run() {
//        if (usuarioRepository.count() > 0) {
//            return;
//        }
//
//        String[][] usuarios = {
//                {"admin", "admin123"},
//                {"usuario", "user123"}
//        };
//
//        for (String[] dados : usuarios) {
//            Usuario usuario = Usuario.builder()
//                    .username(dados[0])
//                    .password(passwordEncoder.encode(dados[1]))
//                    .build();
//
//            usuarioRepository.save(usuario);
//        }
    }
}
