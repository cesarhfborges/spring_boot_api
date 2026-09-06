package br.com.chfb.api.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MigrateFreshConfig {

    @Bean
    @Profile("migrate-fresh")
    public FlywayMigrationStrategy cleanMigrateStrategy(ApplicationContext context) {
        return flyway -> {
            System.out.println("==========================================================");
            System.out.println("🔄 INICIANDO MIGRATE:FRESH (LIMPANDO E RECRIANDO O BANCO) 🔄");
            System.out.println("==========================================================");
            
            flyway.clean();
            flyway.migrate();
            
            System.out.println("==========================================================");
            System.out.println("🚀 DATABASE LIMPO E RECRIADO COM SUCESSO (migrate:fresh) 🚀");
            System.out.println("==========================================================");
            
            // Encerra a aplicação após recriar o banco (comportamento de CLI)
            System.exit(SpringApplication.exit(context, () -> 0));
        };
    }
}
