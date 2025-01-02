package com.application.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

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
		setBounds(100, 100, 593, 243);
		setLocationRelativeTo(parentFrame);		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtNome = new JTextField();
		txtNome.setBounds(199, 33, 307, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(125, 36, 64, 14);
		contentPane.add(lblNome);
		
		lblCodigo = new JLabel("Código");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(119, 11, 70, 14);
		contentPane.add(lblCodigo);
		
		lblLimiteCompra = new JLabel("Limite para Compra (R$)");
		lblLimiteCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLimiteCompra.setBounds(36, 61, 153, 14);
		contentPane.add(lblLimiteCompra);
		
		lblDiaFechamentoFatura = new JLabel("Dia de Fechamento da Fatura");
		lblDiaFechamentoFatura.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaFechamentoFatura.setBounds(10, 86, 179, 14);
		contentPane.add(lblDiaFechamentoFatura);
		
		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(199, 8, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtLimiteCompra = new JTextField();
		txtLimiteCompra.setBounds(199, 58, 86, 20);
		contentPane.add(txtLimiteCompra);
		txtLimiteCompra.setColumns(10);
		
		txtDiaFechamentoFatura = new JTextField();
		txtDiaFechamentoFatura.setBounds(199, 83, 86, 20);
		contentPane.add(txtDiaFechamentoFatura);
		txtDiaFechamentoFatura.setColumns(10);
		
		JButton alvar = new JButton("Salvar");
		alvar.setBounds(385, 143, 121, 36);
		contentPane.add(alvar);
	}
}
