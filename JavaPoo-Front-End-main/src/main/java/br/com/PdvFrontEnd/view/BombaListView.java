package br.com.PdvFrontEnd.view;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class BombaListView extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240); // Roxo #8F7DF0
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75); // Cinza escuro
    private static final Color ACCENT_COLOR = new Color(100, 80, 180); // Roxo mais escuro para abas ativas
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACTIVE_COLOR = new Color(46, 204, 113); // Verde
    private static final Color INACTIVE_COLOR = new Color(231, 76, 60); // Vermelho
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199); // Roxo mais claro para hover

    public BombaListView() {
        initComponents();
    }

    private void initComponents() {
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

        // Painel central com as bombas - 2 linhas, 3 colunas
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Criar painel para cada bomba (5 bombas: 4 ativas, 1 inativa)
        for (int i = 1; i <= 5; i++) {
            boolean isActive = i <= 4; // Bombas 1 a 4 ativas, Bomba 5 inativa
            mainPanel.add(createBombaCard(i, isActive));
        }

        // Adicionar um painel vazio para preencher a última célula do grid 2x3
        mainPanel.add(new JPanel() {{ setBackground(SECONDARY_COLOR); }});

        add(mainPanel, BorderLayout.CENTER);

        // Painel inferior com legenda
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

        // Título da bomba
        JLabel lblNumero = new JLabel("BOMBA " + numeroBomba);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblNumero.setForeground(TEXT_COLOR);
        lblNumero.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblNumero);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Imagem da Bomba
        JLabel lblImage = new JLabel();
        try {
            InputStream imgStream = getClass().getResourceAsStream("/images/gas_pump_icon.png");

            if (imgStream != null) {
                ImageIcon originalIcon = new ImageIcon(imgStream.readAllBytes());
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(scaledImage));
            } else {
                lblImage.setText("[IMAGEM NÃO ENCONTRADA]");
                lblImage.setForeground(INACTIVE_COLOR);
            }
        } catch (Exception e) {
            lblImage.setText("[ERRO AO CARREGAR IMAGEM]");
            lblImage.setForeground(INACTIVE_COLOR);
            e.printStackTrace();
        }
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblImage);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Status
        String statusText = isActive ? "ATIVA" : "INATIVA";
        Color statusColor = isActive ? ACTIVE_COLOR : INACTIVE_COLOR;

        JLabel lblStatus = new JLabel("● " + statusText);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblStatus.setForeground(statusColor);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblStatus);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botão Abastecer
        JButton btnAbastecer = createStyledButton("ABASTECER");
        btnAbastecer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAbastecer.setMaximumSize(new Dimension(180, 40));
        btnAbastecer.setBackground(isActive ? ACTIVE_COLOR : INACTIVE_COLOR.darker());
        btnAbastecer.setEnabled(isActive); // Desabilita o botão se a bomba estiver inativa

        if (isActive) {
            btnAbastecer.addActionListener(e -> new BombaManagerView(numeroBomba).setVisible(true));
        } else {
            btnAbastecer.addActionListener(e -> JOptionPane.showMessageDialog(card, "Bomba Inativa. Não é possível abastecer.", "Erro", JOptionPane.ERROR_MESSAGE));
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
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_HOVER_COLOR);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    Color bg = (Color) button.getClientProperty("originalColor");
                    if (bg != null) {
                        button.setBackground(bg);
                    } else {
                        button.setBackground(PRIMARY_COLOR);
                    }
                }
            }
        });

        button.putClientProperty("originalColor", button.getBackground());
        return button;
    }
}
