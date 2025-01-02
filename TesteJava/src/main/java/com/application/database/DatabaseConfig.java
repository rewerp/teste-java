package com.application.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

    private static final String CONFIG_FILE = "src/main/resources/db.properties";
    private static Properties properties;

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo de configuração: " + CONFIG_FILE, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
	
}
