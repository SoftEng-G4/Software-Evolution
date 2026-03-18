package io.github.softeng_g8.software_evolution.actions;

import io.github.softeng_g8.software_evolution.BankAccount;
import io.github.softeng_g8.software_evolution.AmountValidator;
import javax.swing.*;

/**
 * Background Worker for Deposits.
 * * UI UNIFICATION UPDATE:
 * Removed the "Balance: " prefix to ensure visual consistency with the withdrawal 
 * worker and the latest UI mockups. Now only displays the currency symbol and amount.
 */
public class DepositWorker extends SwingWorker<Void, Void> {
    private final JLabel balanceLabel;
    private final BankAccount account;
    private final JTextField amountText;
    private boolean isInputValid = true;

    public DepositWorker(JLabel balanceLabel, BankAccount account, JTextField amountText) {
        this.balanceLabel = balanceLabel;
        this.account = account;
        this.amountText = amountText;
    }

    @Override
    protected Void doInBackground() throws Exception {
        String input = amountText.getText();
        if (AmountValidator.isValidAmount(input)) {
            account.deposit(Double.parseDouble(input));
        } else {
            isInputValid = false;
        }
        return null;
    }

    @Override
    protected void done() {
        if (!isInputValid) {
            JOptionPane.showMessageDialog(null, AmountValidator.ERROR_MESSAGE, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        amountText.setText("");
        // Standardised display to match the withdraw worker format: £XXXX.XX
        balanceLabel.setText("£" + String.format("%.2f", account.getBalance()));
    }
}