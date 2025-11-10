package com.br.pdvpostocombustivel.enums;

public enum FormaPagamento {

    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    PIX("PIX"),
    CHEQUE("Cheque"),
    VALE_COMBUSTIVEL("Vale Combustível"),
    CORTESIA("Cortesia");

    private final String descricao;

    private FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

