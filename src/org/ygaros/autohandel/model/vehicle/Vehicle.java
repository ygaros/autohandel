package org.ygaros.autohandel.model.vehicle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Vehicle {
    private double value;
    private String brand;
    private int mileage;
    private Color color;
    private Segment segment;
    private VehiclePart[] vehicleParts;
    private List<FixEntry> history;
    public Vehicle(
            double value,
            String brand,
            int mileage,
            Color color,
            Segment segment,
            VehiclePart[] vehicleParts,
            List<FixEntry> history
    ) {
        this.value = value;
        this.brand = brand;
        this.mileage = mileage;
        this.color = color;
        this.segment = segment;
        this.vehicleParts = vehicleParts;
        this.history = history;
    }
    public Vehicle(
            double value,
            String brand,
            int mileage,
            Color color,
            Segment segment,
            VehiclePart[] vehicleParts
    ) {
        this(
                value, brand, mileage,
                color, segment, vehicleParts,
                new ArrayList<>()
        );
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public VehiclePart[] getVehicleParts() {
        return vehicleParts;
    }

    public void setVehicleParts(VehiclePart[] vehicleParts) {
        this.vehicleParts = vehicleParts;
    }

    public List<FixEntry> getHistory() {
        return history;
    }

    public void addToHistory(FixEntry entry){
        this.history.add(entry);
    }

    public void setHistory(List<FixEntry> history) {
        this.history = history;
    }
    public Double getSummary(){
        return this.history.stream()
                .mapToDouble(FixEntry::getPrice)
                .reduce(0, Double::sum);
    }
    @Override
    public String toString() {
        return "value=" + value +
                ", brand='" + brand + '\'' +
                ", mileage=" + mileage +
                ", color=" + color +
                ", segment=" + segment +
                ", vehicleParts=" + Arrays.toString(vehicleParts) +
                '}';
    }
}
