package com.br.pdvpostocombustivel.api.acesso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para requisição de Acesso")
public class AcessoRequest {
    @NotBlank
    @Schema(description = "Nome de usuário", example = "joao.silva")
    private String usuario;

    @NotBlank
    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;

    @Schema(description = "ID da pessoa associada", example = "1")
    private Long pessoaId;

    @Schema(description = "Função do usuário (ADMIN ou FRENTISTA)", example = "FRENTISTA")
    private String role;

    // Getters e Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
