package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Produto;
import br.com.PdvFrontEnd.service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class ProdutoList extends JPanel {
    private final ProdutoService produtoService;
    private JTable table;
    private JPanel cardPanel; // Painel que gerencia a troca de views (Lista vs Formulário)
    private final static String LIST_VIEW = "Lista";
    private final static String FORM_VIEW = "Formulário";

    // Cores para a nova interface
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(75, 75, 75);
    private static final Color TABLE_HEADER_COLOR = new Color(52, 73, 94);
    private static final Color TABLE_SELECTION_COLOR = new Color(142, 68, 173);
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);

    public ProdutoList(ProdutoService service) {
        this.produtoService = service;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        // 1. Painel de Cartões (CardLayout)
        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(createListView(), LIST_VIEW);

        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createListView() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel header = new JLabel("GERENCIAMENTO DE PRODUTOS", SwingConstants.CENTER);
        header.setFont(new Font("Arial Black", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 3),
                new EmptyBorder(15, 0, 15, 0)
        ));
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
                new EmptyBorder(10, 10, 10, 10)
        ));

        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(BACKGROUND_COLOR);
        table.setSelectionBackground(TABLE_SELECTION_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        table.getTableHeader().setForeground(TEXT_COLOR);
        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(SECONDARY_COLOR);

        JButton btnAdicionar = criarBotao("Adicionar", PRIMARY_COLOR, TEXT_COLOR);
        JButton btnEditar = criarBotao("Editar", PRIMARY_COLOR, TEXT_COLOR);
        JButton btnRemover = criarBotao("Remover", PRIMARY_COLOR, TEXT_COLOR);
        JButton btnAtualizar = criarBotao("Atualizar", PRIMARY_COLOR, TEXT_COLOR);

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnRemover);
        buttonPanel.add(btnAtualizar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> exibirFormulario(null));
        btnEditar.addActionListener(this::editarProduto);
        btnRemover.addActionListener(this::removerProduto);
        btnAtualizar.addActionListener(e -> atualizarTabela());

        return mainPanel;
    }

    private JButton criarBotao(String texto, Color fundo, Color textoCor) {
        JButton btn = new JButton(texto);
        btn.setBackground(fundo);
        btn.setForeground(textoCor);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                new EmptyBorder(8, 16, 8, 16)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Estilos de botão removidos para simplificar, mas podem ser adicionados de volta
        return btn;
    }

    public void atualizarTabela() {
        String[] colunas = {"ID", "Nome", "Referência", "Fornecedor", "Categoria", "Marca"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Produto> produtos = produtoService.getAllProdutos();
        for (Produto produto : produtos) {
            model.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getReferencia(),
                    produto.getFornecedor(),
                    produto.getCategoria(),
                    produto.getMarca()
            });
        }

        table.setModel(model);
        // Esconder a coluna ID para um visual mais limpo, mas manter o dado para edição/remoção
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setWidth(0);
        }
    }

    private void exibirFormulario(Produto produto) {
        // Remove o formulário anterior, se houver
        if (cardPanel.getComponentCount() > 1) {
            cardPanel.remove(1);
        }

        // Callbacks para o formulário
        Runnable onSave = () -> {
            atualizarTabela();
            exibirLista();
        };
        Runnable onCancel = this::exibirLista;

        // Cria o novo formulário
        ProdutoForm form = new ProdutoForm(produtoService, onSave, onCancel, produto);
        cardPanel.add(form, FORM_VIEW);

        // Exibe o formulário
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, FORM_VIEW);
    }

    private void exibirLista() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, LIST_VIEW);
    }

    private void editarProduto(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Obtém o ID do produto selecionado
                Long produtoId = (Long) table.getValueAt(selectedRow, 0);
                Produto produto = produtoService.getProdutoById(produtoId);
                exibirFormulario(produto);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar produto para edição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerProduto(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Long produtoId = (Long) table.getValueAt(selectedRow, 0);
                String nome = (String) table.getValueAt(selectedRow, 1);
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover " + nome + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    produtoService.removeProduto(produtoId);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Produto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
