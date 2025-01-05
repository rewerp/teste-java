package com.application.view.venda;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.util.List;

import com.application.controller.VendaController;
import com.application.model.Venda;

public class ConsultaVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaVendas;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaVenda frame = new ConsultaVenda();
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
		setTitle("Consulta Vendas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
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
		
		JButton btnVisualizar = new JButton("Visualizar");
		panelBottom.add(btnVisualizar);
		
		JButton btnAdicionar = new JButton("Adicionar");
		panelBottom.add(btnAdicionar);
		
		JButton btnAlterar = new JButton("Alterar");
		panelBottom.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
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
			model.addRow(new Object[] { venda.getCodigo(), venda.getCliente().getCodigo(), venda.getCliente().getNome(), venda.getDataVenda().format(formatter), venda.getValorTotal() });
		}
	}

}
