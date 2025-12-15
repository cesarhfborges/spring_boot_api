package br.com.chfb.api.configuration;

import br.com.chfb.api.seeds.PautaSeed;
import br.com.chfb.api.seeds.RoleSeed;
import br.com.chfb.api.seeds.UsuarioSeed;
import br.com.chfb.api.seeds.contract.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeedRunner implements ApplicationRunner {

    private final ApplicationContext applicationContext;
    private final List<Class<? extends Seed>> seedOrder = List.of(
            RoleSeed.class,
            UsuarioSeed.class,
            PautaSeed.class
    );

    @Override
    public void run(ApplicationArguments args) {
        for (Class<? extends Seed> seedClass : seedOrder) {
            Seed seed = applicationContext.getBean(seedClass);
            seed.run();
        }
    }
}
