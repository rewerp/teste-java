package com.application.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnCadastro;
	private JMenuItem mnCadastroCliente;
	private JMenuItem mnCadastroProduto;
	private JMenuItem mnCadastroVenda;
	private JMenu mnConsulta;
	private JMenuItem mnConsultaCliente;
	private JMenuItem mnConsultaProduto;
	private JMenuItem mnConsultaVenda;
	
	private CadastroCliente clienteCadastro;
	private ConsultaCliente consultaCadastro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setTitle("SISTEMA DE VENDAS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 849, 553);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) contentPane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		menuBar = new JMenuBar();
		contentPane.add(menuBar);
		
		mnCadastro = new JMenu("Cadastros");
		menuBar.add(mnCadastro);
		
		mnCadastroCliente = new JMenuItem("> Clientes");
		mnCadastroCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            if (clienteCadastro == null || !clienteCadastro.isDisplayable()) {
	            	clienteCadastro = new CadastroCliente(Main.this);
	                clienteCadastro.setVisible(true);
	            } else {
	                clienteCadastro.toFront();
	            }				
			}
		});
		mnCadastro.add(mnCadastroCliente);
		
		mnCadastroProduto = new JMenuItem("> Produtos");
		mnCadastro.add(mnCadastroProduto);
		
		mnCadastroVenda = new JMenuItem("> Vendas");
		mnCadastro.add(mnCadastroVenda);
		
		mnConsulta = new JMenu("Consultas");
		menuBar.add(mnConsulta);
		
		mnConsultaCliente = new JMenuItem("> Clientes");
		mnConsultaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (consultaCadastro == null || !consultaCadastro.isDisplayable()) {
					consultaCadastro = new ConsultaCliente(Main.this);
					consultaCadastro.setVisible(true);
	            } else {
	            	consultaCadastro.toFront();
	            }
			}
		});
		mnConsulta.add(mnConsultaCliente);
		
		mnConsultaProduto = new JMenuItem("> Produtos");
		mnConsulta.add(mnConsultaProduto);
		
		mnConsultaVenda = new JMenuItem("> Vendas");
		mnConsulta.add(mnConsultaVenda);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
