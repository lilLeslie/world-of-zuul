import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.  Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomStack;
    private List<Item> inventory;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
        roomStack = new Stack<>();
        inventory = new ArrayList<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room carbon, hydrogen, oxygen, nitrogen, sulfur, phosphorus;

        carbon = new Room("Carbon Core", "in the Carbon Core, the dark main deck housing the incubator.");
                hydrogen = new Room("Hydrogen Silo", "in the Hydrogen Silo, a zero-gravity dome filled with explosive fuel.");
        nitrogen = new Room("Nitrogen Vault", "in the Nitrogen Vault, an icy, misty room used for flash-freezing.");
        oxygen = new Room("Oxygen Tank", "in the Oxygen Tank, a bright blue chamber filled with sweet, pressurized air.");
        phosphorus = new Room("Phosphorus Bay", "in the Phosphorus Bay, a pitch-black room where glowing dust coats the walls");
        sulfur = new Room("Sulfur Pit", "in the Sulfur Pit, a hot, yellow tunnel that reeks of rotten eggs");

        hydrogen.setExits("down", carbon);
        carbon.setExits("up", hydrogen);
        carbon.setExits("right", nitrogen);
        nitrogen.setExits("right", oxygen);
        nitrogen.setExits("down", phosphorus);
        nitrogen.setExits("left", carbon);
        oxygen.setExits("down", sulfur);
        oxygen.setExits("left", nitrogen);
        phosphorus.setExits("up", nitrogen);
        phosphorus.setExits("right", sulfur);
        sulfur.setExits("up", oxygen);
        sulfur.setExits("left", phosphorus);

        hydrogen.addItem(new Item("h-canister", "Flammable lifting fuel; critical for organic bonds.", 2));
        nitrogen.addItem(new Item("n-capsule", "Ultra-cold atmospheric buffer.", 3));
        oxygen.addItem(new Item("o-canister", "Highly reactive oxidizer; needed for cellular respiration.", 2));
        phosphorus.addItem(new Item("p-vial", "Glow-in-the-dark compound; genetic backbone.", 1));
        sulfur.addItem(new Item("s-crystal", "Thermal heatsource; protein structural link.", 2));

        currentRoom = hydrogen;
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type " + CommandWord.HELP + " if you need help.");
        printLocationInfo();
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = parser.getEnum(command.getCommandWord());

        switch (commandWord) {
            case HELP -> printHelp();
            case GO -> goRoom(command);
            case LOOK -> look();
            case BACK -> back();
            case QUIT -> wantToQuit = quit(command);
            case UNKNOWN -> System.out.println("I don't know what you mean");
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("up")) {
            nextRoom = currentRoom.getExit("up");
        }
        if (direction.equals("right")) {
            nextRoom = currentRoom.getExit("right");
        }
        if (direction.equals("down")) {
            nextRoom = currentRoom.getExit("down");
        }
        if (direction.equals("left")) {
            nextRoom = currentRoom.getExit("left");
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            roomStack.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /**
     * Print the description of the room.
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Go back to the previous room.
     */
    private void back() {
        if (!roomStack.isEmpty()) {
            currentRoom = roomStack.peek();
            roomStack.pop();
            printLocationInfo();
        } else System.out.println("You cannot go back.");
    }

    /**
     * Check if game is won
     */
    private void synthesize() {
        if (!currentRoom.getName().equals("Carbon Core")){
            System.out.println("There is no DNA Synthesizer here!");
            return;
        }

        if (currentRoom.hasAllItems()){
            System.out.println("The synthesizer hums with blinding light...");
            System.out.println("The CHNOPS elements bond into organic RNA chains.");
            System.out.println("The incubator boots up—life is restored to the Eden-1!");
            System.out.println("\n*** CONGRATULATIONS, YOU WIN! ***");
        } else {
            System.out.println("The synthesizer rejects your sequence: you are still missing elemental reagents.");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }
}
