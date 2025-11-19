package com.br.pdvpostocombustivel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// Anotação principal do Spring Boot, que combina @Configuration, @EnableAutoConfiguration e @ComponentScan.
@SpringBootApplication
// Define o pacote base para a varredura de componentes (Controllers, Services, Repositories, etc.).
@ComponentScan(basePackages = "com.br.pdvpostocombustivel")
// Configuração do OpenAPI (Swagger) para documentação automática da API.
@OpenAPIDefinition(
    info = @Info(
        title = "PDV Posto Combustível API",
        version = "1.0",
        description = "API para gerenciamento de PDV de posto de combustível",
        contact = @Contact(
            name = "Gilvan Pedro",
            email = "gilvanpedro2006@gmail.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8082", description = "Local Development Server")
    }
)
// Classe principal da aplicação.
public class PdvpostocombustivelApplication {
    public static void main(String[] args) {
        // Método que inicia a aplicação Spring Boot.
        SpringApplication.run(PdvpostocombustivelApplication.class, args);
    }
}