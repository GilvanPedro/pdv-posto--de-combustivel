package com.br.pdvpostocombustivel.config;

import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner initDatabase(AcessoRepository acessoRepository) {
        return args -> {

            // Criar ADMIN padrão se não existir
            if (!acessoRepository.existsByUsuario("admin")) {
                Acesso admin = new Acesso();
                admin.setUsuario("admin");
                admin.setSenha("admin123");
                admin.setRole("ADMIN");
                admin.setNomeCompleto("Administrador do Sistema");
                acessoRepository.save(admin);
                System.out.println("Usuário ADMIN criado com sucesso (admin / admin123)");
            }

            // Criar FRENTISTA padrão se não existir
            if (!acessoRepository.existsByUsuario("frentista")) {
                Acesso frentista = new Acesso();
                frentista.setUsuario("frentista");
                frentista.setSenha("1234");
                frentista.setRole("FRENTISTA");
                frentista.setNomeCompleto("Frentista Padrão");
                acessoRepository.save(frentista);
                System.out.println("Usuário FRENTISTA criado com sucesso (frentista / 1234)");
            }
        };
    }
}
