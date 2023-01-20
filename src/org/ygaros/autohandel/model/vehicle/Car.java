package org.ygaros.autohandel.model.vehicle;

import java.awt.Color;
import java.util.List;

public class Car extends Vehicle{

    private boolean isVan;

    public Car(
            double value,
            String brand,
            int mileage,
            Color color,
            Segment segment,
            VehiclePart[] vehicleParts,
            List<FixEntry> history
    ) {
        super(value, brand, mileage, color, segment, vehicleParts, history);
    }

    public Car(
            double value,
            String brand,
            int mileage,
            Color color,
            Segment segment,
            VehiclePart[] vehicleParts
    ) {
        super(value, brand, mileage, color, segment, vehicleParts);
    }


    public boolean isVan() {
        return isVan;
    }

    public void setVan(boolean van) {
        isVan = van;
    }

    @Override
    public String toString() {
        return "Car{" +
                "isVan=" + isVan + ", " +
                super.toString() +
                "} ";
    }
}