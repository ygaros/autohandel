package org.ygaros.autohandel;

import org.ygaros.autohandel.domain.AdvertisementService;
import org.ygaros.autohandel.domain.FixService;
import org.ygaros.autohandel.domain.TransactionService;
import org.ygaros.autohandel.domain.impl.AdvertisementServiceImpl;
import org.ygaros.autohandel.domain.impl.FixServiceImpl;
import org.ygaros.autohandel.domain.impl.TransactionServiceImpl;
import org.ygaros.autohandel.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private static Context instance;
    private final VehicleBank vehicleBank;
    private final ClientBank clientBank;
    private final MechanicBank mechanicBank;

    private final FixService fixService;

    private final TransactionService transactionService;

    private final AdvertisementService advertisementService;

    private final List<Player> players;



    private int roundCount;

    private Context(){
        this.vehicleBank = new VehicleBank();
        this.clientBank = new ClientBank();
        this.mechanicBank = new MechanicBank();
        this.players = new ArrayList<>();
        this.fixService = new FixServiceImpl();
        this.transactionService = new TransactionServiceImpl();
        this.advertisementService = new AdvertisementServiceImpl();
        roundCount = 0;
    }
    public static Context instance(){
        if(instance == null){
            instance = new Context();
        }
        return instance;
    }

    public VehicleBank getVehicleBank() {
        return vehicleBank;
    }

    public ClientBank getClientBank() {
        return clientBank;
    }

    public MechanicBank getMechanicBank() {
        return mechanicBank;
    }

    public FixService getFixService() {
        return fixService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public AdvertisementService getAdvertisementService() {
        return advertisementService;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void add(Player player){
        this.players.add(player);
    }
    public void addAll(List<Player> players){
        this.players.addAll(players);
    }
    public int getRoundCount() {
        return roundCount;
    }
}
