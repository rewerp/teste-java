package com.application.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.application.controller.ClienteController;
import com.application.dao.ClienteDAO;
import com.application.model.Cliente;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JTextPane;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class CadastroCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JLabel lblCodigo;
	private JLabel lblLimiteCompra;
	private JLabel lblDiaFechamentoFatura;
	private JTextField txtCodigo;
	private JTextField txtLimiteCompra;
	private JTextField txtDiaFechamentoFatura;
	
	private ICadastroClienteListener listener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroCliente frame = new CadastroCliente(null);
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
	public CadastroCliente(JFrame parentFrame) {
		setAlwaysOnTop(true);
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
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNome = new JLabel("Nome");
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
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(199, 21, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtLimiteCompra = new JTextField();
		txtLimiteCompra.setBounds(199, 71, 86, 20);
		contentPane.add(txtLimiteCompra);
		txtLimiteCompra.setColumns(10);

		txtDiaFechamentoFatura = new JTextField();
		txtDiaFechamentoFatura.setBounds(199, 96, 86, 20);
		contentPane.add(txtDiaFechamentoFatura);
		txtDiaFechamentoFatura.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = txtNome.getText();
					double limiteCompra = Double.parseDouble(txtLimiteCompra.getText());
					int diaFechamentoFatura = Integer.parseInt(txtDiaFechamentoFatura.getText());

					ClienteController clienteController = new ClienteController(new Cliente(nome, limiteCompra, diaFechamentoFatura));
					clienteController.salvar();

					JOptionPane.showMessageDialog(CadastroCliente.this, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					
					txtNome.setText("");
					txtLimiteCompra.setText("");
					txtDiaFechamentoFatura.setText("");
					
					if (listener != null) {
			            listener.onClienteAtualizado();
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
	
	public void setCadastroClienteListener(ICadastroClienteListener listener) {
	    this.listener = listener;
	}
}
