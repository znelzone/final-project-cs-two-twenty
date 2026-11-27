//===================== CITATIONS ==========================
    
/*
Utilizes the LocalDateTime Class' now() method to record the time when a roblox game is being recorded. The
Now() method is utilized in the recordFormattedTime() method in this RobloxGame Class.
[7] “LocalDateTime (Java Platform SE 8 ),” docs.oracle.com. https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html (accessed Nov. 26, 2026).

This RobloxGame class utilizes the ofPattern() method from the DateTimeFormatter Class to format the time returned
by the Now() method to turn the returned infomration from the now message as a String representation of the time
of record.
[8] “DateTimeFormatter (Java Platform SE 8 ),” docs.oracle.com. https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html (accessed Nov. 26, 2026).

*/
    
//this is the citation for the history stuff involving record
//[9] https://docs.oracle.com/en/java/javase/17/language/records.html#GUID-6699E26F-4A9B-4393-A08B-1E47D4B2D263

//======================== CODE ===========================

package edu.radford;
import java.io.Serializable;
import java.lang.Comparable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RobloxGame represents a game from the website Roblox.
 * 
 * @author Zackary Nelson, Bradley Patton, Peyton Slusser, and Ulises Royal
 * @version 11-27-2026
 * @since 9-26-2026
 */
public class RobloxGame implements Serializable, Comparable<RobloxGame>{

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

    //will be calculated as a ratio of likes to dislikes
    private double likeRatio;

    //explicitly declared serilization ID declared to avoid InvalidClassException
    //when making modifications to this class.
    private static final long serialVersionUID = 8675309L;

    //location of the exact time and date the game's information was recorded
    private String formattedTime;
    private LocalDateTime nonFormattedTime;

    //an ArrayList of snapshots of RobloxGames will be saved in this
    private RobloxDataHistory history;
    
    
    //================================================== CONSTRUCTOR ==================================================

    /**
     * Constructs a RobloxGame with the specified information.
     * 
     * @param rootPlaceId the root place id  
     * @param isContentRestricted determines if content is or isn't restricted 
     * @param name the name of this Roblox game
     * @param id the universe ID of this Roblox game
     * @param playing the concurent player count of this Roblox game
     * @param genre the genre of this Roblox game
     * @param visits the overall number of times users have visited this Roblox game
     * @param favoritedCount the favorited count of this Roblox game
     * @param upVotes the  upVote count for this Roblox game
     * @param downVotes the downVote count for this Roblox game
     * @param maxPlaying the sum of historical player counts for this Roblox game
     * @param serialVersionUID the Long representation of this objects serialVersionUID
     * @param likeRatio the double representation of the like-to-dislike ratio
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

    //================================================= METHODS ============================================================

    /**
     * Overwrites compareTo inorder to compare two roblox games;
     * returns a positive int if first game is greater than the 
     * other game, returns 0 if both games are equal, returns negative
     * int if first game is less than other game.
     * 
     * 
     * @param other the RobloxGame to compare a game to.
     * @return int the integer representation of the comparison evaluation.
     * @since 10-26-2026
     */
    @Override 
    public int compareTo(RobloxGame other){
        return Integer.compare(this.playing, other.playing);
    }

    /**
     * Calculates a like-to-dislike percentage.
     * 
     * @param upVotes The count of upVotes or likes that this game has recieved
     * @param downVotes the count of downVotes or dislikes that this game has recieved
     * @return a double representation of the percetage of likes to dislikes
     * @since 11-27-2026
     */
    public double calcLikeToDislike(int upVotes, int downVotes){
        int total = upVotes + downVotes;
        if (total == 0){
            return 0.0; //not votes yet; avoids division by 0
        }
        return ((double) upVotes / total) * 100;
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
                "\nDownVotes: " + downVotes +
                "\nLike-To-Dislike Percentage: " + Math.round(calcLikeToDislike(getUpVotes(), getDownVotes()) * 10) / 10.0 +
                "\nPast Top Player Count: " + maxPlaying +
                "\nTime Recorded: " + formattedTime;
    }

    /**
     * Creates and Returns a string representation of this roblox game with limited
     * information (Universe Id, Game Name, Current Player Count).
     * 
     * @return A string representation of this roblox game
     * @since 11-24-2026
     */
    public String getLimitedInfo(){
        return 
            "Game Name: " + name + 
            " | Universe ID: " + id + 
            " | Player Count: " + playing + 
            " | Max Players: " + maxPlaying + 
            " | Time Recorded: " + formattedTime + 
            " | Like-To-Dislike Ratio: " + Math.round(calcLikeToDislike(getUpVotes(), getDownVotes()) * 10) / 10.0;
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
     * Returns an ArrayList of the history of this Roblox Game.
     * 
     * @return ArrayList of the history of this Roblox Game
     * @since 11-27-2026
     */
    public RobloxDataHistory getHistory(){
        if (history == null){
            history = new RobloxDataHistory();
        }
        return history;
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
     * Returns a String representation of the time the game was recorded in a human friendly
     * readable format.
     * 
     * @return a string representation of the time the game was recorded
     * @since 11-26-2026
     */
    public String getFormattedTime(){
        return formattedTime;
    }

    /**
     * Returns a string representation of the time the game was recorded in a computer friendly
     * fomrat.
     * 
     * @return A string representation of the time the game was recorded
     * @since 11-27-2026
     */
    public LocalDateTime getNonFormattedTime(){
        return nonFormattedTime;
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


    //==================================================== RECORD ==============================================
    
    /**
     * Records the current time in a month/day/year/time format.
     * 
     * @since 11-26-2026
     */
    public void recordFormattedTime(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        this.formattedTime = LocalDateTime.now().format(formatter);
    }

    /**
     * Records the current time in a computer friendly format that could be used for sorrting.
     * 
     * @since 11-27-2026
     */
    public  void recordNonFormattedTime(){
        this.nonFormattedTime = LocalDateTime.now();
    }

    /**
     * 
     */
    public void recordSnapshot(){
        getHistory().addSnapshot(this);
    }


    
    //==================================================== SETTERS =============================================

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
     * playing is larger than maxPlaying.
     * 
     * @param maxPlaying represents the highest recorded player count
     * @param playing represents the current recorded player count
     * @since 9-26-2026
     */
    public void updateMaxPlaying(int maxPlaying, int playing){
        if(playing > maxPlaying){
            this.maxPlaying = playing;
        } else {
            this.maxPlaying = maxPlaying;
        }
    }

    /**
     * Sets the overall historical number of visits to this game.
     * 
     * @param visits represents the overall number of visits to this game
     * @since 9-26-2026
     */
    public void setVisits(long visits){
        this.visits = visits;
    }


}
