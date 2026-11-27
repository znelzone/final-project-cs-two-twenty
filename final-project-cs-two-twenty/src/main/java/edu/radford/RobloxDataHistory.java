// ============================== IMPORTS / PACKAGES ===============================
package edu.radford;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

// ============================== CODE =============================================

/**
 * RobloxDataHistory contains methods for collecting and
 * saving historical data for roblox games.
 * @author Zackary Nelson
 * 
 * @version 11-27-2026
 * @since 10-5-2026
 */

public class RobloxDataHistory implements Serializable{

    private final static long serialVersionUID = 898954L;

// =============================== CONSTRUCTOR ======================================

    /**
     * A singular point-in-time of a Roblox Game's stats.
     * 
     * @since 11-27-2026
     */
    public record Snapshot(long id, long rootPlaceId, String name, int playing, long visits,
        String genre, int favoritedCount, Boolean isContentRestricted, int upVotes, int downVotes, 
        int maxPlaying, double likeRatio, String formattedTime, LocalDateTime nonFormattedTime
        ) implements Serializable{}

    private ArrayList<Snapshot> snapshots = new ArrayList<Snapshot>();

// =============================== METHODS ==========================================

        /**
         * Adds a snapshot of an existing game to an Arraylist of snapshots of an existing game.
         * 
         * @param game the game to take a snapshot of.
         * @since 11-27-2026
         */
        public void addSnapshot(RobloxGame game){
            
                snapshots.add( new Snapshot(game.getID(), game.getRootPlaceID(), game.getName(), game.getPlaying(), game.getVisits(),
                 game.getGenre(), game.getFavoritedCount(), game.getISContentRestricted(), game.getUpVotes(), game.getDownVotes(),
                  game.getMaxPlaying(), game.calcLikeToDislike(game.getUpVotes(), game.getDownVotes()), game.getFormattedTime(), game.getNonFormattedTime()));
        }

        /**
         * Returns an ArrayList of snapshots of a Roblox Game.
         * 
         * @return an ArrayList of snapshots of a Roblox Game
         * @since 11-27-2026
         */
        public ArrayList<Snapshot> getSnapshots(){
            return snapshots;
        }

        /**
         * Returns and int representation of the size of the ArrayList of snapshots.
         * 
         * @return int representation of the size of the ArrayList of snapshots
         */
        public int size(){
            return snapshots.size();
        }

}
