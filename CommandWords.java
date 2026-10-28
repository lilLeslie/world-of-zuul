import java.util.HashMap;
import java.util.Set;

public class CommandWords {
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        validCommands = new HashMap<String, CommandWord>();
        for (CommandWord command : CommandWord.values()){
            if (command != CommandWord.UNKNOWN){
                validCommands.put(command.toString(), command);
            }
        }

    }

    /**
     * Print all valid commands
     */

    public String showAll() {
        String returnString = "";
        Set<String> keys = validCommands.keySet();
        for (String command : keys) {
            returnString += command + " ";
        }
        return returnString;
    }

    /**
     * Check whether a given String is a valid command word.
     *
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString) {
        return validCommands.get(aString) != null;
    }

    public CommandWord getEnum(String command){
        return validCommands.get(command);
    }
}
