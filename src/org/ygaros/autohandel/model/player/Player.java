package org.ygaros.autohandel.model.player;

import org.ygaros.autohandel.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.ygaros.autohandel.util.Generator.BALANCE_MAX;
import static org.ygaros.autohandel.util.Generator.BALANCE_MIN;

public class Player {
    private String name;
    private double balance;
    private List<Vehicle> garage;
    private List<TransactionEntry> history;

    public Player(String name){
        this.name = name;
        this.balance = ThreadLocalRandom.current().nextDouble(BALANCE_MAX) + BALANCE_MIN;
        this.garage = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public boolean removeFromBalance(double value){
        if(balance - value > 0){
            this.balance -= value;
            return true;
        }
        return false;
    }
    public void addToGarage(Vehicle vehicle){
        this.garage.add(vehicle);
    }
    public boolean removeFromGarage(Vehicle vehicle){
        return this.garage.remove(vehicle);
    }
    public void addToHistory(TransactionEntry entry){
        this.history.add(entry);
    }
    public List<Vehicle> getGarage() {
        return garage;
    }

    public void setGarage(List<Vehicle> garage) {
        this.garage = garage;
    }

    public List<TransactionEntry> getHistory() {
        return history;
    }

    public void setHistory(List<TransactionEntry> history) {
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
