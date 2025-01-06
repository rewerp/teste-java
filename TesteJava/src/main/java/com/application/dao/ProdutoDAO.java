package com.application.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.application.database.DatabaseConnection;
import com.application.model.Produto;

public class ProdutoDAO {

    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (descricao, preco_unitario) VALUES (?, ?);";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setString(1, produto.getDescricao());
        	statement.setDouble(2, produto.getPrecoUnitario());
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o produto!", e);
        }
    }

    public Produto buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM produtos WHERE codigo = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigo);
        	
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearProduto(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o produto por CODIGO!", e);
        }
        
        return null;
    }

    public List<Produto> buscarTodos() {
        String sql = "SELECT * FROM produtos ORDER BY codigo";
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
            	produtos.add(mapearProduto(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os produtos!", e);
        }
        
        return produtos;
    }

    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET descricao = ?, preco_unitario = ? WHERE codigo = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setString(1, produto.getDescricao());
        	statement.setDouble(2, produto.getPrecoUnitario());
        	statement.setInt(3, produto.getCodigo());
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o produto!", e);
        }
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigo);
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o produto!", e);
        }
    }

    private Produto mapearProduto(ResultSet resultSet) throws SQLException {
        Produto produto = new Produto();
        produto.setCodigo(resultSet.getInt("codigo"));
        produto.setDescricao(resultSet.getString("descricao"));
        produto.setPrecoUnitario(resultSet.getDouble("preco_unitario"));
        
        return produto;
    }
	
}
