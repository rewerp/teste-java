package com.application.teste;

import com.application.dao.ClienteDAO;
import com.application.model.Cliente;

public class ClienteAdicionarTeste {

    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setLimiteCompra(5000.00);
        cliente.setDiaFechamentoFatura(15);
        clienteDAO.salvar(cliente);

        Cliente c = clienteDAO.buscarPorCodigo(1);
        System.out.println(c);

        clienteDAO.buscarTodos().forEach(System.out::println);
    }
	
}
