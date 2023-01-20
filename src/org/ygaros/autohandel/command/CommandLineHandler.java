package org.ygaros.autohandel.command;

import org.ygaros.autohandel.exception.CommandParseException;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.player.TransactionEntry;
import org.ygaros.autohandel.model.vehicle.FixEntry;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandLineHandler {
    private static final List<String> APPROVE = List.of("yes", "Yes", "YES", "y", "Y");
    private static final List<String> DECLINE  = List.of("no", "No", "NO", "n", "N");
    private static final String WELCOME = """
            Hello on car-dealer simulator.
            Try to double your money on the fewest rounds possible.
            To start the game type start.
        """;
    private static final String HELP = """
            Commands available are:
                example command: sell 1 to 1
                That means that you are trying to sell you 1st vehicle to 1st client.
                You can use index or name of given client.
                
                example command: fix adrian 1 part 2
                That means that adrian is trying to fix in your 1st vehicle 2nd part.
                You can use index or name of the part.
                Parts are always in that order:
                    [BREAKS, SUSPENSION, ENGINE, BODY, GEARBOX]
                there are 3 available mechanics:
                    [adrian, marian, janusz]
                    
            other available commands:
                fix adrian 1 part 2
                fix janusz 3 part engine
                fix marian 5 part suspension
                fix janusz 3 part 4
                
                sell 1 to 3
                
                buy vehicle 1
                buy ad paper
                buy ad internet
                
                list clients
                list garage
                list vehicle-bank
                
                show balance
                show transaction-history
                show vehicle-history 1
                show summary 1
            """;

    private final Scanner scanner;
    private final PrintStream printStream;

    public CommandLineHandler(
            InputStream inputStream,
            OutputStream outputStream
    ){
        this.scanner = new Scanner(inputStream);
        this.printStream = new PrintStream(outputStream);
    }
    public Command waitForCorrectCommand(Command command){
        Command typedCommand =  this.getNextCommand();
        while(!typedCommand.equals(command)){
            this.printErrorMessage("wrong command the correct command -> "+command);
            typedCommand = this.getNextCommand();
        }
        return typedCommand;
    }
    public Command getNextCommand() {
        String line;
        line = this.scanner.nextLine();
        if (line.equals(Command.EXIT.getCommand())) {
            System.exit(0);
        }
        Command command = null;
        do {
            try {
                command = CommandParser.parse(line.toLowerCase(Locale.ROOT));
            } catch (CommandParseException e) {
                this.printErrorMessage(e.getMessage());
            }
        } while (command == null);
        return command;
    }
    public int getNextNumber(){
        Integer integer = null;
        do {
            try {
                integer = Integer.parseInt(this.scanner.nextLine().trim());
            } catch (NumberFormatException e){
                this.printErrorMessage("Please input number");
            }
        } while(integer == null);

        return integer;
    }
    public String getNextString(){
        String line;
        do{
            line = this.scanner.nextLine();
        }while(line == null);
        return line;
    }
    public String getNextFlag(){
        String flag;
        do {
            flag = this.scanner.nextLine();
        } while(flag == null);

        return flag;
    }
    public List<Player> getPlayersNames(){
        List<Player> players = new ArrayList<>();
        boolean decision;
        do {
            this.println("Enter player name:");
            String name = this.getNextString();
            Player player = new Player(name);
            players.add(player);
            this.println("Is there another player with you?");
            decision = this.getDecision();
        }while(decision);
        return players;
    }
    public boolean getDecision(){
        this.println("YES or NO (y/n)");
        String command = this.getNextString();
        return APPROVE.contains(command);
    }
    public void printMessage(TransactionEntry entry){
        this.println(String.format("Transaction was %s value %.2f", entry.getType().toString(), entry.getPrice()));
    }
    public void printMessage(FixEntry entry){
        this.println(String.format("Fix was %s value %.2f the vehicle part %s",
                entry.getType().toString(), entry.getPrice(), entry.getPart().toString()));
    }
    public void printBalance(double balance){
        this.println(String.format("You balance is %.2f", balance));
    }
    public void printRoundInfo(Player player){
        this.println(String.format("Now player %s is moving", player.getName()));
    }
    public void printSummary(double summary){
        this.println(String.format("Summary for requested vehicle %.2f", summary));
    }
    public void printErrorMessage(String message){
        this.println("there was an error "+message);
    }
    public void printCommandErrorMessage(Command command){
        this.println(
                String.format(
                        "Command %s is not valid params: %s",
                        command.getCommand(),
                        String.join(" ", command.getParams())
                )
        );
    }

    public void printWelcomeMessage(){
        this.println(WELCOME);
    }

    public void printHelpMessage(){
        this.println(HELP);
    }
    public void println(String string){
        this.printStream.println(string);
    }
    public void printList(List<?> list){
        AtomicInteger counter = new AtomicInteger(1);
        list.forEach(
                a -> this.println(counter.getAndIncrement() + ": " + a.toString())
        );
    }
}
