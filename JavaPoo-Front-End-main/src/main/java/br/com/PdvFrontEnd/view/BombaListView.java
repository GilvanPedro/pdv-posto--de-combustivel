package br.com.PdvFrontEnd.view;

import javax.swing.*;
import java.awt.*;

public class BombaListView extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240); // Roxo
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75); // Cinza escuro
    private static final Color ACCENT_COLOR = new Color(100, 80, 180); // Roxo mais escuro
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACTIVE_COLOR = new Color(46, 204, 113); // Verde
    private static final Color INACTIVE_COLOR = new Color(231, 76, 60); // Vermelho
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);

    public BombaListView() {
        initComponents();
    }

    private void initComponents() {
        setBackground(SECONDARY_COLOR);
        setLayout(new BorderLayout(10, 10));

        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));

        JLabel lblTitle = new JLabel("GERENCIAMENTO DE BOMBAS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_COLOR);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // Painel principal com as bombas
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        for (int i = 1; i <= 5; i++) {
            boolean isActive = i <= 4; // Bombas 1–4 ativas, 5 inativa
            mainPanel.add(createBombaCard(i, isActive));
        }

        mainPanel.add(new JPanel() {{
            setBackground(SECONDARY_COLOR);
        }});

        add(mainPanel, BorderLayout.CENTER);

        // Rodapé (legenda)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.add(createStatusLabel("● Ativa", ACTIVE_COLOR));
        footerPanel.add(createStatusLabel("● Inativa", INACTIVE_COLOR));
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createBombaCard(int numeroBomba, boolean isActive) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(SECONDARY_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isActive ? ACTIVE_COLOR : INACTIVE_COLOR, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblNumero = new JLabel("BOMBA " + numeroBomba);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblNumero.setForeground(TEXT_COLOR);
        lblNumero.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblNumero);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        BombaIconPanel bombaIcon = new BombaIconPanel(isActive);
        bombaIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        bombaIcon.setPreferredSize(new Dimension(100, 130));
        card.add(bombaIcon);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblStatus = new JLabel("● " + (isActive ? "ATIVA" : "INATIVA"));
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblStatus.setForeground(isActive ? ACTIVE_COLOR : INACTIVE_COLOR);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblStatus);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnAbastecer = createStyledButton("ABASTECER");
        btnAbastecer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAbastecer.setMaximumSize(new Dimension(180, 40));
        btnAbastecer.setBackground(isActive ? ACTIVE_COLOR : INACTIVE_COLOR.darker());
        btnAbastecer.setEnabled(isActive);

        if (isActive) {
            btnAbastecer.addActionListener(e -> new BombaManagerView(numeroBomba).setVisible(true));
        } else {
            btnAbastecer.addActionListener(e ->
                    JOptionPane.showMessageDialog(card, "Bomba Inativa. Não é possível abastecer.",
                            "Erro", JOptionPane.ERROR_MESSAGE));
        }

        card.add(btnAbastecer);
        return card;
    }

    private JLabel createStatusLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled())
                    button.setBackground((Color) button.getClientProperty("originalColor"));
            }
        });

        button.putClientProperty("originalColor", button.getBackground());
        return button;
    }

    /**
     * 🔧 Painel que desenha uma bomba de combustível simples usando Graphics2D.
     */
    private static class BombaIconPanel extends JPanel {
        private final boolean active;

        public BombaIconPanel(boolean active) {
            this.active = active;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Corpo da bomba
            g2d.setColor(new Color(90, 90, 90));
            g2d.fillRoundRect(w / 4, h / 5, w / 2, (int) (h * 0.6), 20, 20);

            // Tela
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(w / 3, h / 4, w / 3, h / 8);

            // Mangueira
            g2d.setStroke(new BasicStroke(4f));
            g2d.setColor(new Color(40, 40, 40));
            g2d.drawArc(w / 2, h / 3, w / 2, h / 2, 180, 180);

            // Bico da bomba
            g2d.setColor(active ? new Color(46, 204, 113) : new Color(231, 76, 60));
            g2d.fillRect((int) (w * 0.75), (int) (h * 0.75), 10, 10);

            // Base
            g2d.setColor(new Color(50, 50, 50));
            g2d.fillRect(w / 4 - 5, (int) (h * 0.8), w / 2 + 10, 10);

            g2d.dispose();
        }
    }
}
