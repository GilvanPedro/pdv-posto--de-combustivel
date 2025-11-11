package br.com.PdvFrontEnd.service;

import br.com.PdvFrontEnd.dto.ProdutoRequest;
import br.com.PdvFrontEnd.dto.ProdutoResponse;
import br.com.PdvFrontEnd.model.Produto;
import br.com.PdvFrontEnd.util.HttpClient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    public ProdutoService() {
    }

    public void addProduto(Produto produto) {
        try {
            ProdutoRequest request = new ProdutoRequest(
                    produto.getNome(),
                    produto.getReferencia(),
                    produto.getFornecedor(),
                    produto.getCategoria(),
                    produto.getMarca()
            );

            HttpClient.post("/produtos", request, ProdutoResponse.class);
            JOptionPane.showMessageDialog(null,
                    "Produto adicionado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao adicionar produto: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Produto> getAllProdutos() {
        try {
            ProdutoResponse[] responses = HttpClient.getArray("/produtos", ProdutoResponse[].class);
            List<Produto> produtos = new ArrayList<>();
            for (ProdutoResponse response : responses) {
                Produto produto = new Produto(
                        response.getNome(),
                        response.getReferencia(),
                        response.getFornecedor(),
                        response.getCategoria(),
                        response.getMarca()
                );
                produto.setId(response.getId());
                produtos.add(produto);
            }
            return produtos;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao listar produtos: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void removeProduto(Long id) {
        try {
            HttpClient.delete("/produtos/" + id);
            JOptionPane.showMessageDialog(null,
                    "Produto removido com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao remover produto: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Produto getProdutoById(Long id) {
        try {
            ProdutoResponse response = HttpClient.get("/produtos/" + id, ProdutoResponse.class);
            Produto produto = new Produto(
                    response.getNome(),
                    response.getReferencia(),
                    response.getFornecedor(),
                    response.getCategoria(),
                    response.getMarca()
            );
            produto.setId(response.getId());
            return produto;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar produto por ID: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void updateProduto(Produto produto) {
        try {
            ProdutoRequest request = new ProdutoRequest(
                    produto.getNome(),
                    produto.getReferencia(),
                    produto.getFornecedor(),
                    produto.getCategoria(),
                    produto.getMarca()
            );

            HttpClient.put("/produtos/" + produto.getId(), request, ProdutoResponse.class);
            JOptionPane.showMessageDialog(null,
                    "Produto atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar produto: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
