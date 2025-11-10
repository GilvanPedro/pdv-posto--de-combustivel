package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    // Cores para a nova interface (mantidas para consistência)
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color TEXT_COLOR = Color.WHITE;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // Inicia o sistema sempre pela tela de login
            new LoginView().setVisible(true);
        });
    }

    public static void showMainApp() {
        EventQueue.invokeLater(() -> {
            // Chama a nova MainFrame que contém o JTabbedPane
            new MainFrame().setVisible(true);
        });
    }
    
    // Método auxiliar para verificar acesso de administrador (mantido)
    public static boolean checkAdminAccess(Component parent) {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.isAdmin()) {
            JOptionPane.showMessageDialog(parent,
                    "Acesso negado. Esta funcionalidade é exclusiva para Administradores.",
                    "Acesso Negado",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Método auxiliar para criar botões estilizados (mantido para compatibilidade)
    public static JButton createModernButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito Hover
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
