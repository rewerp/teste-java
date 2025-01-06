package com.application.controller;

import java.util.List;

import com.application.model.Produto;
import com.application.dao.ProdutoDAO;

public class ProdutoController {

	private Produto _produto;
	
	public ProdutoController() {
	}

	public ProdutoController(Produto produto) {
		_produto = produto;
	};
	
	private void validarProduto() {
		if (_produto == null) {
			throw new IllegalArgumentException("Produto inválido!");
		} 
		
		if (_produto.getDescricao() == null || _produto.getDescricao().trim().isEmpty()) {
			throw new IllegalArgumentException("A descrição deve ser preenchida!");
		} 
		
		if (_produto.getPrecoUnitario() <= 0) {
			throw new IllegalArgumentException("O preço unitário não pode ser ZERO ou negativo!");
		}
	}

	public void salvar() {
		validarProduto();

		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.salvar(_produto);
	}
	
	public void atualizar() {
		validarProduto();

		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.atualizar(_produto);
	}
	
	public void excluir(int codigo) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.excluir(codigo);
	}
	
	public List<Produto> buscarTodos() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
	    return produtoDAO.buscarTodos();
	}
	
	public Produto buscarPorCodigo(int codigo) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
	    return produtoDAO.buscarPorCodigo(codigo);
	}
	
}
