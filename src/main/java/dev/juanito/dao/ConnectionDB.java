package dev.juanito.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/notasdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionDB() {}

    public static Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            System.out.println("Succesful connection wit MySQL");

        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return connection;
    }
}