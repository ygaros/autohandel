package org.ygaros.autohandel.model.vehicle;

public class VehiclePart {

    private VehiclePartType type;

    private boolean isWorking;

    public VehiclePart(VehiclePartType type, boolean isWorking) {
        this.type = type;
        this.isWorking = isWorking;
    }

    public void fix(){
        this.isWorking = true;
    }

    public VehiclePartType getType() {
        return type;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public String toString() {
        return "VehiclePart{" +
                "type=" + type +
                ", isWorking=" + isWorking +
                '}';
    }
}
