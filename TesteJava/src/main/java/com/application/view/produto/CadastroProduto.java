package com.application.view.produto;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.application.controller.ProdutoController;
import com.application.enums.ModoOperacao;
import com.application.model.Produto;

public class CadastroProduto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCodigo;
	private JLabel lblDescricao;
	private JLabel lblPrecoUnitario;
	private JTextField txtCodigo;
	private JTextField txtDescricao;
	private JTextField txtPrecoUnitario;

	private ICadastroProdutoListener _listener;
	private Produto _produto;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroProduto frame = new CadastroProduto(null, ModoOperacao.ADICIONAR);
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
	public CadastroProduto() {
	};
	
	public CadastroProduto(JFrame parentFrame, ModoOperacao modo) {
		CreateFrame(parentFrame, modo);
	}

	public CadastroProduto(JFrame parentFrame, ModoOperacao modo, Produto produto) {
		CreateFrame(parentFrame, modo);

		_produto = produto;

		txtCodigo.setText(String.valueOf(_produto.getCodigo()));
		txtDescricao.setText(_produto.getDescricao());
		txtPrecoUnitario.setText(String.valueOf(_produto.getPrecoUnitario()));
	}
	
	public void CreateFrame(JFrame parentFrame, ModoOperacao modo) {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Cadastro Produto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 569, 223);
		setLocationRelativeTo(parentFrame);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(199, 46, 307, 20);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);

		lblDescricao = new JLabel("Descrição");
		lblDescricao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescricao.setBounds(125, 49, 64, 14);
		contentPane.add(lblDescricao);

		lblCodigo = new JLabel("Código");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(119, 24, 70, 14);
		contentPane.add(lblCodigo);

		lblPrecoUnitario = new JLabel("Preço Unitário (R$)");
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecoUnitario.setBounds(36, 74, 153, 14);
		contentPane.add(lblPrecoUnitario);

		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(199, 21, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtPrecoUnitario = new JTextField();
		txtPrecoUnitario.setBounds(199, 71, 86, 20);
		contentPane.add(txtPrecoUnitario);
		txtPrecoUnitario.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
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
					JOptionPane.showMessageDialog(CadastroProduto.this, "Erro ao salvar o produto! \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSalvar.setBounds(417, 139, 89, 23);
		contentPane.add(btnSalvar);
	}
	
	private void salvar() {
		String descricao = txtDescricao.getText();
		double precoUnitario = Double.parseDouble(txtPrecoUnitario.getText());

		ProdutoController produtoController = new ProdutoController(new Produto(descricao, precoUnitario));
		produtoController.salvar();

		JOptionPane.showMessageDialog(CadastroProduto.this, "Produto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	private void atualizar() {
		_produto.setDescricao(txtDescricao.getText());
		_produto.setPrecoUnitario(Double.parseDouble(txtPrecoUnitario.getText()));

		ProdutoController produtoController = new ProdutoController(_produto);
		produtoController.atualizar();

		JOptionPane.showMessageDialog(CadastroProduto.this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	public void setCadastroProdutoListener(ICadastroProdutoListener listener) {
		this._listener = listener;
	}

}
