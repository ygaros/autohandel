package org.ygaros.autohandel.model.vehicle;

public enum VehiclePartType {
    BREAKS(10, 1_500d),
    SUSPENSION(20, 7_000d),
    ENGINE(100, 15_000d),
    BODY(50, 12_000d),
    GEARBOX(50, 11_000d);

    private final int valueIncreaseInPercentages;
    private final double priceToFix;


    VehiclePartType(int valueIncreaseInPercentages, double priceToFix){
        this.valueIncreaseInPercentages = valueIncreaseInPercentages;
        this.priceToFix = priceToFix;
    }

    public int getValueIncreaseInPercentages() {
        return valueIncreaseInPercentages;
    }

    public double getPriceToFix() {
        return priceToFix;
    }
}
