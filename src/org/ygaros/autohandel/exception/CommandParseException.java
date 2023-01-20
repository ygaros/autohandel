package org.ygaros.autohandel.exception;

public class CommandParseException extends RuntimeException{
    public CommandParseException(String command){
        super(String.format("Command %s is not valid try again", command));
    }
}
