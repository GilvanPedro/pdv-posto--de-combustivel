package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Estoque;
import br.com.PdvFrontEnd.service.EstoqueService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EstoqueForm extends JPanel {
    // Cores para a nova interface
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(75, 75, 75);
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);

    private JTextField txtQuantidade;
    private JTextField txtLocalTanque;
    private JTextField txtLocalEndereco;
    private JTextField txtLoteFabricacao;
    private JTextField txtDataValidade;
    private EstoqueService estoqueService;
    private Estoque estoqueEmEdicao;
    private Runnable onSaveCallback;
    private Runnable onCancelCallback;

    public EstoqueForm(EstoqueService service, Runnable onSave, Runnable onCancel) {
        this(service, onSave, onCancel, null);
    }

    public EstoqueForm(EstoqueService service, Runnable onSave, Runnable onCancel, Estoque estoque) {
        this.estoqueService = service;
        this.onSaveCallback = onSave;
        this.onCancelCallback = onCancel;
        this.estoqueEmEdicao = estoque;

        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Título do Formulário
        JLabel header = new JLabel(estoque == null ? "CADASTRO DE ESTOQUE" : "EDITAR ESTOQUE", SwingConstants.CENTER);
        header.setFont(new Font("Arial Black", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel, BorderLayout.CENTER);

        // Campos do Formulário
        mainPanel.add(createStyledLabel("Quantidade:"));
        txtQuantidade = createStyledTextField();
        mainPanel.add(txtQuantidade);

        mainPanel.add(createStyledLabel("Local Tanque:"));
        txtLocalTanque = createStyledTextField();
        mainPanel.add(txtLocalTanque);

        mainPanel.add(createStyledLabel("Local Endereço:"));
        txtLocalEndereco = createStyledTextField();
        mainPanel.add(txtLocalEndereco);

        mainPanel.add(createStyledLabel("Lote Fabricação:"));
        txtLoteFabricacao = createStyledTextField();
        mainPanel.add(txtLoteFabricacao);

        mainPanel.add(createStyledLabel("Data de Validade (dd/MM/yyyy):"));
        txtDataValidade = createStyledTextField();
        mainPanel.add(txtDataValidade);

        // Se estiver editando, preencher os campos
        if (estoqueEmEdicao != null) {
            txtQuantidade.setText(estoqueEmEdicao.getQuantidade().toString());
            txtLocalTanque.setText(estoqueEmEdicao.getLocalTanque());
            txtLocalEndereco.setText(estoqueEmEdicao.getLocalEndereco());
            txtLoteFabricacao.setText(estoqueEmEdicao.getLoteFabricacao());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            txtDataValidade.setText(dateFormat.format(estoqueEmEdicao.getDataValidade()));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        add(buttonPanel, BorderLayout.SOUTH);

        JButton btnSalvar = createStyledButton("Salvar");
        btnSalvar.addActionListener(this::salvarEstoque);
        buttonPanel.add(btnSalvar);

        JButton btnCancelar = createStyledButton("Cancelar");
        btnCancelar.addActionListener(e -> onCancelCallback.run());
        buttonPanel.add(btnCancelar);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
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

    private void salvarEstoque(ActionEvent e) {
        try {
            BigDecimal quantidade = new BigDecimal(txtQuantidade.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataValidade = dateFormat.parse(txtDataValidade.getText());

            Estoque novoEstoque = new Estoque(
                    null, // ID será definido pelo backend
                    quantidade,
                    txtLocalTanque.getText(),
                    txtLocalEndereco.getText(),
                    txtLoteFabricacao.getText(),
                    dataValidade
            );

            if (estoqueEmEdicao != null) {
                // Modo edição
                novoEstoque.setId(estoqueEmEdicao.getId());
                estoqueService.updateEstoque(estoqueEmEdicao.getId(), novoEstoque);
                JOptionPane.showMessageDialog(this, "Estoque atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Modo criação
                estoqueService.addEstoque(novoEstoque);
                JOptionPane.showMessageDialog(this, "Estoque adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            onSaveCallback.run();

        } catch (NumberFormatException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar estoque: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
