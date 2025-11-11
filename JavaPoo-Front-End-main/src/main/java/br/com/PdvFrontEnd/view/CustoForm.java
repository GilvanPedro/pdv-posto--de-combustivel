package br.com.PdvFrontEnd.view;

import br.com.PdvFrontEnd.model.Custo;
import br.com.PdvFrontEnd.service.CustoService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustoForm extends JPanel {
    // Cores para a nova interface
    private static final Color PRIMARY_COLOR = new Color(143, 125, 240);
    private static final Color SECONDARY_COLOR = new Color(75, 75, 75);
    private static final Color ACCENT_COLOR = new Color(100, 80, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(75, 75, 75);
    private static final Color BUTTON_HOVER_COLOR = new Color(169, 156, 199);
    private static final Color BG2 = new Color(255, 255, 255);

    private JTextField txtImposto;
    private JTextField txtFrete;
    private JTextField txtCustoVariavel;
    private JTextField txtCustoFixo;
    private JTextField txtMargemLucro;
    private JTextField txtDataProcessamento;
    private CustoService custoService;
    private Custo custoEmEdicao;
    private Runnable onSaveCallback;
    private Runnable onCancelCallback;

    public CustoForm(CustoService service, Runnable onSave, Runnable onCancel) {
        this(service, onSave, onCancel, null);
    }

    public CustoForm(CustoService service, Runnable onSave, Runnable onCancel, Custo custo) {
        this.custoService = service;
        this.onSaveCallback = onSave;
        this.onCancelCallback = onCancel;
        this.custoEmEdicao = custo;

        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Título do Formulário
        JLabel header = new JLabel(custo == null ? "CADASTRO DE CUSTO" : "EDITAR CUSTO", SwingConstants.CENTER);
        header.setFont(new Font("Arial Black", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        mainPanel.setBackground(BG2);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel, BorderLayout.CENTER);

        // Campos do Formulário
        mainPanel.add(createStyledLabel("Imposto:"));
        txtImposto = createStyledTextField();
        mainPanel.add(txtImposto);

        mainPanel.add(createStyledLabel("Frete:"));
        txtFrete = createStyledTextField();
        mainPanel.add(txtFrete);

        mainPanel.add(createStyledLabel("Custo Variável:"));
        txtCustoVariavel = createStyledTextField();
        mainPanel.add(txtCustoVariavel);

        mainPanel.add(createStyledLabel("Custo Fixo:"));
        txtCustoFixo = createStyledTextField();
        mainPanel.add(txtCustoFixo);

        mainPanel.add(createStyledLabel("Margem de Lucro:"));
        txtMargemLucro = createStyledTextField();
        mainPanel.add(txtMargemLucro);

        mainPanel.add(createStyledLabel("Data de Processamento (dd/MM/yyyy):"));
        txtDataProcessamento = createStyledTextField();
        mainPanel.add(txtDataProcessamento);

        // Se estiver editando, preencher os campos
        if (custoEmEdicao != null) {
            txtImposto.setText(String.valueOf(custoEmEdicao.getImposto()));
            txtFrete.setText(String.valueOf(custoEmEdicao.getFrete()));
            txtCustoVariavel.setText(String.valueOf(custoEmEdicao.getCustoVariavel()));
            txtCustoFixo.setText(String.valueOf(custoEmEdicao.getCustoFixo()));
            txtMargemLucro.setText(String.valueOf(custoEmEdicao.getMargemLucro()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            txtDataProcessamento.setText(dateFormat.format(custoEmEdicao.getDataProcessamento()));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        add(buttonPanel, BorderLayout.SOUTH);

        JButton btnSalvar = createStyledButton("Salvar");
        btnSalvar.addActionListener(this::salvarCusto);
        buttonPanel.add(btnSalvar);

        JButton btnCancelar = createStyledButton("Cancelar");
        btnCancelar.addActionListener(e -> onCancelCallback.run());
        buttonPanel.add(btnCancelar);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(SECONDARY_COLOR); // Cor do texto para BG2 (branco)
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

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY_COLOR);
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
                btn.setBackground(PRIMARY_COLOR);
            }
        });
        return btn;
    }

    private void salvarCusto(ActionEvent e) {
        try {
            double imposto = Double.parseDouble(txtImposto.getText());
            double frete = Double.parseDouble(txtFrete.getText());
            double custoVariavel = Double.parseDouble(txtCustoVariavel.getText());
            double custoFixo = Double.parseDouble(txtCustoFixo.getText());
            double margemLucro = Double.parseDouble(txtMargemLucro.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataProcessamento = dateFormat.parse(txtDataProcessamento.getText());

            Custo novoCusto = new Custo(
                    null, // ID será definido pelo backend
                    imposto,
                    frete,
                    custoVariavel,
                    custoFixo,
                    margemLucro,
                    dataProcessamento
            );

            if (custoEmEdicao != null) {
                // Modo edição
                novoCusto.setId(custoEmEdicao.getId());
                custoService.updateCusto(custoEmEdicao.getId(), novoCusto);
                JOptionPane.showMessageDialog(this, "Custo atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Modo criação
                custoService.addCusto(novoCusto);
                JOptionPane.showMessageDialog(this, "Custo adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            onSaveCallback.run();

        } catch (NumberFormatException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar custo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
