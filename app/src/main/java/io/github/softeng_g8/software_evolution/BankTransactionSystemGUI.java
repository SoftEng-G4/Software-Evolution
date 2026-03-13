package io.github.softeng_g8.software_evolution;

import io.github.softeng_g8.software_evolution.actions.DepositWorker;
import io.github.softeng_g8.software_evolution.actions.WithdrawWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankTransactionSystemGUI {
    private static BankAccount account = new BankAccount(1000);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bank Transaction System");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField depositField = new JTextField(10);
        JTextField withdrawField = new JTextField(10);
        // Display initial balance with 2 decimal formatting
        JLabel balanceLabel = new JLabel("Balance: " + String.format("%.2f", account.getBalance()));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");

        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Deposit Amount:"));
        panel.add(depositField);
        panel.add(depositButton);
        panel.add(new JLabel("Withdraw Amount:"));
        panel.add(withdrawField);
        panel.add(withdrawButton);
        panel.add(balanceLabel);
        // ------------------------------------------------

        frame.add(panel);
        frame.setVisible(true);

        // Standardized Action for deposit
        depositButton.addActionListener((ActionEvent e) -> {
            new DepositWorker(balanceLabel, account, depositField).execute();
        });

        // Standardized Action for withdrawal (Replaced custom Thread with Worker)
        withdrawButton.addActionListener((ActionEvent e) -> {
            new WithdrawWorker(balanceLabel, account, withdrawField).execute();
        });
    }
}