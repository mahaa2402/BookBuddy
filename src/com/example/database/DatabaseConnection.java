package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:oracle:thin:@localhost:1521/XE"; // Adjust the URL as needed
            String username = "SYSTEM"; // Your Oracle database username
            String password = "system"; // Your Oracle database password

            // Establish and return the connection
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Set to null to allow reconnection later
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    // Main method for testing the connection
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println("Connection successful!");
            // Close the connection when done
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
