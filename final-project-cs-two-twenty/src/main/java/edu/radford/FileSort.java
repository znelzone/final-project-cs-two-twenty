package edu.radford;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * FileSort contains methods for sorting Roblox games
 * based on their statistics retrieved from the Roblox API
 * 
 * @author Peyton Slusser
 * @version 10-25-2026
 * @since 10-25-2026
 */
public class FileSort {

    /**
     * Sorts a collection of Roblox games by their current
     * player count in descending order
     * Creates a new ArrayList so that the oroginal collection
     * doesnt change
     * 
     * @param collection the collection of Roblox games to sort
     * @return an ArrayList of Roblox games sorted by player count
     * @since 10-25-2026
     */
    
    public ArrayList<RobloxGame> sortByPlaters(RobloxGameCollection collection) {
        //creates arraylist
        ArrayList<RobloxGame> games = new ArrayList<>();

        //copies each robloxgame
        for (RobloxGame game : collection) {
            games.add(game);
        }

        games.sort(
            //compares player counts
            Comparator.comparingInt(
                RobloxGame::getCurrentPlayerCount
            
            //highest to lowest by reversing
            ).reversed()
        );
        //returns sorted list
        return games;
    }
}
