package com.br.pdvpostocombustivel.enums;

public enum StatusBomba {

    DISPONIVEL("Disponível"),
    OCUPADA("Ocupada"),
    MANUTENCAO("Em Manutenção"),
    FORA_DE_SERVICO("Fora de Serviço");

    private final String descricao;

    private StatusBomba(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

