package org.ygaros.autohandel.model.vehicle;

import java.awt.*;
import java.util.List;

public class Motorcycle extends Vehicle{

    public Motorcycle(
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

    public Motorcycle(
            double value,
            String brand,
            int mileage,
            Color color,
            Segment segment,
            VehiclePart[] vehicleParts
    ) {
        super(value, brand, mileage, color, segment, vehicleParts);
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                super.toString() +
                "} ";
    }
}
