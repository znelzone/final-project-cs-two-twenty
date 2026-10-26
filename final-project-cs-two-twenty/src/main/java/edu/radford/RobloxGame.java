package edu.radford;
import java.io.Serializable;


/**
 * RobloxGame represents a game from the website Roblox.
 * 
 * @author Zackary Nelson, Bradley Patton, Peyton Slusser, and Ulises Royal
 * @version 10-26-2026
 * @since 9-26-2026
 */
public class RobloxGame implements Serializable{

    //RobloxGame attributes

    //used to be univers id
    private long id;
    //new data field to hold data from returned json file    
    private long rootPlaceId;
    //used to be game name
    private String name;
    //used to be currentPlayerCount
    private int playing;
    //visit remained the same
    private long visits;
    //genre remained unchanged
    private String genre;
    //used to be favorites
    private int favoritedCount;
    //new data field to hold data from returned json file
    private Boolean isContentRestricted;
    //the upvote count by players
    private int upVotes;
    //the downvote count by players
    private int downVotes;
    //the max count of players in the games past
    private int maxPlaying; 



    


    /**
     * Constructs the game with the specified information.
     * 
     * @param rootPlaceId the root place id // NEW 
     * @param isContentRestricted determines if content is or isn't restricted // NEW 
     * @param name the name of this Roblox game
     * @param id the universe ID of this Roblox game
     * @param playing the concurent player count of this Roblox game
     * @param genre the genre of this Roblox game
     * @param visits the overall number of times users have visited this Roblox game
     * @param favoritedCount the favorited count of this Roblox game
     * @param upVotes the  upVote count for this Roblox game
     * @param downVotes the downVote count for this Roblox game
     * @param maxPlaying the sum of historical player counts for this Roblox game
     */
    public RobloxGame(String name, long rootPlaceId, long id, int playing, String genre, long visits, int favoritedCount,
        int upVotes, int downVotes, int maxPlaying, Boolean isContentRestricted){
        this.name = name;
        this.rootPlaceId = rootPlaceId;
        this.id = id;
        this.playing = playing;
        this.genre = genre;
        this.visits = visits;
        this.favoritedCount = favoritedCount;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.maxPlaying = maxPlaying;
        this.isContentRestricted = isContentRestricted;
    

    }

    //getters for robloxGame
    
    /**
     * Returns and int representation of the Down Vote count for
     * this Roblox Game.
     * 
     * @return int representation of the Down Vote count
     * @since 10-26-2026
     */
    public int getDownVotes(){
        return downVotes;
    }


    /**
     * Creates and returns a string represntation of this Roblox game.
     * 
     * @return a string representation of this Roblox game
     * @since 9-26-2026
     */
    public String getInfo(){
        return "Game Name: " + name +
                "\nRoot Place ID:" + rootPlaceId +
                "\nUniverse ID:" + id +
                "\nGenre: " + genre +
                "\nCurrent Player Count: " + playing +
                "\nVisits: " + visits +
                "\nFavorites: " + favoritedCount +
                "\nUpVotes: " + upVotes +
                "\nDownVotes: " + downVotes;
                //might need to add max player count ie the historical player counts
    }

    /**
     * Returns a Boolean stating whether or not the content for this
     * game is restricted or not.
     * 
     * @return a Boolean stating whether or not games' content is restricted
     * @since 10-25-2026
     */
    public Boolean getISContentRestricted(){
        return isContentRestricted;
    }

    /**
     * Returns a string representation of this game.
     * 
     * @return the name of this game.
     * @since 9-26-2026
     */
    public String getName(){
        return name;
    }

    /**
     * Returns a long that represents the UnivereseID of this game.
     * 
     * @return a long representing the Universe id of this game
     * @since 9-26-2026
     */
    public long getID(){
        return id;
    }

    /**
     * Returns an integer count of this games Concurrent Players.
     * 
     * @return an integer count of this games Concurrent Players
     * @since 9-26-2026
     */
    public int getPlaying(){
        return playing;
    }

    /**
     * Returns a long count of this games root place id.
     * 
     * @return a long count of this games root place id
     * @since 10-25-26
     */
    public long getRootPlaceID(){
        return rootPlaceId;
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
     * @return int representation of total favorites
     * @since 9-26-2026
     */
    public int getFavoritedCount(){
        return favoritedCount;
    }

    /**
     * Returns an integer representing the total number of upVotes this game
     * has received.
     * 
     * @return int representation of total upVotes
     * @since 9-26-2026
     */
    public int getUpVotes(){
        return upVotes;
    }

    /**
     * Returns and integer representing the largest historical concurrent
     * player count for this game.
     * 
     * @return int representing the largest historical largets concurrent player count
     * @since 9-26-2026
     */
    public int getMaxPlaying(){
        return maxPlaying;
    }

    
    //setters for RobloxGame

    /**
     * Sets the current count of Down Votes for this Roblox Game.
     * 
     * @param downVotes represents the count of downVotes from players for this game
     * @since 10-26-2026
     */
    public void setDownVotes(int downVotes){
        this.downVotes = downVotes;
    }


    /**
     * Sets the current number of this games players when called.
     * 
     * @param playing represents the current number of players
     * @since 9-26-2026
     */
    public void setPlaying(int playing){
        this.playing = playing;
    }

    
    /**
     * Sets the number of this games favorited count when called.
     * 
     * @param favoritedCount represents the current number of favorites from players
     * @since 9-26-2026
     */
    public void setFavoritedCount(int favoritedCount){
        this.favoritedCount = favoritedCount;
    }
    
    /**
     * Sets whether or not the games' content is restricted.
     * 
     * @param isContentRestricted represents whether or not a games' content is restricted
     * @since 10-25-2026
     */
    public void setIsContentRestricted(Boolean isContentRestricted){
        this.isContentRestricted = isContentRestricted;
    }


    /**
     * Sets the name of this RobloxGame.
     * 
     * @param name represents the name of this RobloxGame
     * @since 9-26-2026
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the universe ID for this game.
     * 
     * @param id represents the universe ID for this game
     * @since 9-26-2026
     */
    public void setID(long id){
        this.id = id;
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
     * Sets the rootPlaceId for this game.
     * 
     * @param rootPlaceId
     * @since 10-25-26
     */
    public void setRootPlaceID(long rootPlaceId){
        this.rootPlaceId = rootPlaceId;
    }



    /**
     * Sets the upVotes count for this game when called.
     * 
     * @param upVotes represents the number of upVotes for this game
     * @since 9-26-2026S
     */
    public void setUpVotes(int upVotes){
        this.upVotes = upVotes;
    }

    /**
     * Sets the historical maxPlaying to playing if
     * playing is larger than maxPlaying
     * 
     * @param maxPlaying represents the highest recorded player count
     * @param playing represents the current recorded player count
     * @since 9-26-2026
     */
    public void updateMaxPlaying(int maxPlaying, int playing){
        if(playing > maxPlaying){
            this.maxPlaying = playing;
        } 
        this.maxPlaying = maxPlaying;
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
