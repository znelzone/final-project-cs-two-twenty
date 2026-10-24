package edu.radford;
import java.io.Serializable;


/**
 * RobloxGame represents a game from the website Roblox.
 * 
 * @author Zackary Nelson, Bradley Patton, Peyton Slusser, and Ulises Royal
 * @version 10-4-2026
 * @since 9-26-2026
 */
public class RobloxGame implements Serializable{

    //RobloxGame attributes

    private String gameName;
    private long universeID;
    private int currentPlayerCount;
    private String genre;
    private long visits;
    private int favorites;
    private int likes;
    private int maxPlayerCount;


    /**
     * Constructs the game with the specified information.
     * 
     * @param gameName the name of this Roblox game
     * @param universeID the universe ID of this Roblox game
     * @param currentPlayerCount the concurent player count of this Roblox game
     * @param genre the genre of this Roblox game
     * @param visits the overall number of times users have visited this Roblox game
     * @param favorites the favorites count of this Roblox game
     * @param likes the count of likes for this Roblox game
     * @param maxPlayerCount the sum of historical player counts for this Roblox game
     */
    public RobloxGame(String gameName, long universeID, int currentPlayerCount, String genre, long visits, int favorites,
        int likes, int maxPlayerCount){
        this.gameName = gameName;
        this.universeID = universeID;
        this.currentPlayerCount = currentPlayerCount;
        this.genre = genre;
        this.visits = visits;
        this.favorites = favorites;
        this.likes = likes;
        this.maxPlayerCount = maxPlayerCount;

    }

    //getters for robloxGame
    
    /**
     * Creates and returns a string represntation of this Roblox game.
     * 
     * @return a string representation of this Roblox game
     * @since 9-26-2026
     */
    public String getInfo(){
        return "Game Name: " + gameName +
                "\nGenre: " + genre +
                "\nCurrent Player Count: " + currentPlayerCount +
                "\nVisits: " + visits +
                "\nFavorites: " + favorites +
                "\nLikes: " + likes;
                //might need to add max player count ie the historical player counts
    }


    /**
     * Returns a string representation of this game.
     * 
     * @return the name of this game.
     * @since 9-26-2026
     */
    public String getGameName(){
        return gameName;
    }

    /**
     * Returns a long that represents the UnivereseID of this game.
     * 
     * @return a long representing the UniverseID of this game
     * @since 9-26-2026
     */
    public long getUniverseID(){
        return universeID;
    }

    /**
     * Returns an integer count of this games Concurrent Players.
     * 
     * @return an integer count of this games Concurrent Players
     * @since 9-26-2026
     */
    public int getCurrentPlayerCount(){
        return currentPlayerCount;
    }

    /**
     * Returns a String containing the genre of this game.
     * 
     * @return a String containing the genre of this game
     * @since 9-26-2026
     */
    public String getGenre(){
        return genre;
    }

    /**
     * Returns a long that represents the number of total visits this
     * game has received.
     * 
     * @return a long representing total visits
     * @since 9-26-2026
     */
    public long getVisits(){
        return visits;
    }
    
    /**
     * Returns a integer representing the total number of favorites this
     * game has received.
     * 
     * @return int representation of total favoites
     * @since 9-26-2026
     */
    public int getFavorites(){
        return favorites;
    }

    /**
     * Returns an integer representing the total number of likes this game
     * has received.
     * 
     * @return int representation of total likes
     * @since 9-26-2026
     */
    public int getLikes(){
        return likes;
    }

    /**
     * Returns an integer representing the historical total player count for
     * this game
     * 
     * @return int representing historical sum of overall total player count
     * @since 9-26-2026
     */
    public int getMaxPlayerCount(){
        return maxPlayerCount;
    }

    
    //setters for RobloxGame

    /**
     * Sets the current number of this games players when called.
     * 
     * @param currentPlayerCount represents the current number of players
     * @since 9-26-2026
     */
    public void setCurrentPlayerCount(int currentPlayerCount){
        this.currentPlayerCount = currentPlayerCount;
    }

    //set's favorites count when calle
    /**
     * Sets the number of this games favorites when called.
     * 
     * @param favorites represents the current number of favorites
     * @since 9-26-2026
     */
    public void setFavorites(int favorites){
        this.favorites = favorites;
    }
    
    /**
     * Sets the name of this RobloxGame.
     * 
     * @param gameName represents the name of this RobloxGame
     * @since 9-26-2026
     */
    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    /**
     * Sets the universeID for this game.
     * 
     * @param universeID represents the universeID for this game
     * @since 9-26-2026
     */
    public void setUniverseID(long universeID){
        this.universeID = universeID;
    }

    /**
     * Sets the genre for this game.
     * 
     * @param genre represents the genre for this game
     * @since 9-26-2026
     */
    public void setGenre(String genre){
        this.genre = genre;
    }

    /**
     * Sets the like count for this game when called.
     * 
     * @param likes represents the number of likes for this game
     * @since 9-26-2026S
     */
    public void setLikes(int likes){
        this.likes = likes;
    }

    /**
     * Sets the historical maxPlayerCount to currentPlayerCount if
     * CurrentPlayerCount is larger than maxPlayerCount
     * 
     * @param maxPlayerCount represents the highest recorded player count
     * @param currentPlayerCount represents the current recorded player count
     * @since 9-26-2026
     */
    public void updateMaxPlayerCount(int maxPlayerCount, int currentPlayerCount){
        if(currentPlayerCount > maxPlayerCount){
            this.maxPlayerCount = currentPlayerCount;
        } 
        this.maxPlayerCount = maxPlayerCount;
    }

    /**
     * Sets the overall historical number of visits to this game
     * 
     * @param visits represents the overall number of visits to this game
     * @since 9-26-2026
     */
    public void setVisits(long visits){
        this.visits = visits;
    }


}
