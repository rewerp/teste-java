package com.application.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.application.dao.VendaDAO;
import com.application.model.Venda;
import com.application.model.VendaItem;

public class VendaController {

	private Venda _venda;
	
	public VendaController() {
	}

	public VendaController(Venda venda) {
		_venda = venda;
	};
	
	private void validarItemVenda(VendaItem vendaItem) {
		if (vendaItem == null) {
			throw new IllegalArgumentException("Item inválido!");
		}
		
		if (vendaItem.getProduto().getPrecoUnitario() == 0) {
			throw new IllegalArgumentException("O preço unitário deve ser maior que ZERO!");
		}
		
		if (vendaItem.getQuantidade() == 0) {
			throw new IllegalArgumentException("A quantidade do item deve ser maior que ZERO!");
		}
	}
	
	private void validarVenda() {
		
		if (_venda == null) {
			throw new IllegalArgumentException("Venda inválido!");
		} 
		
		if (_venda.getCliente() == null) {
			throw new IllegalArgumentException("Cliente inválido ou não selecionado!");
		} 
		
		double valorTotalVenda = _venda.getVendaItens().stream().mapToDouble(VendaItem::getValorTotal).sum();
		
		if (_venda.getCliente().getLimiteCompra() < valorTotalVenda) {
			throw new IllegalArgumentException("Valor total da venda é maior que o Limite de crédito do cliente para compras!");
		}
		
		if (_venda.getDataVenda() == null) {
			throw new IllegalArgumentException("Uma data de venda válida deve ser informada");
		}
	}

	public void salvar() {
		validarVenda();

		VendaDAO vendaDAO = new VendaDAO();
		vendaDAO.salvar(_venda);
	}
	
	public void atualizar() {
		validarVenda();

		VendaDAO vendaDAO = new VendaDAO();
		vendaDAO.atualizar(_venda);
	}
	
	public void excluir(int codigo) {
		VendaDAO vendaDAO = new VendaDAO();
		vendaDAO.excluir(codigo);
	}
	
	public Venda adicionarItem(VendaItem vendaItem) throws Exception {
		validarItemVenda(vendaItem);

		List<VendaItem> listaItens = new ArrayList<>();
		
		if (_venda.getVendaItens() != null) listaItens = _venda.getVendaItens();
		
		if (!listaItens.isEmpty() && listaItens.stream().anyMatch(item -> item.getProduto().getCodigo() == vendaItem.getProduto().getCodigo())) {
			throw new Exception("Produto já adicionado nessa venda!");
		}
		
		listaItens.add(vendaItem);
		_venda.setVendaItens(listaItens);
		
		return _venda;
	}
	
	public Venda excluirItem(int codigoItem) throws Exception {
		List<VendaItem> listaItens = _venda.getVendaItens();
		listaItens.removeIf(item -> item.getProduto().getCodigo() == codigoItem);
		_venda.setVendaItens(listaItens);
		
		return _venda;
	}
	
	public List<Venda> buscarTodos() {
		VendaDAO vendaDAO = new VendaDAO();
	    return vendaDAO.buscarTodos();
	}
	
	public Venda buscarPorCodigo(int codigo) {
		VendaDAO vendaDAO = new VendaDAO();
	    return vendaDAO.buscarPorCodigo(codigo);
	}
	
	public List<Venda> buscarPorCliente(int codigoCliente) {
		VendaDAO vendaDAO = new VendaDAO();
	    return vendaDAO.buscarPorCliente(codigoCliente);
	}
	
	public List<Venda> buscarPorProduto(int codigoProduto) {
		VendaDAO vendaDAO = new VendaDAO();
	    return vendaDAO.buscarPorProduto(codigoProduto);
	}
	
	public List<Venda> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		VendaDAO vendaDAO = new VendaDAO();
	    return vendaDAO.buscarPorPeriodo(dataInicio, dataFim);
	}
	
}
