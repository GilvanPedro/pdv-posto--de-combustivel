package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Produto;
import br.com.PdvFrontEnd.service.ProdutoService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.event.ActionEvent;

public class ProdutoForm extends JPanel {
    // Cores para a nova interface
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(75, 75, 75);
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);
    private static final Color BG2 = new Color(255, 255, 255);

    private JTextField txtNome;
    private JTextField txtReferencia;
    private JTextField txtFornecedor;
    private JTextField txtCategoria;
    private JTextField txtMarca;
    private ProdutoService produtoService;
    private Produto produtoEmEdicao;
    private Runnable onSaveCallback;
    private Runnable onCancelCallback;

    public ProdutoForm(ProdutoService service, Runnable onSave, Runnable onCancel) {
        this(service, onSave, onCancel, null);
    }

    public ProdutoForm(ProdutoService service, Runnable onSave, Runnable onCancel, Produto produto) {
        this.produtoService = service;
        this.onSaveCallback = onSave;
        this.onCancelCallback = onCancel;
        this.produtoEmEdicao = produto;

        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Título do Formulário
        JLabel header = new JLabel(produto == null ? "CADASTRO DE PRODUTO" : "EDITAR PRODUTO", SwingConstants.CENTER);
        header.setFont(new Font("Arial Black", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBackground(BG2); // Mantendo a cor de fundo original do form
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel, BorderLayout.CENTER);

        // Campos do Formulário
        mainPanel.add(createStyledLabel("Nome:"));
        txtNome = createStyledTextField();
        mainPanel.add(txtNome);

        mainPanel.add(createStyledLabel("Referência:"));
        txtReferencia = createStyledTextField();
        mainPanel.add(txtReferencia);

        mainPanel.add(createStyledLabel("Fornecedor:"));
        txtFornecedor = createStyledTextField();
        mainPanel.add(txtFornecedor);

        mainPanel.add(createStyledLabel("Categoria:"));
        txtCategoria = createStyledTextField();
        mainPanel.add(txtCategoria);

        mainPanel.add(createStyledLabel("Marca:"));
        txtMarca = createStyledTextField();
        mainPanel.add(txtMarca);

        // Se estiver editando, preencher os campos
        if (produtoEmEdicao != null) {
            txtNome.setText(produtoEmEdicao.getNome());
            txtReferencia.setText(produtoEmEdicao.getReferencia());
            txtFornecedor.setText(produtoEmEdicao.getFornecedor());
            txtCategoria.setText(produtoEmEdicao.getCategoria());
            txtMarca.setText(produtoEmEdicao.getMarca());
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        add(buttonPanel, BorderLayout.SOUTH);

        JButton btnSalvar = createStyledButton("Salvar");
        btnSalvar.addActionListener(this::salvarProduto);
        buttonPanel.add(btnSalvar);

        JButton btnCancelar = createStyledButton("Cancelar");
        btnCancelar.addActionListener(e -> onCancelCallback.run());
        buttonPanel.add(btnCancelar);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(SECONDARY_COLOR); // Cor do texto para BG2 (branco)
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(Color.WHITE);
        field.setForeground(SECONDARY_COLOR);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(TEXT_COLOR);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setUI(new BasicButtonUI() {
            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                ((AbstractButton) c).setOpaque(true);
            }

            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                g.setColor(BUTTON_HOVER_COLOR);
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
            }
        });
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });
        return btn;
    }

    private void salvarProduto(ActionEvent e) {
        try {
            Produto novoProduto = new Produto(
                    txtNome.getText(),
                    txtReferencia.getText(),
                    txtFornecedor.getText(),
                    txtCategoria.getText(),
                    txtMarca.getText()
            );

            if (produtoEmEdicao != null) {
                // Modo edição
                novoProduto.setId(produtoEmEdicao.getId());
                produtoService.updateProduto(novoProduto);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Modo criação
                produtoService.addProduto(novoProduto);
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            onSaveCallback.run();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
