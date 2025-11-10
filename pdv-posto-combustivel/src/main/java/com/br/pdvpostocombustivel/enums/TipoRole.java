package com.br.pdvpostocombustivel.enums;

public enum TipoRole {

    ADMIN("Administrador"),
    FRENTISTA("Frentista");

    private final String descricao;

    private TipoRole(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

