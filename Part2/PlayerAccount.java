// PlayerAccount.java - Model class

import java.util.Objects;

public class PlayerAccount {
    private static final double MINIMUM_BALANCE = 0.0;
    private final String accountHolderName;
    private double currentBalance;

    public PlayerAccount(String accountHolderName, double initialBalance) {
        validateInitialBalance(initialBalance);
        this.accountHolderName = Objects.requireNonNull(accountHolderName, "Account holder name cannot be null");
        this.currentBalance = initialBalance;
    }

    private void validateInitialBalance(double balance) {
        if (balance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
    }

    public synchronized void deposit(double amount) {
        validateTransactionAmount(amount);
        currentBalance += amount;
    }

    public synchronized void withdraw(double amount) {
        validateTransactionAmount(amount);
        validateSufficientFunds(amount);
        currentBalance -= amount;
    }

    private void validateTransactionAmount(double amount) {
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
    }

    private void validateSufficientFunds(double amount) {
        if (amount > currentBalance) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        }
    }

    public synchronized double getBalance() {
        return currentBalance;
    }

    public String getHolderName() {
        return accountHolderName;
    }

    @Override
    public String toString() {
        return String.format("Account[holder=%s, balance=%.2f]", accountHolderName, currentBalance);
    }
}