package br.com.chfb.api.seeds;

import br.com.chfb.api.model.Role;
import br.com.chfb.api.repository.RoleRepository;
import br.com.chfb.api.seeds.contract.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeed implements Seed {

    private final RoleRepository roleRepository;

    @Override
    public void run() {
        List<String> roles = List.of(
                "ROLE_ADMIN",
                "ROLE_GESTOR",
                "ROLE_AUDITOR",
                "ROLE_VOTANTE"
        );

        for (String roleName : roles) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(
                            Role.builder().name(roleName).build()
                    ));
        }
    }
}
