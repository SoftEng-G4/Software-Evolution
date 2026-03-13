package io.github.softeng_g8.software_evolution;

import java.util.regex.Pattern;

/**
 * Utility class to validate financial input.
 * In a modern system, we need centralise validation to ensure consistency 
 * across all modules (Deposits, Withdrawals, etc.).
 */
public class AmountValidator {
    
    public static final String ERROR_MESSAGE = "Invalid input! Please enter a numeric value with up to two decimal places (e.g. 20.00).";
    
    // Regular Expression: Ensures the value is a positive number with 0-2 decimal places.
    private static final String CURRENCY_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";
    private static final Pattern PATTERN = Pattern.compile(CURRENCY_PATTERN);

    /**
     * Checks if the user-provided string is a valid monetary amount.
     * @param input The text from the dialogue box or text field.
     * @return true if the format is correct.
     */
    public static boolean isValidAmount(String input) {
        if (input == null || input.trim().isEmpty()) return false;
        return PATTERN.matcher(input.trim()).matches();
    }
}