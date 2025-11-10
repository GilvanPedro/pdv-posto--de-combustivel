package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.service.*;
import br.com.PdvFrontEnd.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color TEXT_COLOR = Color.WHITE;


    private JTabbedPane tabbedPane;
    private SessionManager sessionManager;
    private boolean isAdmin;

    public MainFrame() {
        this.sessionManager = SessionManager.getInstance();
        this.isAdmin = sessionManager.isAdmin();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gerenciamento PDV - " + (isAdmin ? "ADMINISTRADOR" : "FRENTISTA"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 770); // Aumentar o tamanho para acomodar o conteúdo das abas
        setLocationRelativeTo(null);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // 1. Painel Superior (Header)
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 2. Painel de Abas (JTabbedPane)
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(SECONDARY_COLOR);
        tabbedPane.setForeground(TEXT_COLOR);
        tabbedPane.setOpaque(true);

        addTabs();
        
        // Para centralizar as abas, vamos usar um JPanel com FlowLayout.CENTER
        JPanel tabWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tabWrapper.setBackground(SECONDARY_COLOR.darker());
        tabWrapper.add(tabbedPane);
        
        mainPanel.add(tabWrapper, BorderLayout.CENTER);

        // 3. Painel Inferior (Footer)
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(SECONDARY_COLOR.darker());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Painel para o nome do usuário e botão Sair
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setOpaque(false);
        // Nome do usuário
        JLabel lblUser = new JLabel("Usuário: " + sessionManager.getCurrentUsername() + " (" + sessionManager.getUserRole() + ")");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblUser.setForeground(TEXT_COLOR);

        // Botão Sair/Logout
        JButton btnLogout = createModernButton("Sair", new Color(231, 76, 60), new Color(192, 57, 43));
        btnLogout.setPreferredSize(new Dimension(100, 35));
        btnLogout.addActionListener(e -> {
            sessionManager.logout();
            dispose();
            new LoginView().setVisible(true);
        });

        userPanel.add(lblUser, BorderLayout.WEST);
        userPanel.add(btnLogout, BorderLayout.EAST);
        
        topPanel.add(userPanel, BorderLayout.NORTH);

        // Painel para as abas (JTabbedPane) - A centralização das abas é feita no JTabbedPane em initComponents
        // Aqui, apenas retorno o painel superior. O JTabbedPane é adicionado em initComponents.
        
        return topPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(SECONDARY_COLOR.darker());
        JLabel footerLabel = new JLabel("PDV Posto de Combustível - Gilvan Pedro");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(TEXT_COLOR);
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    private void addTabs() {
        if (isAdmin) {
            // Abas adicionais apenas para o Administrador
            tabbedPane.addTab("Gerenciar Pessoas", new PessoaList(new PessoaService()));
            tabbedPane.addTab("Gerenciar Preços", new PrecoList(new PrecoService()));
            tabbedPane.addTab("Gerenciar Produtos", new ProdutoList(new ProdutoService()));
            tabbedPane.addTab("Gerenciar Custos", new CustoList(new CustoService()));
            tabbedPane.addTab("Gerenciar Estoques", new EstoqueList(new EstoqueService()));
            tabbedPane.addTab("Gerenciar Acessos", new AcessoList(new AcessoService()));
            tabbedPane.addTab("Gerenciar Contatos", new ContatoList(new ContatoService()));
        }

        tabbedPane.addTab("Abastecimento", new BombaListView());

    }

    // Método auxiliar para criar botões estilizados (copiado de MainApp)
    private JButton createModernButton(String text, Color bgColor, Color hoverColor) {
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
