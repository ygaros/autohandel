package org.ygaros.autohandel;

import org.ygaros.autohandel.model.mechanic.Mechanic;

import java.util.Locale;

public class MechanicBank {
    public static final String[] MECHANIC_NAMES = new String[]{"marian", "janusz", "adrian"};
    private final Mechanic janusz = new Mechanic(2.0, 1);
    private final Mechanic marian = new Mechanic(1.5, .9);
    private final Mechanic adrian = new Mechanic(1.0, .8, .02);

    public Mechanic getJanusz() {
        return janusz;
    }

    public Mechanic getMarian() {
        return marian;
    }

    public Mechanic getAdrian() {
        return adrian;
    }
    public Mechanic getByName(String name){
        return switch (name.toLowerCase(Locale.ROOT)) {
            case "marian" -> marian;
            case "janusz" -> janusz;
            case "adrian" -> adrian;
            default -> null;
        };
    }
}
