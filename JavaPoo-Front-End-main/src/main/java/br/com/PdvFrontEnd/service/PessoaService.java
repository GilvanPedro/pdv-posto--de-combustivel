package br.com.PdvFrontEnd.service;

import br.com.PdvFrontEnd.dto.PageResponse;
import br.com.PdvFrontEnd.dto.PessoaRequest;
import br.com.PdvFrontEnd.dto.PessoaResponse;
import br.com.PdvFrontEnd.model.Pessoa;
import br.com.PdvFrontEnd.util.HttpClient;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class PessoaService {

    private static final DateTimeFormatter FORMAT_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public PessoaService() {
    }

    public List<Pessoa> listPessoas() {
        try {
            TypeReference<PageResponse<PessoaResponse>> typeRef = new TypeReference<>() {};
            PageResponse<PessoaResponse> pageResponse = HttpClient.get("/v1/pessoas?size=1000", typeRef);

            List<Pessoa> pessoas = new ArrayList<>();
            for (PessoaResponse pessoaResponse : pageResponse.getContent()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(pessoaResponse.getId());
                pessoa.setNome(pessoaResponse.getNomeCompleto());
                pessoa.setCpf(pessoaResponse.getCpfCnpj());
                pessoa.setDataNascimento(pessoaResponse.getDataNascimento() != null ?
                        pessoaResponse.getDataNascimento().toString() : "");
                pessoa.setTipo(pessoaResponse.getTipoPessoa() != null ?
                        pessoaResponse.getTipoPessoa() : "");
                pessoas.add(pessoa);
            }
            return pessoas;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao listar pessoas: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void addPessoa(Pessoa pessoa) {
        try {
            PessoaRequest request = new PessoaRequest();
            request.setNomeCompleto(pessoa.getNome());
            request.setCpfCnpj(pessoa.getCpf());

            if (pessoa.getDataNascimento() != null && !pessoa.getDataNascimento().isEmpty()) {
                LocalDate dataNascimento = parseDataFlexivel(pessoa.getDataNascimento());
                request.setDataNascimento(dataNascimento);
            }

            request.setTipoPessoa(pessoa.getTipo().toUpperCase());

            HttpClient.post("/v1/pessoas", request, PessoaResponse.class);
            JOptionPane.showMessageDialog(null,
                    "Pessoa adicionada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao adicionar pessoa: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removePessoa(Long id) {
        try {
            HttpClient.delete("/v1/pessoas/" + id);
            JOptionPane.showMessageDialog(null,
                    "Pessoa removida com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao remover pessoa: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Pessoa getPessoaById(Long id) {
        try {
            PessoaResponse pessoaResponse = HttpClient.get("/v1/pessoas/" + id, PessoaResponse.class);
            Pessoa pessoa = new Pessoa();
            pessoa.setId(pessoaResponse.getId());
            pessoa.setNome(pessoaResponse.getNomeCompleto());
            pessoa.setCpf(pessoaResponse.getCpfCnpj());
            pessoa.setDataNascimento(pessoaResponse.getDataNascimento() != null ?
                    pessoaResponse.getDataNascimento().toString() : "");
            pessoa.setTipo(pessoaResponse.getTipoPessoa() != null ?
                    pessoaResponse.getTipoPessoa() : "");
            return pessoa;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar pessoa por ID: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void updatePessoa(Long id, Pessoa pessoa) {
        try {
            PessoaRequest request = new PessoaRequest();
            request.setNomeCompleto(pessoa.getNome());
            request.setCpfCnpj(pessoa.getCpf());

            if (pessoa.getDataNascimento() != null && !pessoa.getDataNascimento().isEmpty()) {
                LocalDate dataNascimento = parseDataFlexivel(pessoa.getDataNascimento());
                request.setDataNascimento(dataNascimento);
            }

            request.setTipoPessoa(pessoa.getTipo().toUpperCase());

            HttpClient.put("/v1/pessoas/" + id, request, PessoaResponse.class);
            JOptionPane.showMessageDialog(null,
                    "Pessoa atualizada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar pessoa: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 🔧 Novo método para aceitar múltiplos formatos de data
    private LocalDate parseDataFlexivel(String data) {
        try {
            return LocalDate.parse(data, FORMAT_BR); // tenta dd/MM/yyyy
        } catch (DateTimeParseException e) {
            return LocalDate.parse(data, FORMAT_ISO); // tenta yyyy-MM-dd
        }
    }
}
