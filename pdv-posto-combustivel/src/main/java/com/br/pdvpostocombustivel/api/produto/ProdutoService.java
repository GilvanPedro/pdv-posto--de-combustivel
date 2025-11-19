package com.br.pdvpostocombustivel.api.produto;

import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Marca a classe como um componente de serviço, contendo a lógica de negócio.
@Service
// Classe de serviço responsável por toda a lógica de negócio da entidade Produto.
public class ProdutoService {

    // Injeção de dependência do repositório para acesso ao banco de dados.
    @Autowired
    private ProdutoRepository produtoRepository;

    // Garante que o método seja executado dentro de uma transação de banco de dados.
    @Transactional
    // Cria um novo produto a partir dos dados da requisição.
    public ProdutoResponse criar(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setReferencia(request.getReferencia());
        produto.setFornecedor(request.getFornecedor());
        produto.setCategoria(request.getCategoria());
        produto.setMarca(request.getMarca());

        produto = produtoRepository.save(produto);
        return converterParaResponse(produto);
    }

    // Busca um produto pelo ID, lançando exceção se não for encontrado.
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        return converterParaResponse(produto);
    }

    // Lista todos os produtos cadastrados.
    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    // Garante que o método seja executado dentro de uma transação de banco de dados.
    @Transactional
    // Atualiza os dados de um produto existente.
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produto.setNome(request.getNome());
        produto.setReferencia(request.getReferencia());
        produto.setFornecedor(request.getFornecedor());
        produto.setCategoria(request.getCategoria());
        produto.setMarca(request.getMarca());

        produto = produtoRepository.save(produto);
        return converterParaResponse(produto);
    }

    // Garante que o método seja executado dentro de uma transação de banco de dados.
    @Transactional
    // Remove um produto pelo ID.
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    // Método utilitário para converter a entidade Produto em um objeto de resposta (DTO).
    private ProdutoResponse converterParaResponse(Produto produto) {
        ProdutoResponse response = new ProdutoResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setReferencia(produto.getReferencia());
        response.setFornecedor(produto.getFornecedor());
        response.setCategoria(produto.getCategoria());
        response.setMarca(produto.getMarca());
        return response;
    }
}
