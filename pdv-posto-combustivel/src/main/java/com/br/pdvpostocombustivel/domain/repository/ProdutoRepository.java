package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Marca a interface como um componente de repositório do Spring.
@Repository
// Interface de repositório para a entidade Produto.
// Estende JpaRepository, fornecendo métodos CRUD básicos (Create, Read, Update, Delete)
// e métodos de consulta para a entidade Produto (tipo da entidade) e Long (tipo da chave primária).
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
