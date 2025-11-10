package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public AcessoResponse criar(AcessoRequest request) {
        Acesso acesso = new Acesso();
        acesso.setUsuario(request.getUsuario());
        acesso.setSenha(request.getSenha());

        // Define a role (ADMIN ou FRENTISTA)
        acesso.setRole(request.getRole() != null ? request.getRole() : "FRENTISTA");

        // Se foi informado pessoaId, busca e vincula a pessoa
        if (request.getPessoaId() != null) {
            Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                    .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
            acesso.setPessoa(pessoa);
            acesso.setNomeCompleto(pessoa.getNomeCompleto());
        }

        acesso = acessoRepository.save(acesso);
        return converterParaResponse(acesso);
    }

    public AcessoResponse buscarPorId(Long id) {
        Acesso acesso = acessoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado"));
        return converterParaResponse(acesso);
    }

    public List<AcessoResponse> listarTodos() {
        return acessoRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AcessoResponse atualizar(Long id, AcessoRequest request) {
        Acesso acesso = acessoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado"));

        acesso.setUsuario(request.getUsuario());
        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            acesso.setSenha(request.getSenha());
        }

        // Atualiza role se fornecida
        if (request.getRole() != null) {
            acesso.setRole(request.getRole());
        }

        // Atualiza pessoa se fornecida
        if (request.getPessoaId() != null) {
            Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                    .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
            acesso.setPessoa(pessoa);
            acesso.setNomeCompleto(pessoa.getNomeCompleto());
        }

        acesso = acessoRepository.save(acesso);
        return converterParaResponse(acesso);
    }

    @Transactional
    public void deletar(Long id) {
        if (!acessoRepository.existsById(id)) {
            throw new EntityNotFoundException("Acesso não encontrado");
        }
        acessoRepository.deleteById(id);
    }

    public AcessoResponse login(String usuario, String senha) {
        List<Acesso> acessos = acessoRepository.findAll();

        Acesso acesso = acessos.stream()
                .filter(a -> a.getUsuario().equals(usuario) && a.getSenha().equals(senha))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Usuário ou senha incorretos!"));

        return converterParaResponse(acesso);
    }

    private AcessoResponse converterParaResponse(Acesso acesso) {
        AcessoResponse response = new AcessoResponse();
        response.setId(acesso.getId());
        response.setUsuario(acesso.getUsuario());
        response.setSenha(acesso.getSenha()); // Para compatibilidade com front-end existente
        response.setNomeCompleto(acesso.getNomeCompleto());
        response.setRole(acesso.getRole());
        response.setPessoaId(acesso.getPessoa() != null ? acesso.getPessoa().getId() : null);
        return response;
    }
}
