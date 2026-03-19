package io.github.softeng_g8.software_evolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private BankAccount account;
    private final double INITIAL_BALANCE = 1000.00;

    @BeforeEach
    void setUp() {
        account = new BankAccount(INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Deposit should increment balance correctly")
    void testDeposit() {
        account.deposit(500.50);
        assertEquals(1500.50, account.getBalance(), 0.001, "Balance should reflect the deposit.");
    }

    @Test
    @DisplayName("Withdraw should return true and decrement balance if funds are sufficient")
    void testWithdrawSuccess() {
        boolean result = account.withdraw(400.00);
        assertTrue(result, "Withdrawal should be successful.");
        assertEquals(600.00, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdraw should return false and keep balance intact if funds are insufficient")
    void testWithdrawFailure() {
        boolean result = account.withdraw(2000.00);
        assertFalse(result, "Withdrawal should fail due to insufficient funds.");
        assertEquals(INITIAL_BALANCE, account.getBalance(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "10.5", "10.55", "0.01", "1000000"})
    @DisplayName("Validator should accept valid currency formats")
    void testValidAmountFormats(String input) {
        assertTrue(AmountValidator.isValidAmount(input), "Should accept: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-10", "10.555", "abc", "10.5.5", "", " ", "1,000"})
    @DisplayName("Validator should reject invalid formats")
    void testInvalidAmountFormats(String input) {
        assertFalse(AmountValidator.isValidAmount(input), "Should reject: " + input);
    }

    @Test
    @DisplayName("TransactionRecord should capture data accurately")
    void testTransactionRecord() {
        TransactionRecord record = new TransactionRecord("Deposit", 250.00);
        assertEquals("Deposit", record.getType());
        assertEquals(250.00, record.getAmount());
        assertNotNull(record.getTimestamp(), "Timestamp should be generated upon instantiation.");
    }
}