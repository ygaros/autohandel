package org.ygaros.autohandel.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Command {

    public static final Command EXIT = new Command("exit");
    public static final Command START = new Command("start");

    public static final Command BUY = new Command("buy");

    public static final Command SELL = new Command("sell");

    public static final Command LIST = new Command("list");

    public static final Command FIX = new Command("fix");

    public static final Command SHOW = new Command("show");

    private final  String command;

    private final List<String> params;


    public Command(String command, List<String> params) {
        this.command = command;
        this.params = params;
    }
    public Command(String command){
        this.command = command;
        this.params = new ArrayList<>(0);
    }

    public String getCommand() {
        return command;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command1 = (Command) o;
        return command.equalsIgnoreCase(command1.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
