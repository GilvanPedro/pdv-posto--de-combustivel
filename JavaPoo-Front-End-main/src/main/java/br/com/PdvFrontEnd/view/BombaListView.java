package br.com.PdvFrontEnd.view;

import javax.swing.*;
import java.awt.*;

public class BombaListView extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240); // Roxo #8F7DF0
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75); // Cinza escuro
    private static final Color ACCENT_COLOR = new Color(100, 80, 180); // Roxo mais escuro para abas ativas
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACTIVE_COLOR = new Color(46, 204, 113); // Verde
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199); // Roxo mais claro para hover

    public BombaListView() {
        initComponents();
    }

    private void initComponents() {
        // Removidas configurações de JFrame (título, tamanho, fechamento, localização)
        setBackground(SECONDARY_COLOR);
        setLayout(new BorderLayout(10, 10));

        // Título
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));

        JLabel lblTitle = new JLabel("GERENCIAMENTO DE BOMBAS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // Painel central com as bombas
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 30, 30));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Criar painel para cada bomba
        for (int i = 1; i <= 3; i++) {
            mainPanel.add(createBombaCard(i));
        }

        add(mainPanel, BorderLayout.CENTER);

        // Painel inferior com legenda
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.add(createStatusLabel("● Ativa", ACTIVE_COLOR));
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createBombaCard(int numeroBomba) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(SECONDARY_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Título da bomba
        JLabel lblNumero = new JLabel("BOMBA " + numeroBomba);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNumero.setForeground(TEXT_COLOR);
        lblNumero.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblNumero);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Status
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel statusIndicator = new JPanel();
        statusIndicator.setBackground(ACTIVE_COLOR);
        statusIndicator.setPreferredSize(new Dimension(40, 40));
        statusPanel.add(statusIndicator);

        card.add(statusPanel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblStatus = new JLabel("ATIVA");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblStatus.setForeground(ACTIVE_COLOR);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblStatus);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        // Botão Abastecer
        JButton btnAbastecer = createStyledButton("ABASTECER");
        btnAbastecer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAbastecer.setMaximumSize(new Dimension(180, 45));
        btnAbastecer.setBackground(ACTIVE_COLOR);
        btnAbastecer.addActionListener(e -> new BombaManagerView(numeroBomba).setVisible(true));
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
        button.setBackground(PRIMARY_COLOR);;
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Color bg = (Color) button.getClientProperty("originalColor");
                if (bg != null) {
                    button.setBackground(bg);
                } else {
                    button.setBackground(PRIMARY_COLOR);
                }
            }
        });

        button.putClientProperty("originalColor", PRIMARY_COLOR);
        return button;
    }
}

