package com.shadowfox.bank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isAuthenticated = false;
        String username = "";

        while (!isAuthenticated) {
            System.out.println("\n--- Welcome to the Bank System ---");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if (UserAuthentication.loginUser(username, password)) {
                    isAuthenticated = true;
                }
            } else if (choice == 2) {
                System.out.print("Choose a username: ");
                username = scanner.nextLine();
                System.out.print("Choose an account number: ");
                String accountNumber = scanner.nextLine();
                System.out.print("Choose an account type (savings/current): ");
                String accountType = scanner.nextLine();
                System.out.print("Set your password: ");
                String password = scanner.nextLine();

                if (UserAuthentication.registerUser(username, accountNumber, accountType, password)) {
                    System.out.println("✅ Please log in now.");
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else {
                System.out.println("❌ Invalid choice. Please try again.");
            }
        }

        // User is authenticated - Load their account
        BankAccount account = new BankAccount(username);
        while (true) {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int action = scanner.nextInt();

            if (action == 1) {
                System.out.print("Enter deposit amount: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
                System.out.println("✅ Deposit Successful. New Balance: $" + account.getBalance());
            } else if (action == 2) {
                System.out.print("Enter withdrawal amount: ");
                double amount = scanner.nextDouble();
                if (account.withdraw(amount)) {
                    System.out.println("✅ Withdrawal Successful. New Balance: $" + account.getBalance());
                } else {
                    System.out.println("❌ Insufficient Balance.");
                }
            } else if (action == 3) {
                System.out.println("Current Balance: $" + account.getBalance());
            } else if (action == 4) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("❌ Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
