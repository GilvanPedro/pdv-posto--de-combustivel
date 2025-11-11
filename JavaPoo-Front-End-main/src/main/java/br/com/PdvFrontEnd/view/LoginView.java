package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.util.SessionManager;
import br.com.PdvFrontEnd.service.AcessoService;
import br.com.PdvFrontEnd.model.Acesso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(138, 43, 226); // Roxo Elétrico (Accent)
    private static final Color SECONDARY_COLOR = new Color(30, 30, 30); // Fundo Principal (Cinza Escuro)
    private static final Color ACCENT_COLOR = new Color(44, 44, 44); // Card de Login (Cinza Médio Escuro)
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80); // Sucesso (Verde)
    private static final Color PURPLE_COLOR = new Color(255, 152, 0); // Alerta (Laranja)
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_HOVER_COLOR = new Color(150, 60, 240); // Roxo mais claro para hover

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JRadioButton rbFrentista;
    private JRadioButton rbAdmin;
    private SessionManager sessionManager;
    private AcessoService acessoService;

    public LoginView() {
        this.sessionManager = SessionManager.getInstance();
        this.acessoService = new AcessoService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Sistema PDV");
        setSize(480, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false);

        // Painel de fundo com cor sólida minimalista
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                g2d.setColor(SECONDARY_COLOR);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        // Card principal minimalista
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Card de Login (ACCENT_COLOR)
                g2d.setColor(ACCENT_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Borda de destaque (Roxo Elétrico)
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 25, 25);

                g2d.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Espaçamento superior
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Título com quebra de linha: PDV Posto de Combustível
        JLabel lblTitle = new JLabel("<html><center>PDV Posto de<br>Combustível</center></html>");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Acesso ao Sistema");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitle.setForeground(PRIMARY_COLOR);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblSubtitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Tipo de usuário com painel estilizado
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        radioPanel.setOpaque(false);

        rbFrentista = createStyledRadioButton("Frentista", true);
        rbAdmin = createStyledRadioButton("Administrador", false);

        ButtonGroup group = new ButtonGroup();
        group.add(rbFrentista);
        group.add(rbAdmin);

        radioPanel.add(rbFrentista);
        radioPanel.add(rbAdmin);
        cardPanel.add(radioPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Campo Username
        JLabel lblUsername = new JLabel("Usuário");
        lblUsername.setForeground(TEXT_COLOR);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblUsername);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        txtUsername = createStyledTextField(20);
        txtUsername.setMaximumSize(new Dimension(340, 45));
        cardPanel.add(txtUsername);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo Password
        JLabel lblPassword = new JLabel("Senha");
        lblPassword.setForeground(TEXT_COLOR);
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblPassword);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        txtPassword = createStyledPasswordField(20);
        txtPassword.setMaximumSize(new Dimension(340, 45));
        cardPanel.add(txtPassword);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão Login com cor sólida de destaque
        JButton btnLogin = createModernButton("ENTRAR", PRIMARY_COLOR, BUTTON_HOVER_COLOR);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(340, 50));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.addActionListener(e -> handleLogin());
        cardPanel.add(btnLogin);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Botão Preencher Informações
        JButton btnPreencherInfo = createModernButton("Cadastrar", SUCCESS_COLOR, SUCCESS_COLOR.darker());
        btnPreencherInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPreencherInfo.setMaximumSize(new Dimension(340, 45));
        btnPreencherInfo.addActionListener(e -> new CadastroPessoaView().setVisible(true));
        cardPanel.add(btnPreencherInfo);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 12)));


        // Botão Cadastrar Admin (só se não existir admin)
        if (!sessionManager.adminExists()) {
            JButton btnRegisterAdmin = createModernButton("Cadastrar Admin", PURPLE_COLOR, PURPLE_COLOR.darker());
            btnRegisterAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnRegisterAdmin.setMaximumSize(new Dimension(340, 45));
            btnRegisterAdmin.addActionListener(e -> {
                new RegisterAdminView().setVisible(true);
                dispose();
            });
            cardPanel.add(btnRegisterAdmin);
        }

        // Centralizar o card na tela
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(cardPanel);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // Enter para fazer login
        txtPassword.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tenta fazer login via backend
        try {
            Acesso acesso = acessoService.login(username, password);

            if (acesso == null) {
                JOptionPane.showMessageDialog(this,
                        "Usuário ou senha incorretos!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                return;
            }

            // Verifica se o role corresponde ao tipo selecionado
            boolean isAdmin = rbAdmin.isSelected();
            String roleEsperado = isAdmin ? "ADMIN" : "FRENTISTA";

            if (acesso.getRole() != null && !acesso.getRole().equals(roleEsperado)) {
                JOptionPane.showMessageDialog(this,
                        "Este usuário não tem permissão de " + roleEsperado + "!",
                        "Acesso Negado",
                        JOptionPane.WARNING_MESSAGE);
                txtPassword.setText("");
                return;
            }

            // Login bem-sucedido
            String role = acesso.getRole() != null ? acesso.getRole() : "FRENTISTA";
            sessionManager.login(username, String.valueOf(acesso.getId()), role);

            JOptionPane.showMessageDialog(this,
                    "Login realizado com sucesso!\n" +
                            "Bem-vindo(a), " + (acesso.getNomeCompleto() != null ? acesso.getNomeCompleto() : username) + "!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Abre a tela principal
            MainApp.showMainApp();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Usuário ou senha incorretos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    private JRadioButton createStyledRadioButton(String text, boolean selected) {
        JRadioButton rb = new JRadioButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected()) {
                    g2d.setColor(PRIMARY_COLOR.brighter());
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }

                super.paintComponent(g);
            }
        };
        rb.setForeground(TEXT_COLOR);
        rb.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rb.setOpaque(false);
        rb.setSelected(selected);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rb.setFocusPainted(false);
        return rb;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));

                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (hasFocus()) {
                    g2d.setColor(PRIMARY_COLOR);
                    g2d.setStroke(new BasicStroke(2.5f));
                } else {
                    g2d.setColor(new Color(100, 100, 100)); // Cinza para borda normal
                    g2d.setStroke(new BasicStroke(1.5f));
                }

                g2d.draw(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, 12, 12));
                g2d.dispose();
            }
        };

        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.repaint();
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.repaint();
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));

                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (hasFocus()) {
                    g2d.setColor(PRIMARY_COLOR);
                    g2d.setStroke(new BasicStroke(2.5f));
                } else {
                    g2d.setColor(new Color(100, 100, 100)); // Cinza para borda normal
                    g2d.setStroke(new BasicStroke(1.5f));
                }

                g2d.draw(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, 12, 12));
                g2d.dispose();
            }
        };

        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.repaint();
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.repaint();
            }
        });

        return field;
    }

    private JButton createModernButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            private boolean hovered = false;
            private int shadowSize = 0; // Removendo sombra para minimalismo

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Sombra (Removida)

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                // Cor Sólida (Roxo Elétrico)
                if (hovered) {
                    g2d.setColor(BUTTON_HOVER_COLOR);
                } else {
                    g2d.setColor(startColor);
                }

                g2d.fill(new RoundRectangle2D.Double(shadowSize, shadowSize,
                        w - shadowSize * 2, h - shadowSize * 2, 15, 15));

                // Borda brilhante (Removida)

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.repaint();
            }
            public void mouseExited(MouseEvent e) {
                button.repaint();
            }
        });

        return button;
    }
}
