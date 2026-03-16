package io.github.softeng_g8.software_evolution.actions;

import io.github.softeng_g8.software_evolution.BankAccount;
import io.github.softeng_g8.software_evolution.AmountValidator;
import javax.swing.*;

/**
 * Background Worker for Withdrawals with overdrawn protection.
 */
public class WithdrawWorker extends SwingWorker<Void, Void> {
    private final JLabel balanceLabel;
    private final BankAccount account;
    private final JTextField amountText;
    private boolean isInputValid = true;
    private boolean isSufficient = true;

    public WithdrawWorker(JLabel balanceLabel, BankAccount account, JTextField amountText) {
        this.balanceLabel = balanceLabel;
        this.account = account;
        this.amountText = amountText;
    }

    @Override
    protected Void doInBackground() throws Exception {
        String input = amountText.getText();
        if (AmountValidator.isValidAmount(input)) {
            // Check if the account allows the withdrawal
            isSufficient = account.withdraw(Double.parseDouble(input));
        } else {
            isInputValid = false;
        }
        return null;
    }

    @Override
    protected void done() {
        if (!isInputValid) {
            JOptionPane.showMessageDialog(null, AmountValidator.ERROR_MESSAGE, "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isSufficient) {
            // British English warning for insufficient funds
            JOptionPane.showMessageDialog(null, 
                "Transaction Declined: Insufficient funds available in your account.", 
                "Balance Warning", 
                JOptionPane.WARNING_MESSAGE);
        }
        
        amountText.setText("");
        balanceLabel.setText("£" + String.format("%.2f", account.getBalance()));
    }
}