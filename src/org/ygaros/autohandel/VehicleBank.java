package org.ygaros.autohandel;

import org.ygaros.autohandel.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleBank {

    private final List<Vehicle> availableVehicles;

    public VehicleBank() {
        this.availableVehicles = new ArrayList<>();
    }

    public Vehicle get(int index){
        return this.availableVehicles.get(index);
    }
    public boolean remove(Vehicle vehicle){
        return this.availableVehicles.remove(vehicle);
    }

    public List<Vehicle> getAvailableVehicles() {
        return availableVehicles;
    }

    public void addAll(List<Vehicle> vehicles){
        this.availableVehicles.addAll(vehicles);
    }
    public void addVehicle(Vehicle vehicle){
        this.availableVehicles.add(vehicle);
    }
}
