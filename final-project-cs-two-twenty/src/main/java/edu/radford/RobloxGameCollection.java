/*
Data Structure and Class adapted from
Title: Java Software Structures
Code Name: Program of Study
Author: Josheph Chase, John Lewis
Date: 2014
Type: Source Code
IEEE Citation: J. Lewis, J. Chase, and Piyali Sengupta, Java software structures : designing and using data structures. 
                Harlow, Essex: Pearson Education Limited, pp. 133-134, 2014.
 */

package edu.radford;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a RobloxGameCollection, a list of games retreived from the Roblox
 * api catalog.
 * 
 * @version 9-3-2026
 * @since 9-3-2026
 */
public class RobloxGameCollection implements Iterable<RobloxGame>, Serializable{
    
    private List<RobloxGame> list;

    /**
     * Constructs an initially empty linkedlist of RobloxGames
     */
    public RobloxGameCollection(){
        list = new LinkedList<RobloxGame>();
    }

    /**
     * Adds the specified Roblox game to the end of the Collection list.
     * 
     * @param game the game to be added
     */
    public void addGame(RobloxGame game){
        if(game != null){
            list.add(game);
        }
    }

    /**
     * Finds and returns the course matching the specified gameName and universeID
     * 
     * @param gameName
     * @param UniverseID
     * @return the game, or null if not found
     */
    public RobloxGame find(String gameName, long universeID){
        for(RobloxGame robloxGame: list){
            if(gameName.equals(robloxGame.getGameName()) 
                && universeID == robloxGame.getUniverseID()){
                return robloxGame;
            }
        }
        return null;
    }

    /**
     * Adds the specified game after the target game. Does nothing if
     * either the course is null or if the target is not found.
     * 
     * @param target the game after which the new course will be added
     * @param newGame the game to add
     */
    public void addAfterGame(RobloxGame target, RobloxGame newGame){
        if(target == null || newGame == null){
            return;
        }
        int targetIndex = list.indexOf(target);
        if(targetIndex != -1){
            list.add(targetIndex + 1, newGame);
        }
    }


    /**
     * Replaces the specified target game with the new game. Does nothing if
     * either course is null or if the target is not found.
     * 
     * @param target the course to be replaced
     * @param newGame the new game to add
     */
    public void replace(RobloxGame target, RobloxGame newGame){
        if(target == null || newGame == null){
            return;
        }
        int targetIndex = list.indexOf(target);
        if(targetIndex != -1){
            list.set(targetIndex, newGame);
        }
    }

    /**
     * Creates and returns a string representation of this RobloxGameCollection list.
     * 
     * @return a string representation of this RobloxGameCollection
     */
    public String toString(){
        String result = "";
        for(RobloxGame robloxGame: list){
            result += robloxGame.getInfo() + "\n";
        }
        return result;
    }

    /**
     * Returns an Iterator for this RobloxGameCollection.
     * 
     * @return an Iterator for this RobloxGameCollection
     */
    public Iterator<RobloxGame> iterator(){
        return list.iterator();
    }

    /**
     * Saves a serialized version of this RobloxGameCollection to the specified
     * file name.
     * 
     * @param fileName the file name under which the RGC will be stored
     * @throws IOException
     */
    public void save(String fileName) throws IOException{
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    /**
     * Loads a serialized RobloxGameCollection from the specified file.
     * 
     * @param fileName the file from which the RGC is read
     * @return the loaded RobloxGameCollection
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static RobloxGameCollection load(String fileName) throws IOException,
    ClassNotFoundException{
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        RobloxGameCollection rgc = (RobloxGameCollection) ois.readObject();
        ois.close();

        return rgc;
    }

    
}
