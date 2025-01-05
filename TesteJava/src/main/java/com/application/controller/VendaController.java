package com.application.controller;

import java.util.List;

import com.application.dao.VendaDAO;
import com.application.model.Venda;

public class VendaController {

	private Venda _venda;
	
	public VendaController() {
	}

	public VendaController(Venda venda) {
		_venda = venda;
	};
	
	private void validarVenda() {
//		if (_venda == null) {
//			throw new IllegalArgumentException("Produto inválido!");
//		} else if (_venda.getDescricao() == null || _venda.getDescricao().trim().isEmpty()) {
//			throw new IllegalArgumentException("A descrição deve ser preenchida!");
//		} else if (_venda.getPrecoUnitario() <= 0) {
//			throw new IllegalArgumentException("O preço unitário não pode ser ZERO ou negativo!");
//		}
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
	
	public List<Venda> buscarTodos() {
		VendaDAO vendaDAO = new VendaDAO();
	    return vendaDAO.buscarTodos();
	}
	
}
