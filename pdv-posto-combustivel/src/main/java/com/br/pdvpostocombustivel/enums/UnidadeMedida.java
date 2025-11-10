package com.br.pdvpostocombustivel.enums;

public enum UnidadeMedida {

    LITRO("Litro"),
    UNIDADE("Unidade"),
    KILOGRAMA("Kilograma"),
    GRAMA("Grama"),
    METRO("Metro"),
    CENTIMETRO("Centímetro"),
    CAIXA("Caixa"),
    PACOTE("Pacote");

    private final String descricao;

    private UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

