package com.br.pdvpostocombustivel.api.acesso.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para resposta de Acesso")
public class AcessoResponse {
    @Schema(description = "ID do acesso")
    private Long id;

    @Schema(description = "Nome de usuário", example = "joao.silva")
    private String usuario;

    @Schema(description = "Nome completo da pessoa")
    private String nomeCompleto;

    @Schema(description = "Função do usuário (ADMIN ou FRENTISTA)", example = "FRENTISTA")
    private String role;

    @Schema(description = "ID da pessoa associada")
    private Long pessoaId;

    @Schema(description = "Senha (retornada apenas para compatibilidade)")
    private String senha;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
