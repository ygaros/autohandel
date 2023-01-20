package org.ygaros.autohandel.model.client;

import org.ygaros.autohandel.model.vehicle.VehiclePartType;

import java.util.Arrays;

public class Client {

    private final double balance;

    private final String[] brands;

    private final boolean needVan;
    private final boolean needMotorcycle;

    private final VehiclePartType[] partsThatCanBeBroken;

    public Client(
            double balance,
            String[] brands,
            boolean needVan,
            boolean needMotorcycle,
            VehiclePartType[] partsThatCanBeBroken
    ) {
        this.balance = balance;
        this.brands = brands;
        this.needVan = needVan;
        this.needMotorcycle = needMotorcycle;
        this.partsThatCanBeBroken = partsThatCanBeBroken;
    }

    public double getBalance() {
        return balance;
    }

    public String[] getBrands() {
        return brands;
    }

    public boolean isNeedVan() {
        return needVan;
    }

    public boolean isNeedMotorcycle() {
        return needMotorcycle;
    }

    public VehiclePartType[] getPartsThatCanBeBroken() {
        return partsThatCanBeBroken;
    }

    @Override
    public String toString() {
        return "Client{" +
                "balance=" + balance +
                ", brands=" + Arrays.toString(brands) +
                ", needVan=" + needVan +
                ", needMotorcycle=" + needMotorcycle +
                ", partsThatCanBeBroken=" + Arrays.toString(partsThatCanBeBroken) +
                '}';
    }
}
