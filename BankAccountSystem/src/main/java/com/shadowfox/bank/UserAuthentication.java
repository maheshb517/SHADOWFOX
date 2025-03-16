package com.shadowfox.bank;

import java.sql.*;

public class UserAuthentication {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/BankDB?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "mahesh";  // Update if needed

    // Register a new user
    public static boolean registerUser(String username, String accountNumber, String accountType, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Accounts (username, account_number, account_type, balance, password) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, accountNumber);
            stmt.setString(3, accountType);
            stmt.setDouble(4, 0.0);  // Start with $0 balance
            stmt.setString(5, password);
            stmt.executeUpdate();
            System.out.println("✅ User registered successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Login user
    public static boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM Accounts WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password"); // Get password from DB
                if (storedPassword.equals(password)) { // Compare with input password
                    System.out.println("✅ Login successful! Welcome, " + username);
                    return true;
                } else {
                    System.out.println("❌ Incorrect password.");
                }
            } else {
                System.out.println("❌ Username not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
