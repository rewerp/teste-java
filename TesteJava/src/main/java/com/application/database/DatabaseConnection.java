package com.application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
    public static Connection getConnection() {
        try {
            String url = DatabaseConfig.get("db.url");
            String user = DatabaseConfig.get("db.user");
            String password = DatabaseConfig.get("db.password");
            
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    
}
