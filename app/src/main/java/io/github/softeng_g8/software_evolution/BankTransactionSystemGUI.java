package io.github.softeng_g8.software_evolution;

import io.github.softeng_g8.software_evolution.actions.DepositWorker;
import io.github.softeng_g8.software_evolution.actions.WithdrawWorker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Main GUI class refactored for professional state management.
 * * WHY THE ARCHITECTURE?
 * 
 * 1. DYNAMIC VISIBILITY: The input field is decoupled from the main view and only 
 * appears when a transaction mode is active. This creates a cleaner, task-focused UI.
 * 
 * 2. PLACEHOLDER LOGIC: We use a FocusListener to manage the hint text ("Click again to go back"). 
 * This avoids cluttering the UI with extra labels.
 * 
 * 3. STATE MACHINE: The 'currentMode' variable controls the entire UI flow, ensuring 
 * that users cannot trigger multiple conflicting actions simultaneously.
 */
public class BankTransactionSystemGUI {
    private static final BankAccount account = new BankAccount(1000);
    private final JFrame frame = new JFrame("Bank Transaction System");
    private final JLabel balanceLabel = new JLabel("£" + String.format("%.2f", account.getBalance()));
    private final JTextField amountField = new JTextField(10);
    private final JButton depositBtn = new JButton("Deposit");
    private final JButton withdrawBtn = new JButton("Withdraw");
    private final JButton confirmBtn = new JButton("Confirm");
    private final TransactionHistoryPanel historyPanel = new TransactionHistoryPanel();
    
    private String currentMode = "NONE";
    private final String HINT_TEXT = "Click button again to go back";

    public BankTransactionSystemGUI() {
        initialiseUI();
        resetToInitialState();
    }

    private void initialiseUI() {
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 38));
        frame.setLayout(new BorderLayout(20, 0));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. TOP: Welcome header
        JLabel welcomeLabel = new JLabel("WELCOME USER", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(welcomeLabel, gbc);

        // 2. MIDDLE: Balance Header & Number
        JLabel balanceHeader = new JLabel("Current Balance", SwingConstants.CENTER);
        balanceHeader.setForeground(Color.LIGHT_GRAY);
        balanceHeader.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        centerPanel.add(balanceHeader, gbc);

        // UI Update: Changed font color to WHITE as requested
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 52));
        balanceLabel.setForeground(Color.WHITE); 
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        centerPanel.add(balanceLabel, gbc);

        // 3. BOTTOM: Actions
        amountField.setBackground(new Color(50, 50, 60));
        amountField.setForeground(Color.GRAY);
        amountField.setPreferredSize(new Dimension(0, 45));
        setupPlaceholder();
        gbc.gridy = 3;
        centerPanel.add(amountField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setOpaque(false);
        styleButton(withdrawBtn, new Color(150, 130, 255));
        styleButton(depositBtn, new Color(180, 255, 100));
        styleButton(confirmBtn, new Color(255, 200, 50));
        
        btnPanel.add(withdrawBtn);
        btnPanel.add(depositBtn);
        btnPanel.add(confirmBtn);
        gbc.gridy = 4;
        centerPanel.add(btnPanel, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(historyPanel, BorderLayout.EAST);
        historyPanel.setPreferredSize(new Dimension(300, 0));

        setupButtonListeners();
    }

    private void setupPlaceholder() {
        amountField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (amountField.getText().equals(HINT_TEXT)) {
                    amountField.setText("");
                    amountField.setForeground(Color.WHITE);
                    amountField.setFont(new Font("Arial", Font.PLAIN, 18));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (amountField.getText().isEmpty()) {
                    showHint();
                }
            }
        });
    }

    private void showHint() {
        amountField.setText(HINT_TEXT);
        amountField.setForeground(Color.GRAY);
        amountField.setFont(new Font("Arial", Font.ITALIC, 14));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void setupButtonListeners() {
        withdrawBtn.addActionListener(e -> toggleAction("WITHDRAW"));
        depositBtn.addActionListener(e -> toggleAction("DEPOSIT"));
        confirmBtn.addActionListener(e -> handleConfirm());
    }

    /**
     * Toggles between standard view and transaction view.
     * Clicking the same active button triggers a reset to main panel.
     */
    private void toggleAction(String mode) {
        if (currentMode.equals(mode)) {
            resetToInitialState();
        } else {
            currentMode = mode;
            withdrawBtn.setText(mode.equals("WITHDRAW") ? "Cancel" : "Withdraw");
            styleButton(withdrawBtn, new Color(227, 10, 41, 207));
            depositBtn.setVisible(mode.equals("DEPOSIT"));
            confirmBtn.setVisible(true);
            amountField.setVisible(true);
            showHint();
        }
    }

    private void resetToInitialState() {
        currentMode = "NONE";
        withdrawBtn.setVisible(true);
        styleButton(withdrawBtn, new Color(150, 130, 255));
        withdrawBtn.setText("Withdraw");
        depositBtn.setVisible(true);
        confirmBtn.setVisible(false);
        amountField.setVisible(false);
        amountField.setText("");
        balanceLabel.setText("£" + String.format("%.2f", account.getBalance()));
    }

    private void handleConfirm() {
        String input = amountField.getText();
        
        // Prevent action if only placeholder is present
        if (input.equals(HINT_TEXT) || !AmountValidator.isValidAmount(input)) {
            JOptionPane.showMessageDialog(frame, AmountValidator.ERROR_MESSAGE, "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(frame, 
            "Are you sure you want to proceed with this " + currentMode.toLowerCase() + "?", 
            "Confirmation Required", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            double amount = Double.parseDouble(input);
            executeFinalAction(amount);
        }
    }

    private void executeFinalAction(double amount) {
        if (currentMode.equals("DEPOSIT")) {
            new DepositWorker(balanceLabel, account, amountField).execute();
            historyPanel.addRecord(new TransactionRecord("Deposit", amount));
        } else {
            // for withdraw we check balance before we proceed，
            // it will only appear this pop UI when we got enough balance
            if (account.getBalance() >= amount) {
                new WithdrawWorker(balanceLabel, account, amountField).execute();
                historyPanel.addRecord(new TransactionRecord("Withdraw", amount));
            } else {
                // if had insufficient fund trigger work to remind account holder
                new WithdrawWorker(balanceLabel, account, amountField).execute();
            }
        }
        resetToInitialState();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankTransactionSystemGUI().frame.setVisible(true);
        });
    }
}