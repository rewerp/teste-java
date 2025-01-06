package com.application.view.venda;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.util.List;

import com.application.controller.VendaController;
import com.application.enums.ModoOperacao;
import com.application.model.Venda;

public class ConsultaVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaVendas;
	
	private VendaController vendaController;
	private CadastroVenda vendaCadastro;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaVenda frame = new ConsultaVenda(null);
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
	public ConsultaVenda() {
	}
	
	public ConsultaVenda(JFrame parentFrame) {
		setTitle("Consulta Vendas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(parentFrame);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTop.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		JButton btnAtualizarLista = new JButton("Atualizar Lista");
		btnAtualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarVendas();
			}
		});
		panelTop.add(btnAtualizarLista);
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaVendas.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ConsultaVenda.this, "Nenhum cliente selecionado para visualização!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int codigo = (int)tabelaVendas.getValueAt(selectedRow, 0);
				Venda vendaVisualizar = new VendaController().buscarPorCodigo(codigo);
				
				if (vendaCadastro == null || !vendaCadastro.isDisplayable()) {
					vendaCadastro = new CadastroVenda(ConsultaVenda.this, ModoOperacao.VISUALIZAR, vendaVisualizar);
					vendaCadastro.setVisible(true);
				} else {
					vendaCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnVisualizar);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (vendaCadastro == null || !vendaCadastro.isDisplayable()) {
					vendaCadastro = new CadastroVenda(ConsultaVenda.this, ModoOperacao.ADICIONAR);

					vendaCadastro.setCadastroVendaListener (new ICadastroVendaListener() {
						@Override
						public void onClienteAtualizado() {
							carregarVendas();
						}
					});

					vendaCadastro.setVisible(true);
				} else {
					vendaCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnAdicionar);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaVendas.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ConsultaVenda.this, "Nenhum cliente selecionado para visualização!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int codigo = (int)tabelaVendas.getValueAt(selectedRow, 0);
				Venda vendaAtualizar = new VendaController().buscarPorCodigo(codigo);
				
				if (vendaCadastro == null || !vendaCadastro.isDisplayable()) {
					vendaCadastro = new CadastroVenda(ConsultaVenda.this, ModoOperacao.ATUALIZAR, vendaAtualizar);
					
					vendaCadastro.setCadastroVendaListener (new ICadastroVendaListener() {
						@Override
						public void onClienteAtualizado() {
							carregarVendas();
						}
					});
					
					vendaCadastro.setVisible(true);
				} else {
					vendaCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaVendas.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ConsultaVenda.this, "Nenhuma venda selecionada para exclusão!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(ConsultaVenda.this, "Deseja excluir a venda selecionada?", "Confirmação", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					try {
						int codigo = (int)tabelaVendas.getValueAt(selectedRow, 0);
						vendaController = new VendaController();
						vendaController.excluir(codigo);

						JOptionPane.showMessageDialog(ConsultaVenda.this, "Venda excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

						carregarVendas();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ConsultaVenda.this, "Erro ao excluir a venda! \n " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panelBottom.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tabelaVendas = new JTable();
		scrollPane.setViewportView(tabelaVendas);
		String[] colunas = { "Código", "Código Cliente", "Nome Cliente", "Data Venda", "Valor Total (R$)" };
		DefaultTableModel model = new DefaultTableModel(null, colunas);
		tabelaVendas.setModel(model);
		
		carregarVendas();
	}
	
	private void carregarVendas() {
		VendaController vendaController = new VendaController();
		List<Venda> vendas = vendaController.buscarTodos();
		
		DefaultTableModel model = (DefaultTableModel) tabelaVendas.getModel();
		model.setRowCount(0);

		for (Venda venda : vendas) {
			model.addRow(new Object[] { venda.getCodigo(), venda.getCliente().getCodigo(), 
					venda.getCliente().getNome(), venda.getDataVenda().format(formatter), venda.getValorTotal() });
		}
	}

}
