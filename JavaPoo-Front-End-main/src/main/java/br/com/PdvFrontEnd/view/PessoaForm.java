package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Pessoa;
import br.com.PdvFrontEnd.service.PessoaService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.event.ActionEvent;

public class PessoaForm extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(75, 75, 75);
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);

    private JTextField txtNome;
    private JTextField txtCtps;
    private JTextField txtDataNascimento;
    private JComboBox<String> comboTipo;
    private PessoaService pessoaService;
    private Pessoa pessoaEmEdicao;
    private Runnable onSaveCallback;
    private Runnable onCancelCallback;

    public PessoaForm(PessoaService service, Runnable onSave, Runnable onCancel) {
        this(service, onSave, onCancel, null);
    }

    public PessoaForm(PessoaService service, Runnable onSave, Runnable onCancel, Pessoa pessoa) {
        this.pessoaService = service;
        this.onSaveCallback = onSave;
        this.onCancelCallback = onCancel;
        this.pessoaEmEdicao = pessoa;

        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JLabel header = new JLabel(pessoa == null ? "CADASTRO DE PESSOA" : "EDITAR PESSOA", SwingConstants.CENTER);
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

        mainPanel.add(createStyledLabel("Nome:"));
        txtNome = createStyledTextField();
        mainPanel.add(txtNome);

        mainPanel.add(createStyledLabel("CTPS:"));
        txtCtps = createStyledTextField();
        mainPanel.add(txtCtps);

        mainPanel.add(createStyledLabel("Data de Nascimento:"));
        txtDataNascimento = createStyledTextField();
        mainPanel.add(txtDataNascimento);

        mainPanel.add(createStyledLabel("Tipo:"));
        comboTipo = createStyledComboBox(new String[]{"Cliente", "Fornecedor"});
        mainPanel.add(comboTipo);

        if (pessoaEmEdicao != null) {
            txtNome.setText(pessoaEmEdicao.getNome());
            txtCtps.setText(pessoaEmEdicao.getCpf());
            txtDataNascimento.setText(pessoaEmEdicao.getDataNascimento());
            comboTipo.setSelectedItem(pessoaEmEdicao.getTipo());
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        add(buttonPanel, BorderLayout.SOUTH);

        JButton btnSalvar = createStyledButton("Salvar", PRIMARY_COLOR);
        btnSalvar.addActionListener(this::salvarPessoa);
        buttonPanel.add(btnSalvar);

        JButton btnCancelar = createStyledButton("Cancelar", SECONDARY_COLOR);
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

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBackground(Color.WHITE);
        combo.setForeground(SECONDARY_COLOR);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return combo;
    }

    private JButton createStyledButton(String texto, Color fundo) {
        JButton btn = new JButton(texto);
        btn.setBackground(fundo);
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
                btn.setBackground(fundo);
            }
        });
        return btn;
    }

    private void salvarPessoa(ActionEvent e) {
        try {
            Pessoa novaPessoa = new Pessoa();
            novaPessoa.setNome(txtNome.getText());
            novaPessoa.setCpf(txtCtps.getText());
            novaPessoa.setDataNascimento(txtDataNascimento.getText());
            novaPessoa.setTipo(comboTipo.getSelectedItem().toString());

            if (pessoaEmEdicao == null) {
                pessoaService.addPessoa(novaPessoa);
            } else {
                pessoaService.updatePessoa(pessoaEmEdicao.getId(), novaPessoa);
            }

            onSaveCallback.run();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pessoa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
