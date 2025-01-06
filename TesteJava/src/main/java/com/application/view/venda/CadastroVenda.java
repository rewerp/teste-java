package com.application.view.venda;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;

import com.application.controller.ClienteController;
import com.application.controller.ProdutoController;
import com.application.controller.VendaController;
import com.application.enums.ModoOperacao;
import com.application.model.Cliente;
import com.application.model.Produto;
import com.application.model.Venda;
import com.application.model.VendaItem;
import com.application.util.NumberUtils;


public class CadastroVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaVendaItens;
	private JTextField txtCodigo;
	private JTextField txtCodigoCliente;
	private JTextField txtNomeCliente;
	private JTextField txtValorTotal;
	private JTextField txtCodigoItem;
	private JTextField txtDescricaoItem;
	private JTextField txtPrecoUnitarioItem;
	private JTextField txtQuantidadeItem;
	private JTextField txtValorTotalItem;
	private JTextField txtData;
	
	private ICadastroVendaListener _listener;
	private Venda _venda;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroVenda frame = new CadastroVenda();
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
	public CadastroVenda() {
		setResizable(false);
		CreateFrame(null, null);
	}
	
	public CadastroVenda(JFrame parentFrame, ModoOperacao modo) {
		CreateFrame(parentFrame, modo);
		
		if (_venda == null) {
			_venda = new Venda();
		}
	}
	
	public CadastroVenda(JFrame parentFrame, ModoOperacao modo, Venda venda) {
		CreateFrame(parentFrame, modo);
		
		_venda = venda;
		
		txtCodigo.setText(String.valueOf(_venda.getCodigo()));
		txtCodigoCliente.setText(String.valueOf(_venda.getCliente().getCodigo()));
		txtNomeCliente.setText(_venda.getCliente().getNome());
		txtData.setText(_venda.getDataVenda().format(formatter));
		txtValorTotal.setText(String.valueOf(_venda.getValorTotal()));
		
		carregarItens(_venda);
	}
	
	public void CreateFrame(JFrame parentFrame, ModoOperacao modo) {
		setTitle("Cadastro Venda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(parentFrame);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 237, 964, 236);
		contentPane.add(scrollPane);
		
		tabelaVendaItens = new JTable();
		scrollPane.setViewportView(tabelaVendaItens);
		String[] colunas = { "Código", "Descrição", "Preço Unitário (R$)", "Quantidade", "Valor Total (R$)" };
		DefaultTableModel model = new DefaultTableModel(null, colunas) {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
//		        return EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo);
		        return false;
		    }
		};		
		tabelaVendaItens.setModel(model);
		
		JButton btnSalvar = new JButton("Salvar Venda");
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
					JOptionPane.showMessageDialog(CadastroVenda.this, "Erro ao salvar a venda! \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSalvar.setBounds(854, 484, 120, 23);
		contentPane.add(btnSalvar);
		
		JPanel panelDadosItemVenda = new JPanel();
		panelDadosItemVenda.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDadosItemVenda.setName("");
		panelDadosItemVenda.setBounds(495, 11, 479, 181);
		contentPane.add(panelDadosItemVenda);
		panelDadosItemVenda.setLayout(null);
		
		JLabel lblDadosItemVenda = new JLabel("Dados Item Venda");
		lblDadosItemVenda.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDadosItemVenda.setBounds(10, 11, 140, 14);
		panelDadosItemVenda.add(lblDadosItemVenda);
		
		JLabel lblCodigoProduto = new JLabel("Código Produto");
		lblCodigoProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigoProduto.setBounds(32, 41, 94, 14);
		panelDadosItemVenda.add(lblCodigoProduto);
		
		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescricao.setBounds(20, 66, 106, 14);
		panelDadosItemVenda.add(lblDescricao);
		
		JLabel lblPrecoUnitario = new JLabel("Preço Unitário");
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecoUnitario.setBounds(20, 91, 106, 14);
		panelDadosItemVenda.add(lblPrecoUnitario);
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuantidade.setBounds(20, 116, 106, 14);
		panelDadosItemVenda.add(lblQuantidade);
		
		JLabel lblValorTotalItem = new JLabel("Valor Total (R$)");
		lblValorTotalItem.setVisible(false);
		lblValorTotalItem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorTotalItem.setBounds(20, 141, 106, 14);
		panelDadosItemVenda.add(lblValorTotalItem);
		
		txtCodigoItem = new JTextField();
		txtCodigoItem.setBounds(136, 38, 86, 20);
		txtCodigoItem.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		panelDadosItemVenda.add(txtCodigoItem);
		txtCodigoItem.setColumns(10);
		
		txtDescricaoItem = new JTextField();
		txtDescricaoItem.setBounds(136, 63, 300, 20);
		txtDescricaoItem.setEditable(false);
		panelDadosItemVenda.add(txtDescricaoItem);
		txtDescricaoItem.setColumns(10);
		
		txtPrecoUnitarioItem = new JTextField();
		txtPrecoUnitarioItem.setBounds(136, 88, 130, 20);
		txtPrecoUnitarioItem.setEditable(false);
		panelDadosItemVenda.add(txtPrecoUnitarioItem);
		txtPrecoUnitarioItem.setColumns(10);
		
		txtQuantidadeItem = new JTextField();
		txtQuantidadeItem.setBounds(136, 113, 86, 20);
		txtQuantidadeItem.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		panelDadosItemVenda.add(txtQuantidadeItem);
		txtQuantidadeItem.setColumns(10);
		
		txtValorTotalItem = new JTextField();
		txtValorTotalItem.setVisible(false);
		txtValorTotalItem.setBounds(136, 138, 130, 20);
		txtValorTotalItem.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		panelDadosItemVenda.add(txtValorTotalItem);
		txtValorTotalItem.setColumns(10);
		
		JButton btnAdicionarItem = new JButton("Adicionar");
		btnAdicionarItem.setEnabled(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		btnAdicionarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					adicionarItem();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(CadastroVenda.this, "Erro ao adicionar um item! \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAdicionarItem.setBounds(342, 137, 95, 23);
		panelDadosItemVenda.add(btnAdicionarItem);
		
		JButton btnBuscarProduto = new JButton("Buscar");
		btnBuscarProduto.setEnabled(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		btnBuscarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCodigoItem.getText() == "") {
					JOptionPane.showMessageDialog(CadastroVenda.this, "Código do produto deve ser informado!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				try {
					carregarProduto(NumberUtils.parseIntOrDefault(txtCodigoItem.getText(), 0));
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(CadastroVenda.this, "Erro ao carregar produto! \n " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBuscarProduto.setBounds(232, 38, 86, 20);
		panelDadosItemVenda.add(btnBuscarProduto);
		
		JPanel panelDadosVenda = new JPanel();
		panelDadosVenda.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDadosVenda.setBounds(10, 11, 475, 181);
		contentPane.add(panelDadosVenda);
		panelDadosVenda.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(52, 41, 64, 14);
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDadosVenda.add(lblCodigo);
		
		JLabel lblCodigoCliente = new JLabel("Código Cliente");
		lblCodigoCliente.setBounds(16, 66, 100, 14);
		lblCodigoCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDadosVenda.add(lblCodigoCliente);
		
		JLabel lblNomeCliente = new JLabel("Nome Cliente");
		lblNomeCliente.setBounds(22, 91, 94, 14);
		lblNomeCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDadosVenda.add(lblNomeCliente);
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(62, 116, 54, 14);
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDadosVenda.add(lblData);
		
		JLabel lblValorTotal = new JLabel("Valor Total (R$)");
		lblValorTotal.setBounds(10, 141, 106, 14);
		lblValorTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDadosVenda.add(lblValorTotal);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(126, 38, 86, 20);
		txtCodigo.setColumns(10);
		panelDadosVenda.add(txtCodigo);
		
		txtCodigoCliente = new JTextField();
		txtCodigoCliente.setBounds(126, 63, 86, 20);
		txtCodigoCliente.setColumns(10);
		txtCodigoCliente.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		panelDadosVenda.add(txtCodigoCliente);
		
		txtNomeCliente = new JTextField();
		txtNomeCliente.setBounds(126, 88, 300, 20);
		txtNomeCliente.setColumns(10);
		txtNomeCliente.setEditable(false);
		panelDadosVenda.add(txtNomeCliente);
		
		txtData = new JTextField();
		txtData.setBounds(126, 113, 86, 20);
		txtData.setEditable(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		txtData.setColumns(10);
		panelDadosVenda.add(txtData);
		
		txtValorTotal = new JTextField();
		txtValorTotal.setBounds(126, 138, 130, 20);
		txtValorTotal.setColumns(10);
		txtValorTotal.setEditable(false);
		panelDadosVenda.add(txtValorTotal);
		
		JLabel lblDadosVenda = new JLabel("Dados Venda");
		lblDadosVenda.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDadosVenda.setBounds(10, 11, 117, 14);
		panelDadosVenda.add(lblDadosVenda);
		
		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setEnabled(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		btnBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCodigoCliente.getText() == "") {
					JOptionPane.showMessageDialog(CadastroVenda.this, "Código do cliente não informado!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				try {
					carregarCliente(NumberUtils.parseIntOrDefault(txtCodigoCliente.getText(), 0));
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(CadastroVenda.this, "Erro ao buscar cliente! \n " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBuscarCliente.setBounds(222, 63, 86, 20);
		panelDadosVenda.add(btnBuscarCliente);
		
		JButton btnExcluirItem = new JButton("Excluir Item");
		btnExcluirItem.setEnabled(EnumSet.of(ModoOperacao.ADICIONAR, ModoOperacao.ATUALIZAR).contains(modo));
		btnExcluirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaVendaItens.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(CadastroVenda.this, "Nenhum item selecionado para exclusão!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(CadastroVenda.this, "Deseja excluir o item selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					try {
						int codigoItem = (int)tabelaVendaItens.getValueAt(selectedRow, 0);
						excluirItem(codigoItem);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(CadastroVenda.this, "Erro ao excluir item! \n " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}				
			}
		});
		btnExcluirItem.setBounds(854, 203, 120, 23);
		contentPane.add(btnExcluirItem);
	}
	
	private void adicionarItem() throws Exception {
		VendaController vendaController = new VendaController(_venda);
		
		Produto produto = new ProdutoController().buscarPorCodigo(NumberUtils.parseIntOrDefault(txtCodigoItem.getText(), 0));
		double quantidade = NumberUtils.parseDoubleOrDefault(txtQuantidadeItem.getText(), 0);
		
		VendaItem vendaItemAdicionar = new VendaItem(produto, quantidade);
		
		vendaController.adicionarItem(vendaItemAdicionar);
		carregarItens(_venda);
		
		txtCodigoItem.setText("");
		txtDescricaoItem.setText("");
		txtPrecoUnitarioItem.setText("");
		txtQuantidadeItem.setText("");
	}
	
	private void excluirItem(int codigoItem) throws Exception {
		VendaController vendaController = new VendaController(_venda);
		vendaController.excluirItem(codigoItem);
		carregarItens(_venda);
	}
	
	private void salvar() {
		_venda.setCliente(new ClienteController().buscarPorCodigo(NumberUtils.parseIntOrDefault(txtCodigoCliente.getText(), 0)));
		_venda.setDataVenda(LocalDate.parse(txtData.getText(), formatter));
		_venda.setValorTotal(0);

		VendaController vendaController = new VendaController(_venda);
		vendaController.salvar();
		
		JOptionPane.showMessageDialog(CadastroVenda.this, "Venda salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void atualizar() {
		_venda.setCliente(new ClienteController().buscarPorCodigo(NumberUtils.parseIntOrDefault(txtCodigoCliente.getText(), 0)));
		_venda.setDataVenda(LocalDate.parse(txtData.getText(), formatter));
		_venda.setValorTotal(0);

		VendaController vendaController = new VendaController(_venda);
		vendaController.atualizar();
		
		JOptionPane.showMessageDialog(CadastroVenda.this, "Venda atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void carregarItens(Venda venda) {
		DefaultTableModel model = (DefaultTableModel) tabelaVendaItens.getModel();
		model.setRowCount(0);

		for (VendaItem item : venda.getVendaItens()) {
			model.addRow(new Object[] { item.getProduto().getCodigo(), item.getProduto().getDescricao(), 
					item.getProduto().getPrecoUnitario(), item.getQuantidade(), item.getValorTotal() });
		}
	}
	
	private void carregarCliente(int codigoCliente) {
		Cliente clienteCarregar = new ClienteController().buscarPorCodigo(codigoCliente);
		_venda.setCliente(clienteCarregar);
		
		txtNomeCliente.setText(_venda.getCliente().getNome());
	}
	
	public void carregarProduto(int codigoProduto) {
		Produto produtoCarregar = new ProdutoController().buscarPorCodigo(codigoProduto);
		
		txtDescricaoItem.setText(produtoCarregar.getDescricao());
		txtPrecoUnitarioItem.setText(String.valueOf(produtoCarregar.getPrecoUnitario()));
	}
	
	public void setCadastroVendaListener(ICadastroVendaListener listener) {
		this._listener = listener;
	}
}
