package io.github.softeng_g8.software_evolution;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    // TODO: Make it so that you must `lock` the entire account before performing transactions
    private double balance;
    // Using `ReentrantLock` to lock operations on a bank account to prevent race conditions
    private Lock lock = new ReentrantLock(true);

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized methods for thread-safe operations
    public void deposit(double amount) {
        lock.lock();
        balance += amount;
        System.out.println("Deposited: " + amount + " | Current Balance: " + balance);
        lock.unlock();
    }

    public void withdraw(double amount) {
        lock.lock();
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | Current Balance: " + balance);
        } else {
            System.out.println("Insufficient funds.");
        }
        lock.unlock();
    }

    public double getBalance() {
        return balance;
    }
}
