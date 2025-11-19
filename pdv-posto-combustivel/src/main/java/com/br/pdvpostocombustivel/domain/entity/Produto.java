package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Marca a classe como uma entidade JPA, mapeando-a para uma tabela no banco de dados.
@Entity
// Especifica o nome da tabela no banco de dados.
@Table(name = "produtos")
// Entidade que representa um Produto no sistema.
public class Produto {

    // Marca o campo como a chave primária da tabela.
    @Id
    // Configura a geração automática do valor da chave primária (auto-incremento).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // Identificador único do produto.

        private String nome; // Nome do produto.
        private String referencia; // Código de referência ou SKU do produto.
        private String fornecedor; // Nome do fornecedor.
        private String categoria; // Categoria do produto (ex: Combustível, Óleo, Pneu).
        private String marca; // Marca do produto.

    // Construtor existente
    public Produto(String nome, String referencia, String fornecedor, String categoria, String marca) {
        this.nome = nome;
        this.referencia = referencia;
        this.fornecedor = fornecedor;
        this.categoria = categoria;
        this.marca = marca;
    }

    public Produto() {
        super();
    }

    // Getter e Setter para ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters e setters existentes
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}