package com.application.view.produto;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.util.List;

import com.application.controller.ProdutoController;
import com.application.enums.ModoOperacao;
import com.application.model.Produto;

public class ConsultaProduto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaProdutos;
	
	private CadastroProduto produtoCadastro;
	private ProdutoController produtoController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaProduto frame = new ConsultaProduto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConsultaProduto() {
		setTitle("Consulta Produtos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 869, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTop.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		JButton btnAtualizarLista = new JButton("Atualizar Lista");
		panelTop.add(btnAtualizarLista);
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (produtoCadastro == null || !produtoCadastro.isDisplayable()) {
					produtoCadastro = new CadastroProduto(ConsultaProduto.this, ModoOperacao.ADICIONAR);

					produtoCadastro.setCadastroProdutoListener (new ICadastroProdutoListener() {
						@Override
						public void onClienteAtualizado() {
							carregarProdutos();
						}
					});

					produtoCadastro.setVisible(true);
				} else {
					produtoCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnAdicionar);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaProdutos.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ConsultaProduto.this, "Nenhum produto selecionado para atualização!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int codigo = (int)tabelaProdutos.getValueAt(selectedRow, 0);
				String descricao = (String)tabelaProdutos.getValueAt(selectedRow, 1);
				double precoUnitario = (double)tabelaProdutos.getValueAt(selectedRow, 2);
				
				Produto produtoAtualizar = new Produto(codigo, descricao, precoUnitario);
				
				if (produtoCadastro == null || !produtoCadastro.isDisplayable()) {
					produtoCadastro = new CadastroProduto(ConsultaProduto.this, ModoOperacao.ATUALIZAR, produtoAtualizar);

					produtoCadastro.setCadastroProdutoListener(new ICadastroProdutoListener() {
						@Override
						public void onClienteAtualizado() {
							carregarProdutos();
						}
					});

					produtoCadastro.setVisible(true);
				} else {
					produtoCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaProdutos.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ConsultaProduto.this, "Nenhum produto selecionado para exclusão!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(ConsultaProduto.this, "Deseja excluir o produto selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					try {
						int codigo = (int)tabelaProdutos.getValueAt(selectedRow, 0);
						produtoController = new ProdutoController();
						produtoController.excluir(codigo);

						JOptionPane.showMessageDialog(ConsultaProduto.this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

						carregarProdutos();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ConsultaProduto.this, "Erro ao excluir produto! \n " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panelBottom.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tabelaProdutos = new JTable();
		scrollPane.setViewportView(tabelaProdutos);
		String[] colunas = { "Código", "Descrição", "Preço Unitário (R$)" };
		DefaultTableModel model = new DefaultTableModel(null, colunas);
		tabelaProdutos.setModel(model);
		
		carregarProdutos();
	}
	
	private void carregarProdutos() {
		ProdutoController produtoController = new ProdutoController();
		List<Produto> produtos = produtoController.buscarTodos();
		
		DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();
		model.setRowCount(0);

		for (Produto produto : produtos) {
			model.addRow(new Object[] { produto.getCodigo(), produto.getDescricao(), produto.getPrecoUnitario() });
		}
	}

}
