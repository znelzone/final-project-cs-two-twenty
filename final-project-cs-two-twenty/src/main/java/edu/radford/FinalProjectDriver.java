package edu.radford;
import java.util.Scanner;
// import com.google.gson.Gson;
import java.util.ArrayList;

/*
Sources:
Roblox API Endpoints utilized in RobloxDataRetriever Class:
[1] “games.roblox.com | Documentation - Roblox Creator Hub,” robloxdevrel, 2026. https://create.roblox.com/docs/en-us/cloud/reference/domains/games (accessed Sep. 26, 2026).

Adapted the Program of Study class (mainly the data structure) from our text book in RobloxGameCollection.java class:
[2] IEEE Citation: J. Lewis and J. Chase, Java software structures : Designing and using data structures, 4th ed. United States of America: Pearson Education Limited, 2014, pp. 133-134.

Adapted the Quick Sort Algorithm featured on pages 233-235 in our FileSort.java class:
[3] IEEE Citation: J. Lewis and J. Chase, Java Software Structures: Designing and using data structures, 4th ed. United States of America: Pearson Education Limited, 2014, pp.232-235.

At first we didn't realize how easily we could utilize the jsonparser.parse() method to parse json objects returned from api endpoints, so we went through the effort of reformating
a new java project utilizing maven and Google's Gson library. Upon finding out the jsonparser.parse() method was relitvely simple, with little to not additional effort to utilize
however we did keep the google imports for json object and json parser. This is the conversation we had with ChatGPT on how to set up our project with the gson library available to us.
[4] “Check out this chat,” ChatGPT, https://chatgpt.com/share/6adfd13e-b84c-83ea-a53e-46ea8b2ed50c (accessed Oct. 23, 2026). 

Utilized this web article for a basic understanding of Java's jsonparser.parse() method's capabilities and basic systax structure for the updateGameData method in our
RobloxDataRetriever.java class and the updateGameDataLikes method in the same class:
[5] A. Obregon, “Java’s jsonparser.parse() method explained | medium,” Medium, https://medium.com/@AlexanderObregon/javas-jsonparser-parse-method-explained-76b126f6110e (accessed Oct. 12, 2026).

*/

/**
 * Driver class used to start the data capture/final project program.
 *
 * @author Zackary Nelson, Bradly Patton, Peyton Slusser, Ulises Royal
 * @version 10-27-2026
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
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to display some Roblox Game Data[Y/N]?");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")){
            
            System.out.println("updating data...");
            //creates a roblox games collection, loads a previously saved games collection into
            //newly created games collection, and then updates said information.
            RobloxDataRetriever retriever = new RobloxDataRetriever();
            
            System.out.println("Would you like a list in [1] ascending order () or [2] Decending order ()? \nType 1 or 2 for chosen option.");
            
            int sortChoice = scanner.nextInt();

            //creates object that can sort games
            FileSort sorter = new FileSort();

            ArrayList<RobloxGame> sortedGames = sorter.sortByPlayers(retriever.getCollection());

            //options for how user wants their data sorted in ascending or descending order
            switch (sortChoice) {
                case 1:
                //Ascending order

                for (RobloxGame sortedGame : sortedGames) {
                System.out.println(sortedGame.getInfo());
                System.out.println("-------------------------------");
                }

                    break;
            
                case 2:
                //Descending order

                for (RobloxGame sortedGame : sortedGames.reversed()) {
                System.out.println(sortedGame.getInfo());
                System.out.println("-------------------------------");
                }
                   break;
            }

            System.out.println("Finished Search!");
        } else if(choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")){
            System.out.println("See you next time!");
            
        } else {
            System.out.println("Answer was neither [Y/N] couldn't search");

        }

        scanner.close();
    }
}
