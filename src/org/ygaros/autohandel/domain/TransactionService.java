package org.ygaros.autohandel.domain;

import org.ygaros.autohandel.VehicleBank;
import org.ygaros.autohandel.model.client.Client;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.player.TransactionEntry;
import org.ygaros.autohandel.model.vehicle.Vehicle;

public interface TransactionService {

    TransactionEntry buyCarFromBank(Player player, Vehicle vehicle, VehicleBank bank);

    TransactionEntry sellCarToClient(Player player, Vehicle vehicle, Client client);

}
