import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaProduto extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private ProdutoRepositorio repositorio;

    public TelaProduto() {
        repositorio = new ProdutoRepositorio();

        setTitle("Cadastro de Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(new Object[]{"Código", "Descrição", "Preço"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar);
        botoes.add(btnAtualizar);
        botoes.add(btnRemover);

        add(botoes, BorderLayout.SOUTH);

        carregarTabela();

        // Eventos dos botões
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnAtualizar.addActionListener(e -> atualizarProduto());
        btnRemover.addActionListener(e -> removerProduto());
    }

    private void carregarTabela() {
        modelo.setRowCount(0); // Limpa a tabela
        for (Produto p : repositorio.listar()) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getDescricao(), p.getPreco()});
        }
    }

    private void adicionarProduto() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Código:"));
            String descricao = JOptionPane.showInputDialog("Descrição:");
            double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço:"));

            Produto p = new Produto(codigo, descricao, preco);
            repositorio.salvar(p);
            carregarTabela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro nos valores informados!");
        }
    }

    private void atualizarProduto() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            int codigo = (int) tabela.getValueAt(linha, 0);

            String descricao = JOptionPane.showInputDialog("Nova descrição:");
            double preco = Double.parseDouble(JOptionPane.showInputDialog("Novo preço:"));

            Produto p = new Produto(codigo, descricao, preco);
            repositorio.atualizar(p);
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar!");
        }
    }

    private void removerProduto() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            int codigo = (int) tabela.getValueAt(linha, 0);
            Produto p = new Produto();
            p.setCodigo(codigo);
            repositorio.remover(p);
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaProduto().setVisible(true));
    }
}
