package io.github.softeng_g8.software_evolution;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Dedicated component for the Transaction History sidebar.
 * Encapsulating this prevents the main GUI from becoming too complex.
 */
public class TransactionHistoryPanel extends JPanel {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> historyList = new JList<>(listModel);
    private final LinkedList<TransactionRecord> records = new LinkedList<>();
    private static final int MAX_RECORDS = 10;

    public TransactionHistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(44, 44, 52)); // Dark theme sidebar
        setBorder(BorderFactory.createTitledBorder(null, "Transaction History", 0, 0, null, Color.WHITE));

        historyList.setBackground(new Color(44, 44, 52));
        historyList.setForeground(Color.WHITE);
        // Use HTML rendering for multi-line list items and specific colours
        add(new JScrollPane(historyList), BorderLayout.CENTER);
    }

    /**
     * Adds a new record, ensuring a maximum of 10 items are displayed.
     */
    public void addRecord(TransactionRecord record) {
        if (records.size() >= MAX_RECORDS) {
            records.removeLast();
            listModel.remove(listModel.size() - 1);
        }
        records.addFirst(record);

        String colour = record.getType().equals("Deposit") ? "#99ff99" : "#ff9999";
        String symbol = record.getType().equals("Deposit") ? "+" : "-";

        // HTML styling for the requirement: Amount on top, date below
        String entry = String.format(
            "<html><div style='width:180px; margin-bottom:5px;'>" +
            "<b style='color:%s;'>%s</b> <span style='float:right; color:%s;'>%s £%.2f</span><br>" +
            "<small style='color:#aaaaaa;'>%s</small></div></html>",
            colour, record.getType(), colour, symbol, record.getAmount(), record.getTimestamp()
        );
        
        listModel.add(0, entry);
    }
}