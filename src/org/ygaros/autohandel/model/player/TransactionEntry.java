package org.ygaros.autohandel.model.player;

import java.time.LocalDateTime;

public class TransactionEntry {
    private final LocalDateTime date;

    private final double price;

    private final TransactionType type;

    public TransactionEntry(double price, TransactionType type) {
        this.date = LocalDateTime.now();
        this.price = price;
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TransactionEntry{" +
                "date=" + date +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}

