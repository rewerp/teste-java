package com.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.application.database.DatabaseConnection;
import com.application.model.VendaItem;

public class VendaItemDAO {

	public void salvar(int codigoVenda, VendaItem vendaItem) {
        String sql = "INSERT INTO venda_itens (codigo_venda, codigo_produto, preco_unitario, quantidade, valor_total) VALUES (?, ?, ?, ?, ?);";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigoVenda);
        	statement.setInt(2, vendaItem.getProduto().getCodigo());
        	statement.setDouble(3, vendaItem.getProduto().getPrecoUnitario());
        	statement.setDouble(4, vendaItem.getQuantidade());
        	statement.setDouble(5, vendaItem.getValorTotal());
        	statement.executeUpdate();
        	
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o item da venda!", e);
        }
	}
	
	public void salvar(Connection connection ,int codigoVenda, VendaItem vendaItem) {
        String sql = "INSERT INTO venda_itens (codigo_venda, codigo_produto, preco_unitario, quantidade, valor_total) VALUES (?, ?, ?, ?, ?);";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigoVenda);
        	statement.setInt(2, vendaItem.getProduto().getCodigo());
        	statement.setDouble(3, vendaItem.getProduto().getPrecoUnitario());
        	statement.setDouble(4, vendaItem.getQuantidade());
        	statement.setDouble(5, vendaItem.getValorTotal());
        	statement.executeUpdate();
        	
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o item da venda!", e);
        }
	}
	
	public void atualizar(int codigoVenda, VendaItem vendaItem) {
        String sql = "UPDATE venda_itens SET codigo_produto = ?, preco_unitario = ?, quantidade = ?, valor_total = ? WHERE codigo_venda = ? and codigo_produto = ?;";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, vendaItem.getProduto().getCodigo());
        	statement.setDouble(2, vendaItem.getProduto().getPrecoUnitario());
        	statement.setDouble(3, vendaItem.getQuantidade());
        	statement.setDouble(4, vendaItem.getValorTotal());
        	statement.setInt(5, codigoVenda);
        	statement.setInt(6, vendaItem.getProduto().getCodigo());
        	statement.executeUpdate();
        	
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o item da venda!", e);
        }
	}
	
	public void excluir(int codigoVenda, int codigoProduto) {
        String sql = "DELETE FROM venda_itens WHERE codigo_venda = ? AND codigo_produto = ?;";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigoVenda);
        	statement.setInt(2, codigoProduto);
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o item da venda!", e);
        }
	}
	
	public void excluirTodosPorVenda(Connection connection, int codigoVenda) {
        String sql = "DELETE FROM venda_itens WHERE codigo_venda = ?;";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigoVenda);
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o item da venda!", e);
        }
	}
	
	public List<VendaItem> buscarPorVenda(int codigoVenda) {
        String sql = "SELECT * FROM venda_itens WHERE codigo_venda = ?;";
        List<VendaItem> vendaItens = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigoVenda);
        	
        	try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	vendaItens.add(mapearVendaItens(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as vendas!", e);
        }
        
        return vendaItens;
	}
	
	public List<VendaItem> buscarPorVenda(Connection connection, int codigoVenda) {
        String sql = "SELECT * FROM venda_itens WHERE codigo_venda = ?;";
        List<VendaItem> vendaItens = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigoVenda);
        	
        	try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	vendaItens.add(mapearVendaItens(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as vendas!", e);
        }
        
        return vendaItens;
	}
	
    private VendaItem mapearVendaItens(ResultSet resultSet) throws SQLException {
    	VendaItem vendaItem = new VendaItem();
    	vendaItem.setProduto(new ProdutoDAO().buscarPorCodigo(resultSet.getInt("codigo_produto")));
    	vendaItem.setQuantidade(resultSet.getDouble("quantidade"));
    	vendaItem.setValorTotal(resultSet.getDouble("valor_total"));
        
        return vendaItem;
    }
	
}
