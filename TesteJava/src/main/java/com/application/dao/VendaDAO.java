package com.application.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.application.database.DatabaseConnection;
import com.application.model.Venda;
import com.application.model.VendaItem;

public class VendaDAO {
	
	public Venda salvar(Venda venda) {
	    String sql = "INSERT INTO vendas (codigo_cliente, data_venda, valor_total) VALUES (?, ?, ?);";
	    VendaItemDAO vendaItemDAO = new VendaItemDAO();
	    
	    try (Connection connection = DatabaseConnection.getConnection()) {
	    	connection.setAutoCommit(false);
	    	
	        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setInt(1, venda.getCliente().getCodigo());
	            statement.setDate(2, Date.valueOf(venda.getDataVenda()));
	            statement.setDouble(3, 0); // Valor inicial (atualizado posteriormente por trigger)
	            statement.executeUpdate();
	            
	            // Obtendo o código gerado automaticamente
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    venda.setCodigo(generatedKeys.getInt(1));
	                } else {
	                    throw new SQLException("Erro ao obter o código gerado para a venda.");
	                }
	            }

	            // Persistindo os itens da venda
	            for (VendaItem item : venda.getVendaItens()) {
	                vendaItemDAO.salvar(connection, venda.getCodigo(), item);
	            }
	            
	            connection.commit();
	            
	            // Atualizando o valor gerado por trigger
	            venda.setValorTotal(VendaDAO.this.buscarPorCodigo(venda.getCodigo()).getValorTotal());

	            return venda;

	        } catch (SQLException e) {
	            connection.rollback();
	            throw new RuntimeException("Erro ao salvar a venda!", e);
	        } finally {
	        	connection.setAutoCommit(true);
	        }
	        
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao salvar a venda!", e);
	    }
	}
	
	public Venda atualizar(Venda venda) {
        String sql = "UPDATE vendas SET codigo_cliente = ?, data_venda = ? WHERE codigo = ?;";
        VendaItemDAO vendaItemDAO = new VendaItemDAO();
        
        try (Connection connection = DatabaseConnection.getConnection()) {
        	connection.setAutoCommit(false);
        	
        	try (PreparedStatement statement = connection.prepareStatement(sql)) {
        		statement.setInt(1, venda.getCliente().getCodigo());
	            statement.setDate(2, Date.valueOf(venda.getDataVenda()));
	            statement.setInt(3, venda.getCodigo());
	            statement.executeUpdate();
	            
	            vendaItemDAO.excluirTodosPorVenda(connection, venda.getCodigo());

	            // Persistindo os itens da venda
	            for (VendaItem item : venda.getVendaItens()) {
	                vendaItemDAO.salvar(connection, venda.getCodigo(), item);
	            }
        		
	            connection.commit();
	            
	            // Atualizando o valor gerado por trigger
	            venda.setValorTotal(VendaDAO.this.buscarPorCodigo(venda.getCodigo()).getValorTotal());

	            return venda;
        		
        	} catch(SQLException e) {
        		connection.rollback();
	            throw new RuntimeException("Erro ao atualizar a venda!", e);
        	} finally {
        		connection.setAutoCommit(true);
        	}
        	
        } catch(SQLException e) {
        	throw new RuntimeException("Erro ao atualizar a venda!", e);
        }
	}
	
	public void excluir(int codigo) {
        String sql = "DELETE FROM vendas WHERE codigo = ?;";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigo);
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir a venda!", e);
        }
	}
	
	public List<Venda> buscarTodos() {
        String sql = "SELECT * FROM vendas ORDER BY codigo;";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                vendas.add(mapearVenda(connection, resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as vendas!", e);
        }
        
        return vendas;
	}
	
    public Venda buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM vendas WHERE codigo = ?;";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codigo);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearVenda(connection, resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a venda por código!", e);
        }
        
        return null;
    }
	
	public List<Venda> buscarPorCliente(int codigoCliente) {
        String sql = "SELECT * FROM vendas WHERE codigo_cliente = ?;";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codigoCliente);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vendas.add(mapearVenda(connection, resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a venda por código do cliente!", e);
        }
        
        return vendas;
	}
	
	public List<Venda> buscarPorProduto(int codigoProduto) {
        String sql = "SELECT DISTINCT v.* FROM vendas v INNER JOIN venda_itens vi ON v.codigo = vi.codigo_venda WHERE vi.codigo_produto = ?;";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codigoProduto);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vendas.add(mapearVenda(connection, resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a venda por código do produto!", e);
        }
        
        return vendas;
	}
	
	public List<Venda> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		String sql = "SELECT * FROM vendas WHERE data_venda BETWEEN ? AND ? ORDER BY codigo;";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(dataInicio));
            statement.setDate(2, Date.valueOf(dataFim));
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vendas.add(mapearVenda(connection, resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a venda por período!", e);
        }
        
        return vendas;
	}
    
    private Venda mapearVenda(Connection connection, ResultSet resultSet) throws SQLException {
        Venda venda = new Venda();
        venda.setCodigo(resultSet.getInt("codigo"));
        venda.setCliente(new ClienteDAO().buscarPorCodigo(resultSet.getInt("codigo_cliente")));
        venda.setDataVenda(resultSet.getDate("data_venda").toLocalDate());
        venda.setValorTotal(resultSet.getDouble("valor_total"));
        
        // Carregar os itens da venda
        venda.setVendaItens(new VendaItemDAO().buscarPorVenda(connection, venda.getCodigo()));
        
        return venda;
    }
	
}
