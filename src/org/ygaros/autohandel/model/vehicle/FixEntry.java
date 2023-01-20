package org.ygaros.autohandel.model.vehicle;

import java.time.LocalDateTime;

public class FixEntry {

    private final LocalDateTime date;

    private final double price;

    private final Vehicle vehicle;

    private final FixEntryType type;

    /**
     * If type is FixEntryType.BROKE_ANOTHER_THING
     * part points to part that got broken.
     *
     * If type is FixEntryType.WASH
     * part points to null.
     */
    private final VehiclePartType part;

    public FixEntry(double price, Vehicle vehicle, FixEntryType type, VehiclePartType part) {
        this.date = LocalDateTime.now();
        this.price = price;
        this.vehicle = vehicle;
        this.type = type;
        this.part = part;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public FixEntryType getType() {
        return type;
    }

    public VehiclePartType getPart() {
        return part;
    }

    @Override
    public String toString() {
        return "FixEntry{" +
                "date=" + date +
                ", price=" + price +
                ", vehicle=" + vehicle +
                ", type=" + type +
                ", part=" + part +
                '}';
    }
}
