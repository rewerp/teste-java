package com.application.teste;

import com.application.dao.VendaDAO;

public class VendaExcluirTeste {
	
	public static void main(String[] args) {
		
		VendaDAO vendaDAO = new VendaDAO();
		vendaDAO.excluir(12);
		
	}
	
}
