package org.ygaros.autohandel.domain;

import org.ygaros.autohandel.model.vehicle.VehiclePartType;

import java.util.HashMap;
import java.util.Map;

import static org.ygaros.autohandel.util.Generator.BRANDS_MULTIPLIER;

public class FixPriceByBrand {

    private final Map<VehiclePartType, Double> partToFixPrice;

    public FixPriceByBrand(String brand) {
        VehiclePartType[] all =VehiclePartType.values();
        Map<VehiclePartType, Double> map = new HashMap<>(all.length);
        for (VehiclePartType vehiclePartType : all) {
            map.put(vehiclePartType, vehiclePartType.getPriceToFix() * BRANDS_MULTIPLIER.get(brand));
        }
        this.partToFixPrice = map;
    }

    public double getPrice(VehiclePartType vehiclePartType){
        return this.partToFixPrice.get(vehiclePartType);
    }
}
