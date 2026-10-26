package edu.radford;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Comparable;

//QUICK SORT METHODS ADAPTED FROM JOE CHASE CODE!


/**
 * FileSort contains methods for sorting Roblox games
 * based on their statistics retrieved from the Roblox API
 * 
 * @author Peyton Slusser, Zack Nelson
 * @version 10-26-2026
 * @since 10-25-2026
 */
public class FileSort {


    /**
     * Sorts the specified array of objects using the quick sort algorithm.
     * 
     * @param data the array to be sorted
     */
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> data){
        quickSort(data, 0, data.size()-1);
    }


    /**
     * Recursively sorts a range of objects in the specified array using the
     * quick sort algorithm.
     * 
     * @param data the array to be sorted
     * @param min the minimum index in the range to be sorted
     * @param max the maximum index in the range to be sorted
     */
    private static <T extends Comparable<T>> void quickSort(ArrayList<T> data, int min, int max){
        if(min<max){
            //create partitions
            int indexofpartition = partition(data, min, max);

            //sort the left partition (lower values)
            quickSort(data, min, indexofpartition - 1);

            //sort the right partition (higher values)
            quickSort(data, indexofpartition + 1, max);
        }
    }

    /**
     * Used by the quick sort algorithm to find the partition.
     * 
     * @param data the array to be sorted
     * @param min the minimum index in the range to be sorted
     * @param max the maximum index in the range to be sorted
     * @return
     */

    private static <T extends Comparable<T>> int partition(ArrayList<T> data, int min, int max){

        T parititonElement;
        int left, right;
        int middle = (min + max)/2;

        //use the middle data value as the partition element
        parititonElement = data.get(middle);

        //move it out of the way for now
        swap(data, middle, min);

        left = min;
        right = max;

        while (left<right){
            //search for an element this is > the partition element
            while( left < right && data.get(left).compareTo(parititonElement) <= 0){
                left ++;
            }

            //serch for an element this < the partition element
            while(data.get(right).compareTo(parititonElement) > 0){
                right--;
            }

            //swap the elements
            if(left < right){
                swap(data, left, right);
            }
        }

        //move the partition element into place
        swap(data, min, right);

        return right;
    }

    /**
     * Takes two index values swapping the generic values held at
     * those index locations in an ArrayList.
     * 
     * @param data the ArrayList being sorted
     * @param middle the middle index to be swapped
     * @param min the minimum index to be swapped
     * @since 10-26-2026
     */
    private static <T extends Comparable<T>> void swap(ArrayList<T> data, int middle, int min){
        T temp = data.get(middle);
        data.set(middle, data.get(min));
        data.set(min, temp);

    }

    /**
     * Sorts a collection of Roblox games by their current
     * player count in descending order
     * Creates a new ArrayList so that the oroginal collection
     * doesn't change.
     * 
     * @param collection the collection of Roblox games to sort
     * @return an ArrayList of Roblox games sorted by player count
     * @since 10-25-2026
     */
    public ArrayList<RobloxGame> sortByPlayersHighestToLowest(RobloxGameCollection collection) {
        //creates arraylist
        ArrayList<RobloxGame> games = new ArrayList<>();

        //copies each robloxgame
        for (RobloxGame game : collection) {
            games.add(game);
        }

        quickSort(games);

        // games.sort(
        //     //compares player counts
        //     Comparator.comparingInt(
        //         RobloxGame::getPlaying
            
        //     //highest to lowest by reversing
        //     ).reversed()
        // );
        //returns sorted list
        return games;
    }
}
