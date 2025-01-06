package com.application.view.cliente;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.util.EnumSet;

import com.application.controller.ClienteController;
import com.application.enums.ModoOperacao;
import com.application.model.Cliente;
import com.application.util.NumberUtils;

public class CadastroCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JLabel lblLimiteCompra;
	private JLabel lblDiaFechamentoFatura;
	private JTextField txtCodigo;
	private JTextField txtLimiteCompra;
	private JTextField txtDiaFechamentoFatura;

	private ICadastroClienteListener _listener;
	private Cliente _cliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroCliente frame = new CadastroCliente(null, ModoOperacao.ADICIONAR);
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
	public CadastroCliente() {
		CreateFrame(null, null);
	};
	
	public CadastroCliente(JFrame parentFrame, ModoOperacao modo) {
		CreateFrame(parentFrame, modo);
	}

	public CadastroCliente(JFrame parentFrame, ModoOperacao modo, Cliente cliente) {
		CreateFrame(parentFrame, modo);

		_cliente = cliente;

		txtCodigo.setText(String.valueOf(_cliente.getCodigo()));
		txtNome.setText(_cliente.getNome());
		txtLimiteCompra.setText(String.valueOf(_cliente.getLimiteCompra()));
		txtDiaFechamentoFatura.setText(String.valueOf(_cliente.getDiaFechamentoFatura()));
	}

	public void CreateFrame(JFrame parentFrame, ModoOperacao modo) {
		setResizable(false);
		setTitle("Cadastro Cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 569, 223);
		setLocationRelativeTo(parentFrame);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtNome = new JTextField();
		txtNome.setBounds(199, 46, 307, 20);
		txtNome.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(125, 49, 64, 14);
		contentPane.add(lblNome);

		lblCodigo = new JLabel("Código");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(119, 24, 70, 14);
		contentPane.add(lblCodigo);

		lblLimiteCompra = new JLabel("Limite para Compra (R$)");
		lblLimiteCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLimiteCompra.setBounds(36, 74, 153, 14);
		contentPane.add(lblLimiteCompra);

		lblDiaFechamentoFatura = new JLabel("Dia de Fechamento da Fatura");
		lblDiaFechamentoFatura.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaFechamentoFatura.setBounds(10, 99, 179, 14);
		contentPane.add(lblDiaFechamentoFatura);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(199, 21, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtLimiteCompra = new JTextField();
		txtLimiteCompra.setBounds(199, 71, 86, 20);
		txtLimiteCompra.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		contentPane.add(txtLimiteCompra);
		txtLimiteCompra.setColumns(10);

		txtDiaFechamentoFatura = new JTextField();
		txtDiaFechamentoFatura.setBounds(199, 96, 86, 20);
		txtDiaFechamentoFatura.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		contentPane.add(txtDiaFechamentoFatura);
		txtDiaFechamentoFatura.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setVisible(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (modo == ModoOperacao.ADICIONAR) {
						salvar();
					} else if (modo == ModoOperacao.ATUALIZAR) {
						atualizar();
					}

					if (_listener != null) {
						_listener.onClienteAtualizado();
					}
					
					dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(CadastroCliente.this, "Erro ao salvar o cliente! \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSalvar.setBounds(417, 139, 89, 23);
		contentPane.add(btnSalvar);
	}

	private void salvar() {
		String nome = txtNome.getText();
		double limiteCompra = NumberUtils.parseDoubleOrDefault(txtLimiteCompra.getText(), 0);
		int diaFechamentoFatura = NumberUtils.parseIntOrDefault(txtDiaFechamentoFatura.getText(), 0);

		ClienteController clienteController = new ClienteController(new Cliente(nome, limiteCompra, diaFechamentoFatura));
		clienteController.salvar();

		JOptionPane.showMessageDialog(CadastroCliente.this, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	private void atualizar() {
		_cliente.setNome(txtNome.getText());
		_cliente.setLimiteCompra(NumberUtils.parseDoubleOrDefault(txtLimiteCompra.getText(), 0));
		_cliente.setDiaFechamentoFatura(NumberUtils.parseIntOrDefault(txtDiaFechamentoFatura.getText(), 0));

		ClienteController clienteController = new ClienteController(_cliente);
		clienteController.atualizar();

		JOptionPane.showMessageDialog(CadastroCliente.this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	public void setCadastroClienteListener(ICadastroClienteListener listener) {
		this._listener = listener;
	}
}
