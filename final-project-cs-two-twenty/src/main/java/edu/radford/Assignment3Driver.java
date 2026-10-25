package edu.radford;
import java.util.Scanner;
import com.google.gson.Gson;
import java.util.ArrayList;

/*
Sources:
Roblox API Endpoints utilized in RobloxDataRetriever Class:
[1] “games.roblox.com | Documentation - Roblox Creator Hub,” robloxdevrel, 2026. https://create.roblox.com/docs/en-us/cloud/reference/domains/games (accessed Sep. 26, 2026).

Adapted the Program of Study class (mainly the data structure) from our text book in RobloxGameCollection class:
[2] IEEE Citation: J. Lewis, J. Chase, and Piyali Sengupta, Java software structures : designing and using data structures. Harlow, Essex: Pearson Education Limited, pp. 133-134, 2014.

*/

/**
 * Driver class used to start the data capture/final project program.
 *
 * @author Zackary Nelson, Bradly Patton, Peyton Slusser, Ulises Royal
 * @version 10-24-2026
 * @since 9-26-2026 
 */
public class Assignment3Driver{


/**
 * Main method used to start the program. The user is prompted
 * on whether they want to retrieve Roblox Game Data. If yes,
 * a RobloxDataRetriever is created and the game data is retrieved
 * and displayed.
 * 
 * @param args command-line arguments passed to the program
 * @throws Exception if an error occurs while retrieving game data
 */
    public static void main(String[] args) throws Exception {
        //gson json experiment
        
        String json = """
            {

            "gameName": "Rivals",
            "currentPlayerCount": 12500
        }
        """;

        Gson gson = new Gson();

        RobloxGame game = gson.fromJson(json, RobloxGame.class);

        System.out.println(game.getGameName());
        System.out.println(game.getCurrentPlayerCount());

        //gson json experiment --Zack
        
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to display some Roblox Game Data[Y/N]?");
        String begin = scanner.nextLine();

        if(begin.equalsIgnoreCase("y") || begin.equalsIgnoreCase("yes")){
            System.out.println("Starting Search!");
            
            //creates the roblox data retriever and creates a set of 10 temp games
            RobloxDataRetriever retriever = new RobloxDataRetriever();
            
            retriever.loadGameData("robloxGames.dat");
            System.out.println("Loaded saved game data!");

            //creates object that can sort games
            FileSort sorter = new FileSort();

            ArrayList<RobloxGame> sortedGames = sorter.sortByPlaters(retriever.getCollection());

            for (RobloxGame sortedGame : sortedGames) {
                System.out.println(sortedGame.getInfo());
                System.out.println("-------------------------------");
            }

            System.out.println("Finished Search!");
        } else if(begin.equalsIgnoreCase("n") || begin.equalsIgnoreCase("no")){
            System.out.println("See you next time!");
            
        } else {
            System.out.println("Answer was neither [Y/N] couldn't search");
        }

        
        scanner.close();
    }
}
