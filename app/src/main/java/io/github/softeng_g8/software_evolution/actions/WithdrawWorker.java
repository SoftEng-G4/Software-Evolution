package io.github.softeng_g8.software_evolution.actions;

import io.github.softeng_g8.software_evolution.BankAccount;
import io.github.softeng_g8.software_evolution.AmountValidator;
import javax.swing.*;

/**
 * Refactored Background Worker for Withdrawals.
 * * WHY THE CHANGES?
 * 
 * 1. ARCHITECTURAL UNIFORMITY: Both deposit and withdrawal now follow the same 'Worker' pattern. 
 * This makes the code much easier for future developers to maintain and debug.
 * 
 * 2. THREAD SAFETY: Previously, the withdrawal was handled by a manual Thread. 
 * Using SwingWorker ensures that UI updates (like changing the balance label) are handled 
 * 
 * safely on the Event Dispatch Thread (EDT).
 */
public class WithdrawWorker extends SwingWorker<Void, Void> {
    private final JLabel balanceLabel;
    private final BankAccount account;
    private final JTextField amountText;
    private boolean isInputValid = true;

    public WithdrawWorker(JLabel balanceLabel, BankAccount account, JTextField amountText) {
        this.balanceLabel = balanceLabel;
        this.account = account;
        this.amountText = amountText;
    }

    @Override
    protected Void doInBackground() throws Exception {
        String input = amountText.getText();
        if (AmountValidator.isValidAmount(input)) {
            account.withdraw(Double.parseDouble(input));
        } else {
            isInputValid = false;
        }
        return null;
    }

    @Override
    protected void done() {
        if (!isInputValid) {
            JOptionPane.showMessageDialog(null, 
                AmountValidator.ERROR_MESSAGE, 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        amountText.setText("");
        balanceLabel.setText("Balance: " + String.format("%.2f", account.getBalance()));
    }
}