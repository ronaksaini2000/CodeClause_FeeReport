/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FeeReportManagement;

/**
 *
 * @author ronak
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ATMInterface {
    private static double accountBalance;
    private static final String USER_ID = "0001";
    private static final String PIN = "7890";
    private static final String DATA_FILE = "data.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userId, pin;
        do {
            System.out.print("Enter user ID: ");
            userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            pin = scanner.nextLine();
        } while (!authenticate(userId, pin));
        loadData(userId);
        int choice = 0;
        while (choice != 5) {
            System.out.println("ATM Interface");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    saveData(userId);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    saveData(userId);
                    break;
                case 4:
                    System.out.print("Enter recipient's user ID: ");
                    String recipientId = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    transfer(recipientId, transferAmount);
                    saveData(userId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static boolean authenticate(String userId, String pin) {
        return userId.equals(USER_ID) && pin.equals(PIN);
    }

    private static void loadData(String userId) {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                accountBalance = 0.0;
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(userId)) {
                    accountBalance = Double.parseDouble(parts[1]);
                   
                }
            }
          scanner.close();

            accountBalance = 0.0;

        }

        catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }

    }

    private static void saveData(String userId) {
        try {
            File file = new File(DATA_FILE);
            FileWriter writer = new FileWriter(file, true);
            writer.write(userId + "," + accountBalance + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private static void checkBalance() {
        System.out.println("Current balance: " + accountBalance);
    }

    private static void deposit(double amount) {
        accountBalance += amount;
        System.out.println("Deposit successful. New balance: " + accountBalance);
    }

    private static void withdraw(double amount) {
        if (amount > accountBalance) {
            System.out.println("Insufficient funds");
        } else {
            accountBalance -= amount;
            System.out.println("Withdrawal successful. New balance: " + accountBalance);
        }
    }

    private static void transfer(String recipientId, double amount) {

        if (amount > accountBalance) {
            System.out.println("Insufficient funds");
        } else {
            accountBalance -= amount;
            System.out.println("Transfer successful. New balance: " + accountBalance);
        }
    }
}

