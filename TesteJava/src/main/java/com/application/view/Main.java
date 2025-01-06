package com.application.view;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.application.view.cliente.ConsultaCliente;
import com.application.view.produto.ConsultaProduto;
import com.application.view.venda.ConsultaVenda;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private JMenuItem mnConsultaCliente;
	private JMenuItem mnConsultaProduto;
	private JMenuItem mnConsultaVenda;
	
	private ConsultaCliente consultaCliente;
	private ConsultaProduto consultaProduto;
	private ConsultaVenda consultaVenda;

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
		
		mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		mnConsultaCliente = new JMenuItem("> Clientes");
		mnConsultaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (consultaCliente == null || !consultaCliente.isDisplayable()) {
					consultaCliente = new ConsultaCliente(Main.this);
					consultaCliente.setVisible(true);
	            } else {
	            	consultaCliente.toFront();
	            }
			}
		});
		mnMenu.add(mnConsultaCliente);
		
		mnConsultaProduto = new JMenuItem("> Produtos");
		mnConsultaProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (consultaProduto == null || !consultaProduto.isDisplayable()) {
					consultaProduto = new ConsultaProduto(Main.this);
					consultaProduto.setVisible(true);
	            } else {
	            	consultaProduto.toFront();
	            }
			}
		});
		mnMenu.add(mnConsultaProduto);
		
		mnConsultaVenda = new JMenuItem("> Vendas");
		mnConsultaVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (consultaVenda == null || !consultaVenda.isDisplayable()) {
					consultaVenda = new ConsultaVenda(Main.this);
					consultaVenda.setVisible(true);
	            } else {
	            	consultaVenda.toFront();
	            }
			}
		});
		mnMenu.add(mnConsultaVenda);
	}
}
