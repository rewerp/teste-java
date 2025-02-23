package com.application.controller;

import java.util.List;

import com.application.model.Cliente;
import com.application.dao.ClienteDAO;

public class ClienteController {

	private Cliente _cliente;
	
	public ClienteController() {
	}

	public ClienteController(Cliente cliente) {
		_cliente = cliente;
	};
	
	private void validarCliente() {
		if (_cliente == null) {
			throw new IllegalArgumentException("Cliente inválido!");
		} 
		
		if (_cliente.getNome() == null || _cliente.getNome().trim().isEmpty()) {
			throw new IllegalArgumentException("O nome deve ser preenchido!");
		} 
		
		if (_cliente.getLimiteCompra() <= 0) {
			throw new IllegalArgumentException("O limite de compra não pode ser ZERO ou negativo!");
		} 
		
		if (_cliente.getDiaFechamentoFatura() < 1 || _cliente.getDiaFechamentoFatura() > 31) {
			throw new IllegalArgumentException("O dia de fechamento da fatura deve ser um dia do mês válido!");
		}
	}

	public void salvar() {
		validarCliente();

		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.salvar(_cliente);
	}
	
	public void atualizar() {
		validarCliente();

		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.atualizar(_cliente);
	}
	
	public void excluir(int codigo) {
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.excluir(codigo);
	}
	
	public List<Cliente> buscarTodos() {
	    ClienteDAO clienteDAO = new ClienteDAO();
	    return clienteDAO.buscarTodos();
	}
	
	public Cliente buscarPorCodigo(int codigo) {
		ClienteDAO clienteDAO = new ClienteDAO();
	    return clienteDAO.buscarPorCodigo(codigo);
	}

}
