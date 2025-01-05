package com.application.teste;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.application.dao.VendaDAO;
import com.application.model.Cliente;
import com.application.model.Produto;
import com.application.model.Venda;
import com.application.model.VendaItem;

public class VendaAdicionarTeste {

    public static void main(String[] args) {
        
    	Cliente cliente = new Cliente();
    	cliente.setCodigo(1);
        cliente.setNome("João Silva");
        cliente.setLimiteCompra(1500);
        cliente.setDiaFechamentoFatura(5);
    	
        Produto produtoUm = new Produto();
        produtoUm.setCodigo(18);
        produtoUm.setDescricao("Power Bank 20.000mAh");
        produtoUm.setPrecoUnitario(180.00);
        
        Produto produtoDois = new Produto();
        produtoDois.setCodigo(5);
        produtoDois.setDescricao("Teclado Mecânico RGB");
        produtoDois.setPrecoUnitario(350);
        
        VendaItem vendaItemUm = new VendaItem();
        vendaItemUm.setProduto(produtoUm);
        vendaItemUm.setQuantidade(5);
        vendaItemUm.setValorTotal(produtoUm.getPrecoUnitario() * vendaItemUm.getQuantidade());
        
        VendaItem vendaItemDois = new VendaItem();
        vendaItemDois.setProduto(produtoDois);
        vendaItemDois.setQuantidade(5);
        vendaItemDois.setValorTotal(produtoDois.getPrecoUnitario() * vendaItemDois.getQuantidade());
        
        List<VendaItem> itens = new ArrayList<>();
//        itens.add(vendaItemUm);
        itens.add(vendaItemDois);
       
        VendaDAO vendaDAO = new VendaDAO();
        
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setDataVenda(LocalDate.parse("2025-01-01"));
        
        System.out.println(venda.getDataVenda());
        
        venda.setVendaItens(itens);
        venda.setCodigo(11);
        
//        Venda vendaRetorno = vendaDAO.salvar(venda);
        Venda vendaRetorno = vendaDAO.atualizar(venda);
        
        System.out.println(vendaRetorno.toJSON());
    }
	
}
