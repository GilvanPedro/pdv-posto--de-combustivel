package com.br.pdvpostocombustivel.enums;

public enum TipoMovimentacaoEstoque {

    ENTRADA("Entrada"),
    SAIDA("Saída"),
    AJUSTE("Ajuste de Estoque"),
    DEVOLUCAO("Devolução"),
    PERDA("Perda/Avaria");

    private final String descricao;

    private TipoMovimentacaoEstoque(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

