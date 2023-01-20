package org.ygaros.autohandel.domain.impl;

import org.ygaros.autohandel.domain.FixPriceByBrand;
import org.ygaros.autohandel.domain.FixService;
import org.ygaros.autohandel.model.mechanic.Mechanic;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.vehicle.Car;
import org.ygaros.autohandel.model.vehicle.FixEntry;
import org.ygaros.autohandel.model.vehicle.FixEntryType;
import org.ygaros.autohandel.model.vehicle.Vehicle;
import org.ygaros.autohandel.model.vehicle.VehiclePart;
import org.ygaros.autohandel.model.vehicle.VehiclePartType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.ygaros.autohandel.util.Generator.AVAILABLE_BRANDS;
import static org.ygaros.autohandel.util.Generator.BRANDS_MULTIPLIER;

public class FixServiceImpl implements FixService {

    private static final double WASHING_PRICE = 250d;

    private final Map<String, FixPriceByBrand> fixPricesPerBrand;

    public FixServiceImpl() {
        Map<String, FixPriceByBrand> map = new HashMap<>(AVAILABLE_BRANDS.length);
        for (String availableBrand : AVAILABLE_BRANDS) {
            map.put(availableBrand, new FixPriceByBrand(availableBrand));
        }
        this.fixPricesPerBrand = map;
    }

    @Override
    public double calculateFixingPrice(
            Mechanic mechanic,
            String brand,
            VehiclePartType vehiclePartType
    ) {
        return this.fixPricesPerBrand.get(brand).getPrice(vehiclePartType) * mechanic.getFixingFeeMultiplier();

    }

    @Override
    public double calculateWashingPrice(Vehicle vehicle) {
        return WASHING_PRICE * BRANDS_MULTIPLIER.get(vehicle.getBrand());
    }

    @Override
    public FixEntry fix(
            Mechanic mechanic,
            Vehicle vehicle,
            VehiclePartType vehiclePartType,
            Player player
    ) {
        double fee =
                this.fixPricesPerBrand.get(vehicle.getBrand()).getPrice(vehiclePartType)
                        *  mechanic.getFixingFeeMultiplier();
        if(player.removeFromBalance(fee)){
            if(mechanic.getFixingChance() > ThreadLocalRandom.current().nextDouble()){
                Arrays.stream(vehicle.getVehicleParts())
                        .filter(v -> v.getType().equals(vehiclePartType))
                        .findFirst()
                        .ifPresent(v -> {
                            v.fix();
                            vehicle.setValue(vehicle.getValue() + (vehicle.getValue() * (vehiclePartType.getValueIncreaseInPercentages() / 100.0)));
                        });
                return new FixEntry(fee, vehicle, FixEntryType.SUCCESSFUL, vehiclePartType);
            }else{
                if(mechanic.getDestroyingChance() > ThreadLocalRandom.current().nextDouble()){
                    int index;
                    VehiclePart part;
                    do{
                        index = ThreadLocalRandom.current().nextInt(VehiclePartType.values().length);
                        part = vehicle.getVehicleParts()[index];

                    }while(!part.isWorking());
                   part.setWorking(false);
                    return new FixEntry(fee, vehicle, FixEntryType.BROKE_ANOTHER_THING, part.getType());
                }
            }
        }
        return new FixEntry(fee, vehicle, FixEntryType.FAILURE, vehiclePartType);
    }

    @Override
    public FixEntry wash(Vehicle vehicle, Player player) {
        double fee = this.calculateWashingPrice(vehicle);
        if (player.removeFromBalance(fee)) {
            return new FixEntry(fee, vehicle, FixEntryType.WASH, null);
        }
        return new FixEntry(fee, vehicle, FixEntryType.FAILURE, null);
    }
}
