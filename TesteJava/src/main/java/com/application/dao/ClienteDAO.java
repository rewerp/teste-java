package com.application.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.application.database.DatabaseConnection;
import com.application.model.Cliente;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, limite_compra, dia_fechamento_fatura) VALUES (?, ?, ?);";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setString(1, cliente.getNome());
        	statement.setDouble(2, cliente.getLimiteCompra());
        	statement.setInt(3, cliente.getDiaFechamentoFatura());
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o cliente!", e);
        }
    }

    public Cliente buscarPorId(int codigo) {
        String sql = "SELECT * FROM clientes WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCliente(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o cliente por CODIGO!", e);
        }
        return null;
    }

    public List<Cliente> buscarTodos() {
        String sql = "SELECT * FROM clientes ORDER BY codigo";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clientes.add(mapearCliente(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os clientes!", e);
        }
        return clientes;
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, limite_compra = ?, dia_fechamento_fatura = ? WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setString(1, cliente.getNome());
        	statement.setDouble(2, cliente.getLimiteCompra());
        	statement.setInt(3, cliente.getDiaFechamentoFatura());
        	statement.setInt(4, cliente.getCodigo());
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o cliente!", e);
        }
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM clientes WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setInt(1, codigo);
        	statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o cliente!", e);
        }
    }

    private Cliente mapearCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCodigo(resultSet.getInt("codigo"));
        cliente.setNome(resultSet.getString("nome"));
        cliente.setLimiteCompra(resultSet.getDouble("limite_compra"));
        cliente.setDiaFechamentoFatura(resultSet.getInt("dia_fechamento_fatura"));
        return cliente;
    }
	
}
