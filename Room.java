import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private List<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     *
     * @param direction The direction of the exit
     * @param exit      The Room in the given direction
     */
    public void setExits(String direction, Room exit) {
        exits.put(direction, exit);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * @return the name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Add items to this room.
     *
     * @param item The item to add to this room
     */
    public void addItem(Item item) {
        items.add(item);
    }

    public String getExitString() {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += exit + " ";
        }
        return returnString;
    }

    public String getItemString() {
        String returnString = "Items: ";
        for (Item item : items) {
            returnString += item.toString() + "\n";
        }
        return returnString;
    }

    public String getLongDescription() {
        return "\nYou are " + name + " - " + description + "\n" + getExitString() + "\n" + getItemString();
    }

    public boolean hasAllItems(){
        return items.size() == 5;
    }

}
