package br.com.PdvFrontEnd.model;

import java.math.BigDecimal;
import java.util.Date;

public class Preco {

    // atributos
    private Long id;
    private BigDecimal valor;
    private Date dataAlteracao;
    private Date horaAlteracao;

    // construtor para cadastro
    public Preco(BigDecimal valor, Date dataAlteracao, Date horaAlteracao) {
        this(null, valor, dataAlteracao, horaAlteracao);
    }

    // construtor completo para edição
    public Preco(Long id, BigDecimal valor, Date dataAlteracao, Date horaAlteracao) {
        this.id = id;
        this.valor = valor;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
    }


    // getters
    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public Date getHoraAlteracao() {
        return horaAlteracao;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setHoraAlteracao(Date horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }
}
