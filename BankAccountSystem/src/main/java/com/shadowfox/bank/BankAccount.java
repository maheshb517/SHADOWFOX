package com.shadowfox.bank;

import java.sql.*;

public class BankAccount {
    private String username;
    private String accountNumber;
    private String accountType;
    private double balance;

    // MySQL Connection details (Make them private & final)
    private static final String URL = "jdbc:mysql://localhost:3306/BankDB?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "mahesh"; // Change this to your correct password

    // Constructor (Loads account from DB)
    public BankAccount(String username) {
        this.username = username;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connected to MySQL database!");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        loadAccount();
    }

    // Load account from the database
    private void loadAccount() {
        String query = "SELECT * FROM Accounts WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.accountNumber = rs.getString("account_number");
                this.accountType = rs.getString("account_type");
                this.balance = rs.getDouble("balance");

                System.out.println("✅ Account loaded successfully!");
                System.out.println("🔹 Account Number: " + this.accountNumber);
                System.out.println("🔹 Account Type: " + this.accountType);
                System.out.println("🔹 Balance: $" + this.balance);
            } else {
                System.out.println("❌ Account not found for username: " + username);
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error while loading account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            updateBalance();
            System.out.println("✅ Deposit Successful! New Balance: $" + balance);
        } else {
            System.out.println("❌ Deposit amount must be greater than zero.");
        }
    }

    // Withdraw money
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateBalance();
            System.out.println("✅ Withdrawal Successful! New Balance: $" + balance);
            return true;
        } else {
            System.out.println("❌ Insufficient Balance.");
            return false;
        }
    }

    // Update balance in the database
    private void updateBalance() {
        String query = "UPDATE Accounts SET balance = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, balance);
            stmt.setString(2, username);
            stmt.executeUpdate();
            System.out.println("✅ Balance updated in the database.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double getBalance() {
        return balance;
    }
}
