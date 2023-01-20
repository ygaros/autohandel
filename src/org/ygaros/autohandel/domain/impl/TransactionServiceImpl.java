package org.ygaros.autohandel.domain.impl;

import org.ygaros.autohandel.VehicleBank;
import org.ygaros.autohandel.domain.TransactionService;
import org.ygaros.autohandel.model.client.Client;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.player.TransactionEntry;
import org.ygaros.autohandel.model.player.TransactionType;
import org.ygaros.autohandel.model.vehicle.Car;
import org.ygaros.autohandel.model.vehicle.Motorcycle;
import org.ygaros.autohandel.model.vehicle.Vehicle;
import org.ygaros.autohandel.model.vehicle.VehiclePart;
import org.ygaros.autohandel.model.vehicle.VehiclePartType;

import java.awt.desktop.SystemEventListener;
import java.util.Arrays;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private static final double BUYING_TAX = 1.02;
    private static final double SELLING_TAX = .02;

    @Override
    public TransactionEntry buyCarFromBank(Player player, Vehicle vehicle, VehicleBank bank) {
        double vehiclePrice = vehicle.getValue() * BUYING_TAX;
        if(player.removeFromBalance(vehiclePrice)){
            bank.remove(vehicle);
            player.addToGarage(vehicle);
            return new TransactionEntry(vehiclePrice, TransactionType.BUY_VEHICLE);
        }
        return new TransactionEntry(vehiclePrice, TransactionType.NOT_ENOUGH_FUNDS);
    }

    @Override
    public TransactionEntry sellCarToClient(Player player, Vehicle vehicle, Client client) {
        if(
                client.getBalance() >= vehicle.getValue() &&
                this.parseAcceptanceOfBrokenParts(vehicle, client) &&
                this.parseAcceptanceOfBrand(vehicle, client)
        ){
            if(
                    (client.isNeedMotorcycle() && vehicle instanceof Motorcycle) ||
                    (client.isNeedVan() && vehicle instanceof Car && ((Car) vehicle).isVan()) ||
                    (!client.isNeedVan() && !client.isNeedMotorcycle() && vehicle instanceof Car)
            ){
                double value = vehicle.getValue() * SELLING_TAX;
                if(player.removeFromBalance(value)){
                    player.setBalance(player.getBalance() + vehicle.getValue());
                    return new TransactionEntry(vehicle.getValue(), TransactionType.SELL_VEHICLE);
                }
            }
        }
        return new TransactionEntry(vehicle.getValue(), TransactionType.CLIENT_NOT_INTERESTED);
    }
    private boolean parseAcceptanceOfBrokenParts(Vehicle vehicle, Client client){
        List<VehiclePartType> clientParts = Arrays.asList(client.getPartsThatCanBeBroken());
        return Arrays.stream(vehicle.getVehicleParts())
                .filter(v -> !clientParts.contains(v.getType()))
                .allMatch(VehiclePart::isWorking);
    }
    private boolean parseAcceptanceOfBrand(Vehicle vehicle, Client client){
        return Arrays.stream(client.getBrands())
                .anyMatch(b -> vehicle.getBrand().equals(b));
    }
}
