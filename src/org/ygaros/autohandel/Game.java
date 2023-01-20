package org.ygaros.autohandel;

import org.ygaros.autohandel.command.Command;
import org.ygaros.autohandel.command.CommandLineHandler;
import org.ygaros.autohandel.command.CommandParser;
import org.ygaros.autohandel.model.advertisement.AdvertisementType;
import org.ygaros.autohandel.model.client.Client;
import org.ygaros.autohandel.model.mechanic.Mechanic;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.player.TransactionEntry;
import org.ygaros.autohandel.model.player.TransactionType;
import org.ygaros.autohandel.model.vehicle.FixEntry;
import org.ygaros.autohandel.model.vehicle.Vehicle;
import org.ygaros.autohandel.model.vehicle.VehiclePartType;
import org.ygaros.autohandel.util.Generator;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private List<Double> finishLine;
    private static Game instance;

    private static int playerTurn = 0;
    private final Generator gen;

    private final CommandLineHandler handler;
    private Game(){
        this.handler = new CommandLineHandler(System.in, System.out);
        this.gen = new Generator();
    }

    public static Game instance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }
    public void start(){
        this.handler.printWelcomeMessage();
        this.handler.waitForCorrectCommand(Command.START);
        List<Player> players = this.handler.getPlayersNames();
        Context.instance().addAll(players);
        this.finishLine = new ArrayList<>(players.size());
        players.forEach(p -> this.finishLine.add(p.getBalance() * 2));
        Context.instance().getVehicleBank().addAll(this.gen.generateStartGameVehicles());
        Context.instance().getClientBank().addAll(this.gen.generateStartGameClients());
        this.handler.printHelpMessage();
        gameLoop();
    }
    public void gameLoop(){
        while(true){
            if(playerTurn >= Context.instance().getPlayers().size()){
                playerTurn = 0;
            }
            Player player = Context.instance().getPlayers().get(playerTurn);
            this.handler.printRoundInfo(player);
            Command command = this.handler.getNextCommand();
            if(command.equals(Command.BUY)){
                if(CommandParser.validateBuyCommand(command)){
                    String commandParam = command.getParams().get(1);
                    TransactionEntry entry = switch(command.getParams().get(0)){
                        case "ad" -> Context.instance()
                                        .getAdvertisementService()
                                        .buyAd(AdvertisementType.valueOf(commandParam.toUpperCase()), player);
                        case "vehicle" -> Context.instance()
                                          .getTransactionService()
                                          .buyCarFromBank(
                                            player,
                                            Context.instance().getVehicleBank().get(Integer.parseInt(commandParam) - 1),
                                            Context.instance().getVehicleBank()
                                          );
                        default -> null;
                    };

                    if(entry != null){
                        if(entry.getType().equals(TransactionType.BUY_PAPER_ADVERTISEMENT)){
                            for (int i = 0; i < ThreadLocalRandom.current().nextInt(4) + 2; i++) {
                                Context.instance().getClientBank()
                                        .addClient(this.gen.generateNewClient());
                            }
                        }else if(entry.getType().equals(TransactionType.BUY_INTERNET_ADVERTISEMENT)){
                            Context.instance().getClientBank()
                                    .addClient(this.gen.generateNewClient());
                        }
                        player.addToHistory(entry);
                        this.handler.printMessage(entry);
                    }
                    playerTurn++;
                }else{
                    this.handler.printCommandErrorMessage(command);
                }
            }
            else if(command.equals(Command.LIST)){
                if(CommandParser.validateListCommand(command)){
                    String commandParam = command.getParams().get(0);
                    List<?> listToPrint = switch (commandParam){
                        case "clients" -> Context.instance().getClientBank().getAvailableClients();
                        case "garage" -> player.getGarage();
                        case "vehicle-bank" -> Context.instance().getVehicleBank().getAvailableVehicles();
                        default -> Collections.emptyList();
                    };
                    this.handler.printList(listToPrint);
                }else{
                    this.handler.printCommandErrorMessage(command);
                }
            }
            else if(command.equals(Command.SELL)){
                if(CommandParser.validateSellCommand(command)) {
                    int vehicleToSell = Integer.parseInt(command.getParams().get(0)) - 1;
                    Vehicle vehicle = player.getGarage().get(vehicleToSell);
                    Client client = Context.instance().getClientBank().getAvailableClients().get(
                            Integer.parseInt(command.getParams().get(2)) - 1
                    );
                    TransactionEntry entry = Context.instance().getTransactionService()
                            .sellCarToClient(
                                    player,
                                    vehicle,
                                    client
                            );
                    player.addToHistory(entry);
                    this.handler.printMessage(entry);
                    if(entry.getType().equals(TransactionType.SELL_VEHICLE)){
                        Context.instance().getClientBank()
                            .addAll(
                                List.of(
                                    this.gen.generateNewClient(),
                                    this.gen.generateNewClient()
                                )
                            );
                    }
                    playerTurn++;
                }else{
                    this.handler.printCommandErrorMessage(command);
                }
            }
            else if(command.equals(Command.FIX)){
                if(CommandParser.validateFixCommand(command)){
                    Mechanic mechanic = Context.instance().getMechanicBank()
                            .getByName(command.getParams().get(0));
                    Vehicle vehicle = player.getGarage().get(
                            Integer.parseInt(command.getParams().get(1)) - 1
                    );
                    VehiclePartType part;
                    try{
                        part = VehiclePartType.values()[
                                Integer.parseInt(command.getParams().get(3)) - 1
                        ];
                    }catch (NumberFormatException ignored){
                        part = VehiclePartType.valueOf(command.getParams().get(3));
                    }
                    FixEntry entry = Context.instance().getFixService()
                            .fix(
                                mechanic,
                                vehicle,
                                part,
                                player
                            );
                    vehicle.addToHistory(entry);
                    this.handler.printMessage(entry);
                    playerTurn++;
                }else{
                    this.handler.printCommandErrorMessage(command);
                }
            }
            else if(command.equals(Command.SHOW)){
                if(CommandParser.validateShowCommand(command)) {
                    switch (command.getParams().get(0)){
                        case "balance":
                            this.handler.printBalance(player.getBalance());
                            break;
                        case "transaction-history":
                            this.handler.printList(player.getHistory());
                            break;
                        case "vehicle-history":
                            this.handler.printList(player.getGarage()
                                    .get(Integer.parseInt(command.getParams().get(1)) - 1)
                                    .getHistory()
                            );
                            break;
                        case "summary":
                            this.handler.printSummary(player.getGarage()
                                    .get(Integer.parseInt(command.getParams().get(1)) - 1)
                                    .getSummary()
                            );
                            break;
                    }
                }else{
                    this.handler.printCommandErrorMessage(command);
                }
            }else{
                this.handler.printCommandErrorMessage(command);
            }

        }
    }
}
