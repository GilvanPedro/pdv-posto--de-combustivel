package com.br.pdvpostocombustivel.enums;

public enum CategoriaProduto {

    COMBUSTIVEL("Combustível"),
    LUBRIFICANTE("Lubrificante"),
    ADITIVO("Aditivo"),
    LIMPEZA("Produto de Limpeza"),
    ALIMENTACAO("Alimentação"),
    BEBIDA("Bebida"),
    CONVENIENCIA("Conveniência"),
    AUTOMOTIVO("Automotivo"),
    OUTROS("Outros");

    private final String descricao;

    private CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

