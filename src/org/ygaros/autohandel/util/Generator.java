package org.ygaros.autohandel.util;

import org.ygaros.autohandel.model.client.Client;
import org.ygaros.autohandel.model.vehicle.Car;
import org.ygaros.autohandel.model.vehicle.Motorcycle;
import org.ygaros.autohandel.model.vehicle.Segment;
import org.ygaros.autohandel.model.vehicle.Vehicle;
import org.ygaros.autohandel.model.vehicle.VehiclePart;
import org.ygaros.autohandel.model.vehicle.VehiclePartType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    public static final String[] AVAILABLE_BRANDS =
            new String[]{"Audi", "BMW", "Honda", "Polonez", "Jaguar"};

    private static final double LOW_CHANCE = .85;
    private static final double A_BIT_HIGHER_CHANCE = .65;
    public static final Map<String, Double> BRANDS_MULTIPLIER = Map.of(
            AVAILABLE_BRANDS[0], 1.5d,
            AVAILABLE_BRANDS[1], 1.8d,
            AVAILABLE_BRANDS[2], 1.0d,
            AVAILABLE_BRANDS[3], 0.6d,
            AVAILABLE_BRANDS[4], 2.5d
    );
    public static final double BALANCE_MAX = 150_000d;
    public static final double BALANCE_MIN = 20_000d;
    private static final double VALUE_MAX = 60_000d;
    private static final double VALUE_MIX = 20_000d;
    private static final int MILEAGE_MAX = 250_000;

    private String lastBrand = "";


    public Vehicle generateNewVehicle(){
        double value = this.getValue();
        String brand = this.getBrand();
        value = value * BRANDS_MULTIPLIER.get(brand);
        int mileage = this.getMileage();
        Color color = this.getColor();
        boolean motorcycle = this.getVeryLowChance();
        VehiclePart[] parts = this.getVehicleParts();
        if(motorcycle){
            return new Motorcycle(
                    value,
                    brand,
                    mileage,
                    color,
                    Segment.MOTORCYCLE,
                    parts
            );
        }else{
            return new Car(
                    value,
                    brand,
                    mileage,
                    color,
                    this.getSegment(),
                    parts
            );
        }
    }
    public List<Vehicle> generateStartGameVehicles(){
        int howMany = ThreadLocalRandom.current().nextInt(15) + 5;
        List<Vehicle> vehicles = new ArrayList<>(howMany);
        for (int i = 0; i < howMany; i++) {
            vehicles.add(this.generateNewVehicle());
        }
        return vehicles;
    }
    public List<Client> generateStartGameClients(){
        int howMany = ThreadLocalRandom.current().nextInt(10) + 5;
        List<Client> clients = new ArrayList<>(howMany);
        for (int i = 0; i < howMany; i++) {
            clients.add(this.generateNewClient());
        }
        return clients;
    }

    private VehiclePart[] getVehicleParts(){
        VehiclePartType[] all = VehiclePartType.values();
        VehiclePart[] parts = new VehiclePart[all.length];
        List<VehiclePartType> brokenParts =
                List.of(this.generatePartsThatCanBeBroken(A_BIT_HIGHER_CHANCE));
        for (int i = 0; i < all.length; i++) {
            VehiclePartType current = all[i];
            boolean broken = brokenParts.contains(current);
            VehiclePart part = new VehiclePart(current, broken);
            parts[i] = part;
        }
        return parts;
    }
    private double getValue(){
        return ThreadLocalRandom.current().nextDouble(VALUE_MAX) + VALUE_MIX;
    }
    private int getMileage(){
        return ThreadLocalRandom.current().nextInt(MILEAGE_MAX);
    }

    private Segment getSegment(){
        Segment[] all = Segment.values();
        int index = ThreadLocalRandom.current().nextInt(all.length - 1);
        return all[index];
    }
    private Color getColor(){
        return new Color(
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256)
        );
    }
    private boolean getVeryLowChance(){
        return ThreadLocalRandom.current().nextDouble() > LOW_CHANCE;
    }
    private boolean getABitHigherChange(){
        return ThreadLocalRandom.current().nextDouble() > A_BIT_HIGHER_CHANCE;
    }
    private boolean getCustomChance(double chance){
        return ThreadLocalRandom.current().nextDouble() > chance;
    }
    public Client generateNewClient(){
        double newClientBalance = this.getBalance();
        String[] clientBrands = new String[]{this.getBrand(), this.getBrand()};
        boolean needVan = this.getBoolean();
        boolean motorcycle = false;
        if(!needVan){
            motorcycle = this.getVeryLowChance();
        }
        VehiclePartType[] partsThatCanBeBroken = this.generatePartsThatCanBeBroken(LOW_CHANCE);
        return new Client(
                newClientBalance,
                clientBrands,
                needVan,
                motorcycle,
                partsThatCanBeBroken
        );
    }
    private double getBalance(){
        return ThreadLocalRandom.current().nextDouble(BALANCE_MAX) + BALANCE_MIN;
    }
    private String getBrand(){
        String brand;
        do {
            brand = AVAILABLE_BRANDS[
                    ThreadLocalRandom.current().nextInt(AVAILABLE_BRANDS.length)
                    ];
        } while(brand.equals(this.lastBrand));
        this.lastBrand = brand;
        return brand;
    }
    private boolean getBoolean(){
        return ThreadLocalRandom.current().nextDouble() >= .5;
    }
    private VehiclePartType[] generatePartsThatCanBeBroken(double chance){
        VehiclePartType[] all = VehiclePartType.values();
        List<VehiclePartType> generated = new ArrayList<>(2);
        for (VehiclePartType vehiclePartType : all) {
            if(this.getCustomChance(chance)){
                generated.add(vehiclePartType);
            }
        }
        return generated.toArray(new VehiclePartType[]{});
    }
}
