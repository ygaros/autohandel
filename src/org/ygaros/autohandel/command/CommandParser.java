package org.ygaros.autohandel.command;

import org.ygaros.autohandel.Context;
import org.ygaros.autohandel.exception.CommandParseException;
import org.ygaros.autohandel.model.vehicle.VehiclePartType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.ygaros.autohandel.MechanicBank.MECHANIC_NAMES;

public class CommandParser {
    private static final List<String> COMMANDS =
            List.of(
                    "start", "exit", "list",
                    "buy", "sell", "fix", "show"
            );
    private static final String PARAM_SEPARATOR = " ";
    public static Command parse(String line){
        try{
            String[] splited = line.split(PARAM_SEPARATOR);
            List<String> params = new ArrayList<>(splited.length);
            if(splited.length > 1){
                params.addAll(Arrays.asList(splited).subList(1, splited.length));
                return new Command(splited[0], params);
            }else if(splited.length == 0){
                throw new CommandParseException(line);
            }
            return new Command(splited[0]);
        }catch (Exception e){
            throw new CommandParseException(line);
        }
    }
    public static boolean validateBuyCommand(Command command){
        try {
            return
                    command.getCommand().equalsIgnoreCase(COMMANDS.get(3)) &&
                    command.getParams().size() == 2 &&
                    (
                            command.getParams().get(0).equals("vehicle") ||
                            command.getParams().get(0).equals("ad")
                    ) &&
                    (
                            command.getParams().get(1).equals("paper") ||
                            command.getParams().get(1).equals("internet") ||
                            Integer.parseInt(command.getParams().get(1)) > 0
                    );
        }catch (Exception ignored){
            return false;
        }
    }
    public static boolean validateListCommand(Command command){
        return
                command.getCommand().equalsIgnoreCase(COMMANDS.get(2)) &&
                command.getParams().size() == 1 &&
                (
                    command.getParams().get(0).equalsIgnoreCase("clients")||
                    command.getParams().get(0).equalsIgnoreCase("garage")||
                    command.getParams().get(0).equalsIgnoreCase("vehicle-bank")
                );
    }
    public static boolean validateSellCommand(Command command){
        try {
            return
                    command.getCommand().equalsIgnoreCase(COMMANDS.get(4)) &&
                            command.getParams().size() == 3 &&
                            (
                                    Integer.parseInt(command.getParams().get(0)) > 0 &&
                                    Integer.parseInt(command.getParams().get(0)) < Context.instance().getVehicleBank().getAvailableVehicles().size()
                            )&&
                            command.getParams().get(1).equalsIgnoreCase("to") &&
                            Integer.parseInt(command.getParams().get(2)) > 0;
        }catch (Exception ignored){
            return false;
        }
    }
    public static boolean validateFixCommand(Command command){
        try{
            return
                    command.getCommand().equalsIgnoreCase(COMMANDS.get(5)) &&
                    command.getParams().size() == 4 &&
                    Arrays.stream(MECHANIC_NAMES).anyMatch(m -> m.equalsIgnoreCase(command.getParams().get(0))) &&
                    Integer.parseInt(command.getParams().get(1)) > 0 &&
                    command.getParams().get(2).equalsIgnoreCase("part") &&
                    (
                        Arrays.stream(VehiclePartType.values()).anyMatch(p -> p.toString().equalsIgnoreCase(command.getParams().get(3))) ||
                            (
                                Integer.parseInt(command.getParams().get(3)) > 0 &&
                                Integer.parseInt(command.getParams().get(3)) <= VehiclePartType.values().length
                            )
                    );
        }catch (Exception ignored){
            return false;
        }
    }
    public static boolean validateShowCommand(Command command){
        try{
            if(command.getCommand().equalsIgnoreCase(COMMANDS.get(6))){
                if(
                    command.getParams().size() == 1 &&
                        (
                            command.getParams().get(0).equalsIgnoreCase("balance") ||
                            command.getParams().get(0).equalsIgnoreCase("transaction-history")
                        )

                ){
                  return true;
                }else if(
                    command.getParams().size() == 2 &&
                        (
                            command.getParams().get(0).equalsIgnoreCase("vehicle-history") ||
                            command.getParams().get(0).equalsIgnoreCase("summary")
                        )
                ){
                    Integer.parseInt(command.getParams().get(1));
                    return true;
                }
            }
            return false;
        }catch (Exception ignored){
            return false;
        }
    }
}
