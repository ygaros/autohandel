package org.ygaros.autohandel.model.advertisement;

public enum AdvertisementType {
    PAPER(550d),
    INTERNET(200d);

    private final double cost;

    AdvertisementType(double cost){
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
