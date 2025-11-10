package com.br.pdvpostocombustivel.enums;

public enum TipoContato {

    CELULAR("Celular"),
    RESIDENCIAL("Telefone Residencial"),
    COMERCIAL("Telefone Comercial"),
    WHATSAPP("WhatsApp"),
    RECADO("Telefone para Recado");

    private final String descricao;

    private TipoContato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

