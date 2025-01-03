package com.application.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.application.controller.ClienteController;
import com.application.model.Cliente;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class ConsultaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaClientes;

	private CadastroCliente clienteCadastro;
	private ClienteController clienteController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaCliente frame = new ConsultaCliente(null);
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
	public ConsultaCliente(JFrame parentFrame) {
		setAlwaysOnTop(true);
		setTitle("Consulta Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 871, 561);
		setLocationRelativeTo(parentFrame);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		tabelaClientes = new JTable();
		scrollPane.setViewportView(tabelaClientes);
		String[] colunas = { "Código", "Nome", "Limite de Compra (R$)", "Dia Fechamento Fatura" };
		DefaultTableModel model = new DefaultTableModel(null, colunas);
		tabelaClientes.setModel(model);

		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clienteCadastro == null || !clienteCadastro.isDisplayable()) {
					clienteCadastro = new CadastroCliente(ConsultaCliente.this);

					clienteCadastro.setCadastroClienteListener(new ICadastroClienteListener() {
						@Override
						public void onClienteAtualizado() {
							carregarClientes();
						}
					});

					clienteCadastro.setVisible(true);
				} else {
					clienteCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnAdicionar);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clienteCadastro == null || !clienteCadastro.isDisplayable()) {
					clienteCadastro = new CadastroCliente(ConsultaCliente.this);

					clienteCadastro.setCadastroClienteListener(new ICadastroClienteListener() {
						@Override
						public void onClienteAtualizado() {
							carregarClientes();
						}
					});

					clienteCadastro.setVisible(true);
				} else {
					clienteCadastro.toFront();
				}
			}
		});
		panelBottom.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaClientes.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ConsultaCliente.this, "Nenhum cliente selecionado para exclusão!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(ConsultaCliente.this, "Deseja excluir o cliente selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					try {
						int codigo = (int)tabelaClientes.getValueAt(selectedRow, 0);
						clienteController = new ClienteController();
						clienteController.excluir(codigo);

						JOptionPane.showMessageDialog(ConsultaCliente.this, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

						carregarClientes();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ConsultaCliente.this, "Erro ao excluir cliente! \n " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panelBottom.add(btnExcluir);
		
		JPanel panelTop = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTop.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		JButton btnAtualizarLista = new JButton("Atualizar Lista");
		btnAtualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarClientes();
			}
		});
		panelTop.add(btnAtualizarLista);

		carregarClientes();
	}

	private void carregarClientes() {
		ClienteController clienteController = new ClienteController();
		List<Cliente> clientes = clienteController.buscarTodos();

		DefaultTableModel model = (DefaultTableModel) tabelaClientes.getModel();
		model.setRowCount(0);

		for (Cliente cliente : clientes) {
			model.addRow(new Object[] { cliente.getCodigo(), cliente.getNome(), cliente.getLimiteCompra(), cliente.getDiaFechamentoFatura() });
		}
	}

}
