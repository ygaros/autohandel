package org.ygaros.autohandel.domain;

import org.ygaros.autohandel.model.vehicle.Vehicle;
import org.ygaros.autohandel.model.vehicle.VehiclePartType;
import org.ygaros.autohandel.model.vehicle.FixEntry;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.mechanic.Mechanic;
import org.ygaros.autohandel.model.vehicle.Car;

public interface FixService {

    double calculateFixingPrice(Mechanic mechanic, String brand, VehiclePartType vehiclePartType);

    double calculateWashingPrice(Vehicle vehicle);

    FixEntry fix(Mechanic mechanic, Vehicle vehicle, VehiclePartType vehiclePartType, Player player);

    FixEntry wash(Vehicle vehicle, Player player);
}
