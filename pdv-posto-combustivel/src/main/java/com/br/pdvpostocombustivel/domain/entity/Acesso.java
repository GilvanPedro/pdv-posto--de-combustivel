package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "acessos")
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String usuario;

    @NotBlank
    private String senha;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "role", length = 50)
    private String role = "FRENTISTA";

    /*constructor*/
    public Acesso(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
        this.role = "FRENTISTA";
    }

    public Acesso(String usuario, String senha, Pessoa pessoa, String role) {
        this.usuario = usuario;
        this.senha = senha;
        this.pessoa = pessoa;
        this.nomeCompleto = pessoa != null ? pessoa.getNomeCompleto() : null;
        this.role = role != null ? role : "FRENTISTA";
    }

    public Acesso() {
        super();
        this.role = "FRENTISTA";
    }

    /*getters*/
    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getRole() {
        return role;
    }

    /*setters*/
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        if (pessoa != null) {
            this.nomeCompleto = pessoa.getNomeCompleto();
        }
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setRole(String role) {
        this.role = role;
    }
}