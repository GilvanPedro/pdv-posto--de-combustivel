package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Pessoa;
import br.com.PdvFrontEnd.service.PessoaService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroPessoaView extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240); // Roxo #8F7DF0
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75); // Cinza escuro
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180); // Roxo mais escuro para abas ativas
    private static final Color TEXT_COLOR = Color.WHITE;

    private JTextField txtNomeCompleto;
    private JTextField txtCpfCnpj;
    private JTextField txtDataNascimento;
    private JComboBox<String> cmbTipoPessoa;
    private PessoaService pessoaService;

    public CadastroPessoaView() {
        this.pessoaService = new PessoaService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Pessoa - Sistema PDV");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(SECONDARY_COLOR);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Título
        JLabel lblTitle = new JLabel("Cadastro de Pessoa");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Preencha seus dados para criar seu perfil");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(189, 195, 199));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblSubtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Campo Nome Completo
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setForeground(TEXT_COLOR);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblNome);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        txtNomeCompleto = new JTextField(20);
        txtNomeCompleto.setMaximumSize(new Dimension(400, 40));
        txtNomeCompleto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNomeCompleto.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNomeCompleto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(127, 140, 141), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtNomeCompleto);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campo CPF/CNPJ
        JLabel lblCpfCnpj = new JLabel("CPF ou CNPJ:");
        lblCpfCnpj.setForeground(TEXT_COLOR);
        lblCpfCnpj.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCpfCnpj.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblCpfCnpj);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        txtCpfCnpj = new JTextField(20);
        txtCpfCnpj.setMaximumSize(new Dimension(400, 40));
        txtCpfCnpj.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCpfCnpj.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtCpfCnpj.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(127, 140, 141), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtCpfCnpj);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campo Data de Nascimento
        JLabel lblDataNascimento = new JLabel("Data de Nascimento (dd/MM/yyyy):");
        lblDataNascimento.setForeground(TEXT_COLOR);
        lblDataNascimento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDataNascimento.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblDataNascimento);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        txtDataNascimento = new JTextField(20);
        txtDataNascimento.setMaximumSize(new Dimension(400, 40));
        txtDataNascimento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDataNascimento.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtDataNascimento.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(127, 140, 141), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtDataNascimento);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campo Tipo de Pessoa
        JLabel lblTipoPessoa = new JLabel("Tipo de Pessoa:");
        lblTipoPessoa.setForeground(TEXT_COLOR);
        lblTipoPessoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTipoPessoa.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTipoPessoa);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        cmbTipoPessoa = new JComboBox<>(new String[]{"FISICA", "JURIDICA"});
        cmbTipoPessoa.setMaximumSize(new Dimension(400, 40));
        cmbTipoPessoa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTipoPessoa.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(cmbTipoPessoa);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Painel de Botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(SECONDARY_COLOR);
        buttonPanel.setMaximumSize(new Dimension(400, 45));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Cadastrar
        JButton btnCadastrar = createStyledButton("Cadastrar", SUCCESS_COLOR);
        btnCadastrar.setPreferredSize(new Dimension(190, 45));
        btnCadastrar.addActionListener(e -> handleCadastro());
        buttonPanel.add(btnCadastrar);

        // Botão Cancelar
        JButton btnCancelar = createStyledButton("Cancelar", ACCENT_COLOR);
        btnCancelar.setPreferredSize(new Dimension(190, 45));
        btnCancelar.addActionListener(e -> dispose());
        buttonPanel.add(btnCancelar);

        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Enter para cadastrar
        txtDataNascimento.addActionListener(e -> handleCadastro());
    }

    private void handleCadastro() {
        String nomeCompleto = txtNomeCompleto.getText().trim();
        String cpfCnpj = txtCpfCnpj.getText().trim();
        String dataNascimentoStr = txtDataNascimento.getText().trim();
        String tipoPessoa = (String) cmbTipoPessoa.getSelectedItem();

        // Validações
        if (nomeCompleto.isEmpty() || cpfCnpj.isEmpty() || dataNascimentoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Campos Obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida e converte data
        LocalDate dataNascimento;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida! Use o formato dd/MM/yyyy\nExemplo: 15/03/1990",
                    "Data Inválida",
                    JOptionPane.ERROR_MESSAGE);
            txtDataNascimento.requestFocus();
            return;
        }

        // Verifica se é maior de idade (opcional)
        if (LocalDate.now().minusYears(18).isBefore(dataNascimento)) {
            int resposta = JOptionPane.showConfirmDialog(this,
                    "A pessoa é menor de idade. Deseja continuar?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Cadastra a pessoa
        try {
            // Formatar data para String no formato yyyy-MM-dd
            DateTimeFormatter backendFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataFormatada = dataNascimento.format(backendFormatter);

            Pessoa pessoa = new Pessoa();
            pessoa.setNome(nomeCompleto);  // Usar setNome() ao invés de setNomeCompleto()
            pessoa.setCpf(cpfCnpj);  // Usar setCpf() ao invés de setCpfCnpj()
            pessoa.setDataNascimento(dataFormatada);  // Passar como String
            pessoa.setTipo(tipoPessoa);  // Usar setTipo() ao invés de setTipoPessoa()
            // Não há setAtivo() no modelo, então não definir

            pessoaService.addPessoa(pessoa);

            JOptionPane.showMessageDialog(this,
                    "Pessoa cadastrada com sucesso!\n\n" +
                    "Nome: " + nomeCompleto + "\n" +
                    "CPF/CNPJ: " + cpfCnpj + "\n" +
                    "Tipo: " + tipoPessoa + "\n\n" +
                    "Aguarde o administrador criar suas credenciais de acesso.",
                    "Cadastro Realizado",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar pessoa:\n" + ex.getMessage(),
                    "Erro no Cadastro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNomeCompleto.setText("");
        txtCpfCnpj.setText("");
        txtDataNascimento.setText("");
        cmbTipoPessoa.setSelectedIndex(0);
        txtNomeCompleto.requestFocus();
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color hoverColor = bgColor.darker();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}

