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
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color SUCCESS_COLOR = new Color(222, 187, 109);
    private static final Color PURPLE_COLOR = new Color(89, 129, 182);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);

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

        // Painel de fundo com gradiente
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, SECONDARY_COLOR, 0, h, SECONDARY_COLOR.darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        // Card principal com sombra
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth()-10, getHeight()-5, 25, 25);

                // Card branco
                g2d.setColor(new Color(52, 73, 94));
                g2d.fillRoundRect(0, 0, getWidth()-10, getHeight()-10, 25, 25);

                // Borda brilhante
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-12, getHeight()-12, 25, 25);

                g2d.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Espaçamento superior
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Título com efeito
        JLabel lblTitle = new JLabel("Sistema PDV") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Sombra do texto
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                g2d.drawString(getText(), x + 2, getHeight() / 2 + fm.getAscent() / 2 + 2);

                super.paintComponent(g);
            }
        };
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Acesso ao Sistema");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(189, 195, 199));
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

        // Botão Login com gradiente
        JButton btnLogin = createModernButton("ENTRAR", PRIMARY_COLOR, BUTTON_HOVER_COLOR);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(340, 50));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.addActionListener(e -> handleLogin());
        cardPanel.add(btnLogin);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Botão Preencher Informações
        JButton btnPreencherInfo = createModernButton("Preencher Informações", SUCCESS_COLOR, SUCCESS_COLOR.darker());
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
                    g2d.setColor(new Color(52, 152, 219, 30));
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
            private boolean focused = false;

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

                if (focused) {
                    g2d.setColor(SUCCESS_COLOR);
                    g2d.setStroke(new BasicStroke(2.5f));
                } else {
                    g2d.setColor(PRIMARY_COLOR);
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
            private boolean focused = false;

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

                if (focused) {
                    g2d.setColor(SUCCESS_COLOR);
                    g2d.setStroke(new BasicStroke(2.5f));
                } else {
                    g2d.setColor(PRIMARY_COLOR);
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
            private int shadowSize = 5;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 30));
                for (int i = 0; i < shadowSize; i++) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
                    g2d.fill(new RoundRectangle2D.Double(i, i, w - i * 2, h - i * 2, 15, 15));
                }

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                // Gradiente
                GradientPaint gradient;
                if (hovered) {
                    Color hoverStart = endColor.darker();
                    Color hoverEnd = endColor;
                    gradient = new GradientPaint(0, 0, hoverStart, 0, h, hoverEnd);
                } else {
                    gradient = new GradientPaint(0, 0, startColor, 0, h, endColor);
                }

                g2d.setPaint(gradient);
                g2d.fill(new RoundRectangle2D.Double(shadowSize, shadowSize,
                        w - shadowSize * 2, h - shadowSize * 2, 15, 15));

                // Borda brilhante
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new RoundRectangle2D.Double(shadowSize + 1, shadowSize + 1,
                        w - shadowSize * 2 - 2, h - shadowSize * 2 - 2, 15, 15));

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

