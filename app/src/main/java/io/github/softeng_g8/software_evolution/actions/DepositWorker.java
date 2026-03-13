package io.github.softeng_g8.software_evolution.actions;

import io.github.softeng_g8.software_evolution.BankAccount;

import javax.swing.*;

public class DepositWorker extends SwingWorker<Void, Void> {
    private JLabel balanceLabel;
    private BankAccount account;
    private JTextField amountText;

    // Set the no parameter constructor to private
    private DepositWorker() {
        super();
    }

    // Accepts the bank account to operate on, as well as the amount of money.
    public DepositWorker(JLabel balanceLabel, BankAccount account, JTextField amount) {
        super();
        this.balanceLabel = balanceLabel;
        this.account = account;
        this.amountText = amount;
    }

    @Override
    protected Void doInBackground() throws Exception {
        try {
            double amount = Double.parseDouble(amountText.getText());
            account.deposit(amount);
        } catch (NumberFormatException e) {}
        return null;
    }

    @Override
    protected void done() {
        amountText.setText("");
        balanceLabel.setText("Balance: " + account.getBalance());
    }
}
