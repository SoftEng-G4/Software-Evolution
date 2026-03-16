package io.github.softeng_g8.software_evolution;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Data class representing a single financial transaction.
 *  British standard: Uses dd/MM/yyyy for date formatting.
 */
public class TransactionRecord {
    private final String type;
    private final double amount;
    private final String timestamp;

    public TransactionRecord(String type, double amount) {
        this.type = type;
        this.amount = amount;
        // Format: 16/03/2026 14:30
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getTimestamp() { return timestamp; }
}