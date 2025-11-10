package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class RegisterAdminView extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private SessionManager sessionManager;

    public RegisterAdminView() {
        this.sessionManager = SessionManager.getInstance();
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro Administrador - Sistema PDV");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(SECONDARY_COLOR);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título
        JLabel lblTitle = new JLabel("Cadastro Admin");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(ACCENT_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Subtítulo
        JLabel lblSubtitle = new JLabel("Configure a conta do Administrador");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(TEXT_COLOR);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblSubtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Campo Username
        JLabel lblUsername = new JLabel("Usuário Admin:");
        lblUsername.setForeground(TEXT_COLOR);
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblUsername);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        txtUsername = new JTextField(20);
        txtUsername.setMaximumSize(new Dimension(300, 35));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(txtUsername);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campo Password
        JLabel lblPassword = new JLabel("Senha:");
        lblPassword.setForeground(TEXT_COLOR);
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        txtPassword = new JPasswordField(20);
        txtPassword.setMaximumSize(new Dimension(300, 35));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(txtPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campo Confirmar Senha
        JLabel lblConfirmPassword = new JLabel("Confirmar Senha:");
        lblConfirmPassword.setForeground(TEXT_COLOR);
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblConfirmPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblConfirmPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setMaximumSize(new Dimension(300, 35));
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtConfirmPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(txtConfirmPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Botão Cadastrar
        JButton btnRegister = createStyledButton("Cadastrar Admin");
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(300, 40));
        btnRegister.setBackground(ACCENT_COLOR);
        btnRegister.addActionListener(e -> handleRegister());
        mainPanel.add(btnRegister);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botão Voltar
        JButton btnBack = createStyledButton("Voltar");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setMaximumSize(new Dimension(300, 40));
        btnBack.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        mainPanel.add(btnBack);

        add(mainPanel, BorderLayout.CENTER);

        // Enter para cadastrar
        txtConfirmPassword.addActionListener(e -> handleRegister());
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "O nome de usuário deve ter pelo menos 3 caracteres!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "A senha deve ter pelo menos 4 caracteres!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "As senhas não coincidem!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtConfirmPassword.setText("");
            return;
        }

        // Salva as credenciais do Admin
        sessionManager.saveAdminCredentials(username, password);

        JOptionPane.showMessageDialog(this,
                "Administrador cadastrado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        // Volta para a tela de login
        new LoginView().setVisible(true);
        dispose();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(ACCENT_COLOR)) {
                    button.setBackground(ACCENT_COLOR);
                } else {
                    button.setBackground(PRIMARY_COLOR);
                }
            }
        });

        return button;
    }
}

