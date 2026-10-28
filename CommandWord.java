/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public enum CommandWord{
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    LOOK("look"),
    BACK("back"),
    SYNTHESIZE("synthesize"),
    UNKNOWN("?");

    private String commandString;

    CommandWord(String commandString){
        this.commandString = commandString;
    }

    @Override
    public String toString(){
        return commandString;
    }
}
