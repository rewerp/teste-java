package com.application.teste;

import java.time.LocalDate;
import java.util.List;

import com.application.dao.VendaDAO;
import com.application.model.Venda;

public class VendaBuscaTeste {
	
	private static Venda vendaRetorno;

	public static void main(String[] args) {

		VendaDAO vendaDAO = new VendaDAO();
		vendaRetorno = new Venda();
		
		List<Venda> listaVendasRetorno;
		
		vendaRetorno = vendaDAO.buscarPorCodigo(6);
		System.out.println(vendaRetorno.toJSON());
		
		listaVendasRetorno = vendaDAO.buscarTodos();
		System.out.println(listaVendasRetorno);
		
		listaVendasRetorno = vendaDAO.buscarPorCliente(1);
		System.out.println(listaVendasRetorno);
		
		listaVendasRetorno = vendaDAO.buscarPorProduto(3);
		System.out.println(listaVendasRetorno);
		
		listaVendasRetorno = vendaDAO.buscarPorPeriodo(LocalDate.parse("2025-01-01"), LocalDate.parse("2025-01-03"));
		System.out.println(listaVendasRetorno);

	}

}
