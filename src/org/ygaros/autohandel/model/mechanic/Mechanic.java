package org.ygaros.autohandel.model.mechanic;

public class Mechanic {

    private final double fixingFeeMultiplier;

    private final double fixingChance;

    private final double destroyingChance;

    public Mechanic(double fixingFeeMultiplier, double fixingChance){
        this(fixingFeeMultiplier, fixingChance, 0);
    }
    public Mechanic(double fixingFeeMultiplier, double fixingChance, double destroyingChance) {
        this.fixingFeeMultiplier = fixingFeeMultiplier;
        this.fixingChance = fixingChance;
        this.destroyingChance = destroyingChance;
    }

    public double getFixingFeeMultiplier() {
        return fixingFeeMultiplier;
    }

    public double getDestroyingChance() {
        return destroyingChance;
    }

    public double getFixingChance() {
        return fixingChance;
    }
}
