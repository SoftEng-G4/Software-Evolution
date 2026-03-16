package io.github.softeng_g8.software_evolution;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BankAccount logic with thread-safety.
 * Updated to return transaction status for UI feedback.
 */
public class BankAccount {
    private double balance;
    private final Lock lock = new ReentrantLock(true);

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Attempts to withdraw funds.
     * @return true if successful, false if insufficient funds.
     */
    public boolean withdraw(double amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            } else {
                return false; // Insufficient funds
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        return balance;
    }
}