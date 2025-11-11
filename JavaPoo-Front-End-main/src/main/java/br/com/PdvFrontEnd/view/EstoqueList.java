package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Estoque;
import br.com.PdvFrontEnd.service.EstoqueService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class EstoqueList extends JPanel {
    private final EstoqueService estoqueService;
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

    public EstoqueList(EstoqueService service) {
        this.estoqueService = service;
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

        JLabel header = new JLabel("GERENCIAMENTO DE ESTOQUE", SwingConstants.CENTER);
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
        btnEditar.addActionListener(this::editarEstoque);
        btnRemover.addActionListener(this::removerEstoque);
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
        String[] colunas = {"ID", "Quantidade", "Local Tanque", "Local Endereço", "Lote Fabricação", "Data Validade"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Estoque> estoques = estoqueService.getAllEstoques();
        for (Estoque estoque : estoques) {
            model.addRow(new Object[]{
                    estoque.getId(),
                    estoque.getQuantidade(),
                    estoque.getLocalTanque(),
                    estoque.getLocalEndereco(),
                    estoque.getLoteFabricacao(),
                    estoque.getDataValidade()
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

    private void exibirFormulario(Estoque estoque) {
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
        EstoqueForm form = new EstoqueForm(estoqueService, onSave, onCancel, estoque);
        cardPanel.add(form, FORM_VIEW);

        // Exibe o formulário
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, FORM_VIEW);
    }

    private void exibirLista() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, LIST_VIEW);
    }

    private void editarEstoque(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Obtém o ID do estoque selecionado
                Long estoqueId = (Long) table.getValueAt(selectedRow, 0);
                Estoque estoque = estoqueService.getEstoqueById(estoqueId);
                exibirFormulario(estoque);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar estoque para edição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um estoque para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerEstoque(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Long estoqueId = (Long) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este item de estoque?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    estoqueService.removeEstoque(estoqueId);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Estoque removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover estoque: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um estoque para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
