//============================== CITATIONS ============================

/*
Realized along the way we were making WAY TOO MANY REQUESTS, causing alot of themto return to us with the 429 error code and being unable to update alot of our data.
Therefor we utilized the Thread.Sleep() method and exponentially grew the wait time for every attempt allowing only 4 attempts for each request.

Java's Oracle Documentation for Thread's: In this instance we utilized Threads sleep() method to exponentially grow the wait time in ms for our individual call attemtpts.
[6] “Thread (Java Platform SE 8 ),” docs.oracle.com. https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html (accessed Nov. 25, 2026).
*/

//============================= IMPORTS AND PACKAGES ====================

package edu.radford;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

//============================== CODE ===================================

/**
 * Retreives data about Roblox Games from the Roblox API.
 * Sends HTTP requests to Roblox API endpoints
 * 
 * @author Zackary Nelson, Bradley Patton, Peyton Slusser, Ulises Royal
 * @version 11-26-2026
 * @since 9-3-2026
 */
public class RobloxDataRetriever{

    private RobloxGameCollection ourCollection;
    private HttpClient client;
    private String VOTES_URL = "https://games.roblox.com/v1/games/votes?universeIds=";

    //added to keep track and print out the number of good and bad responses from Roblox' API
    private int numOfTooManyReqests = 0;
    private int numOfSuccessfulRequests = 0;


    //================================================ CONSTRUCTOR ===================================================

    /**
     * Constructs a RobloxDataRetriever, creating the HTTP client used
     * to send request to the Roblox API. Also loads and saves the collection
     * used to retrieve and view Roblox Games throug, will be changed so that
     * Collection is not loaded and saved here.
     * 
     * @version 11-26-2026
     * @since 9-26-2026
     */
    public RobloxDataRetriever() throws IOException, ClassNotFoundException{
    client = HttpClient.newHttpClient();

    try {
       ourCollection = RobloxGameCollection.load("robloxGames.dat");
    
        if(ourCollection == null){
            ourCollection = new RobloxGameCollection(0);
            initializeGamesTemp();
        }

        //I believe the if Statement above now fulfills the use of these lines of commented out code;
        //I am however keeping them here for the time being for redundancy -- Zack
        
        //if editing RobloxGame
    //    ourCollection = new RobloxGameCollection(0);
    //    initializeGamesTemp();
       
        addRandomGames();
        updateGameData();
        updateGameDataLikes();

        System.out.println("Here's the Number of Games in the Collection:");
        System.out.println(ourCollection.getNumberOfGames());
       
    } catch (Exception e) {
        e.printStackTrace();
    }

    //temporary saving of our collection until we have a clear location for saving in our driver
    ourCollection.save("robloxGames.dat");    
    }

    //========================================= METHODS ====================================================
    
    /**
     * Constructs a retriever with optional automatic updates.
     *
     * @param searchOnly true to initialize without bulk updates
     */
    public RobloxDataRetriever(boolean searchOnly)
            throws IOException, ClassNotFoundException {
        if (!searchOnly) {
            throw new IllegalArgumentException(
                    "Use the default constructor for full updates.");
        }

        client = HttpClient.newHttpClient();
        ourCollection = new RobloxGameCollection(0);
    }


    /**
     * Utilizes prexisiting RobloxGames collection to add random roblox games
     * to our collection by utilizing a Random Games Endpoint.
     * 
     * @since 11-18-2026
     */
    public void addRandomGames (){
        String randomGameName = findRandInCollection(randomIndexGenerator(ourCollection.getNumberOfGames()));
        
        if (randomGameName == null){
            return; //no name to search with; return nothing
        }

        System.out.println("Random search term:" + randomGameName);

        String json;

        try {
            json = getData(buildRandUniverseRequestURL(randomGameName));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Response null?" + (json == null));

        if(json == null){
            System.out.println("Random Game Search Failed for" + randomGameName);
            return;
        }

        JsonObject responseJsonObject = JsonParser.parseString(json).getAsJsonObject();
        //use existing functions that can take a random universe url to find games and add those to our collection
        JsonArray searchResults = responseJsonObject.getAsJsonArray("searchResults");
        if (searchResults == null){
            return;
        }
        
        int added = 0;

        for(int i = 0; i < searchResults.size(); i++){

            JsonArray contents = searchResults.get(i).getAsJsonObject().getAsJsonArray("contents");
            if (contents == null){continue;}

            for( int j = 0; j < contents.size(); j++){
                JsonObject gameData = contents.get(j).getAsJsonObject();

                if (!gameData.has("universeId") || !gameData.has("name")){continue;}

                long universeID = gameData.get("universeId").getAsLong();
                String gameName = gameData.get("name").getAsString();

                //when a game does not already exist in our collection and has a valid name we will add the game
                if(!ourCollection.gameExists(universeID) && ourCollection.hasValidName(gameName)){
                    RobloxGame robloxGame = new RobloxGame(gameName, 0, universeID, 0, null, 0, 0, 0, 0, 0, null);
                    ourCollection.addGame(robloxGame);
                    added ++;
                }
            }
        }

        System.out.println("Added " + added + " new games");
    }


    public ArrayList<RobloxGame> searchGames(String gameName) throws Exception {

        ArrayList<RobloxGame> results = new ArrayList<>();

        if (gameName == null || gameName.isBlank()) {
            return results;
        }

        String url = buildRandUniverseRequestURL(gameName.trim());
        String json = getData(url);

        if (json == null) {
            throw new IOException("Unable to retrieve search results.");
        }

        JsonObject response = JsonParser.parseString(json).getAsJsonObject();

        JsonArray searchResults = response.getAsJsonArray("searchResults");

        if (searchResults == null) {
            return results;
        }

        for (JsonElement result : searchResults) {

            JsonArray contents = result.getAsJsonObject()
                    .getAsJsonArray("contents");

            if (contents == null) {
                continue;
            }

            for (JsonElement element : contents) {

                JsonObject gameData = element.getAsJsonObject();

                if (!gameData.has("universeId") || !gameData.has("name")) {
                    continue;
                }

                long universeID = gameData.get("universeId").getAsLong();
                String name = gameData.get("name").getAsString();

                RobloxGame game = new RobloxGame(
                        name, 0, universeID, 0, null,
                        0, 0, 0, 0, 0, null
                );

                results.add(game);
            }
        }

        return results;
    }

    /**
     * Retrieves individual game statistics by Universe ID.
     *
     * @param universeID the Universe ID of the game
     * @return the RobloxGame containing its statistics,
     *         or null if the game is not found
     * @throws Exception if an API request fails
     */
    public RobloxGame getGameByUniverseID(long universeID)
            throws Exception {

        String json = getData(buildMainRequestURL(universeID));

        if (json == null) {
            throw new IOException("Unable to retrieve game data.");
        }

        JsonArray data = JsonParser.parseString(json)
                .getAsJsonObject().getAsJsonArray("data");

        if (data == null || data.size() == 0) {
            return null;
        }

        JsonObject gameData = data.get(0).getAsJsonObject();

        RobloxGame game = new RobloxGame(
                gameData.get("name").getAsString(),
                0, universeID, 0, null, 0, 0, 0, 0, 0, null);

        game.setPlaying(gameData.get("playing").getAsInt());
        game.setVisits(gameData.get("visits").getAsLong());
        game.setFavoritedCount(gameData.get("favoritedCount").getAsInt());
        game.setGenre(gameData.get("genre").getAsString());
        game.setRootPlaceID(gameData.get("rootPlaceId").getAsLong());
        game.setIsContentRestricted(
                gameData.get("isContentRestricted").getAsBoolean());

        String votesJson = getData(buildLikesRequestURL(universeID));

        if (votesJson == null) {
            throw new IOException("Unable to retrieve game votes.");
        }

        JsonArray votes = JsonParser.parseString(votesJson)
                .getAsJsonObject().getAsJsonArray("data");

        if (votes == null || votes.size() == 0) {
            throw new IOException("Game votes not found.");
        }

        JsonObject voteData = votes.get(0).getAsJsonObject();

        game.setUpVotes(voteData.get("upVotes").getAsInt());
        game.setDownVotes(voteData.get("downVotes").getAsInt());

        return game;
    }

    /**
     * Generates a random number between 0 to the total number
     * of games held in ourCollection.
     * 
     * @return int number representing the location of a random game in ourCollection
     * @since 11-18-2026
     */
    private int randomIndexGenerator(int numberOfGames){
        int randomIndex = (int)(Math.random() * numberOfGames);
        return randomIndex;
    }


    /**
     * Private helpermethod for addRandomGames. Finds a random game in
     * ourCollection.
     * 
     * @return String representation of the name of a random game in ourCollection
     * @since 11-18-2026
     */
    private String findRandInCollection(int randomIndex){
        int currentNum = 0;
        for(RobloxGame RobloxGame : ourCollection){
            if(currentNum == randomIndex){
             return RobloxGame.getName();
            }
            currentNum++;
        }
        return null;
    }


    /**
     * Creates a String representing a URL API request that concatinates
     * a universeID into the URL.
     * 
     * @param id long representation of a universeId
     * @return String containing a URL concatination
     * @since 10-3-2026
     */
    public String buildMainRequestURL(long id){
        return "https://games.roblox.com/v1/games?universeIds=" +
        id +
        "&fields=name%2C%20playing%2C%20visits%2C%20genre%2C%20favoritedCount";
    }

    /**
     * Creates a String representation of a URL API request that concatinates
     * a universeID onto the end of the VOTES_URL constant.
     * 
     * 
     * @param id long representation of a universeId
     * @return String containing a URL concatination
     * @since 10-26-2026
     */
    public String buildLikesRequestURL(long id){
        return VOTES_URL + id;
    }

    //existed since 11-12 just not in this location
    /**
     * Creates a String representation of a URL API request, concatinating
     * a prexisting Roblox Game name into the url, that retrieves a JSON 
     * object representing random RobloxGames containing universeID's.
     * 
     * @param gameName a string representation of a pre-existing RobloxGame's name
     * @return String containing a URL concatination
     * @since 11-12-2026
     */
    public String buildRandUniverseRequestURL(String gameName){
        return "https://apis.roblox.com/search-api/omni-search?searchQuery=" +
                URLEncoder.encode(gameName, StandardCharsets.UTF_8) +
                "&sessionId=12345&pageType=all";
    }



    /**
     * A temporary method designed to create ten games for the Roblox API
     * to gather other Roblox games using recomended data from the respective
     * ten games. Each game is added to the RobloxGameCollection.
     * 
     * @since 9-30-2026
     */
    public void initializeGamesTemp(){

        //Don't Splash
        RobloxGame splash = new RobloxGame(null, 0, 10771421858L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(splash);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());
        //Anime Adventures
        RobloxGame animeAdventures = new RobloxGame(null, 0, 3183403065L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(animeAdventures);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //the Hollow
        RobloxGame hollow = new RobloxGame(null,0,10770728771L,0,null,0,0, 0, 0, 0, null);
        ourCollection.addGame(hollow);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());
        //A long flight
        RobloxGame aLongFlight= new RobloxGame(null, 0, 10768785226L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(aLongFlight);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());

        //rivals
        RobloxGame rivals = new RobloxGame(null, 0, 6035872082L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(rivals);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());
        //sniperArena
        RobloxGame sniperArena = new RobloxGame(null, 0, 9534705677L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(sniperArena);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //driving Empire
        RobloxGame drivingEmpire = new RobloxGame(null, 0, 1202096104L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(drivingEmpire);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //doors
        RobloxGame doors = new RobloxGame(null, 0, 2440500124L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(doors);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //howtotrainyourdragon
        RobloxGame howToTrainYourDragon = new RobloxGame(null, 0, 7450497506L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(howToTrainYourDragon);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //superGolf
        RobloxGame superGolf = new RobloxGame(null, 0, 1424449565L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(superGolf);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //workAtPizzaPlace
        RobloxGame workAtPizzaPlace = new RobloxGame(null, 0, 47545L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(workAtPizzaPlace);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //ronopoly
        RobloxGame ronopoly = new RobloxGame(null, 0, 2621511041L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(ronopoly);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //isle
        RobloxGame isle = new RobloxGame(null, 0, 1116949753L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(isle);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
        //buildABoatForTreasure
        RobloxGame buildABoatForTreasure = new RobloxGame(null, 0, 210851291L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(buildABoatForTreasure);
        //updatesOurCollection's num of games added to the collection
        ourCollection.incrementNumberOfGames(ourCollection.getNumberOfGames());        
    }

    /**
     * Sends a GET request to the specified URL and retreives the
     * response from the API.
     * 
     * @param url the URL that the HTTP request will be sent to
     * @return a String containing the body of the HTTP response
     * @throws Exception
     * @since 10-4-2026
     */
    public String getData(String url) throws Exception {
        
        int MAXATTEMPTS = 4;
        long waitMs = 1000;

        for (int attempt = 1; attempt <= MAXATTEMPTS; attempt++){

            //REQUEST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        //RESPONSE
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if(response.statusCode() == 200){ // 200 MEANS API REQUEST SUCCEEDED!

            //this call helps keep track of how many successful request's have been recieved and returned
            incrementNumOfSuccessfulRequests();
            return response.body();

        } else if (response.statusCode() == 429){ // 429 MEANS TOO MANY REQUESTS!
            // 429 is known to us, and is reletively important for debugging therefor we need to print when debugging
            // as of 11-25-2026 as the collection size is growing we're seeing an overwellmingly growing ammount of 429 error prints
            // seeing as this is cluttering up the command lines display of this program, I will adjust this to keep track of the number
            // of times we recieve 429 rather than printing each individual 429 print.
            if (attempt < MAXATTEMPTS){
                
                //call threads sleep method; and then increment exponentially the wait time
                Thread.sleep(waitMs);
                waitMs *= 2;
            }

            incrementNumOfTooManyRequests();
            return null;
        }
        System.out.println("Roblox returned status " + response.statusCode());
        }
        //if there was no response from the endpoint
        //ie anything other than one of the known status codes above
        return null;
    }


    /**
     * Provides when called the number of 429 reponse codes received from
     * running this program.
     * 
     * @return an int representation of the number of 429 response codes
     * @since 11-25-2026
     */
    public int getNumOfTooManyRequests(){
        return numOfTooManyReqests;
    }

    /**
     * Provides when called the number of 200 response codes recieved from
     * running this program.
     * 
     * @return an int representaion of the number of 429 response codes
     * @since 11-25-2026
     */
    public int getNumOfSuccessfulRequests(){
        return numOfSuccessfulRequests;
    }

    /**
     * Increments the total num of 429 status codes when called by
     * one.
     * 
     * @since 11-25-2026
     */
    public void incrementNumOfTooManyRequests(){
        this.numOfTooManyReqests++;                
    }

    /**
     * Increments the total num of 200 status codes when called by
     * one.
     * 
     * @since 11-25-2026
     */
    public void incrementNumOfSuccessfulRequests(){
        this.numOfSuccessfulRequests++;
    }

    /*
    Title: Java's JsonParser.parse() Method Explained
    Author: Alexander Obregon
    Date: Oct 13, 2024
    Availability: https://medium.com/@AlexanderObregon/javas-jsonparser-parse-method-explained-76b126f6110e
    IEEE Citation: [4] A. Obregon, “Java’s jsonparser.parse() method explained | medium,” Medium, 
                    https://medium.com/@AlexanderObregon/javas-jsonparser-parse-method-explained-76b126f6110e (accessed Oct. 12, 2026). 

    Note on Usage: Web Article for basic understanding of syntax and strucutre of method calls used within this method.
    */
    /**
     * Retrieves updated data for each RobloxGame currently stored in
     * the collection. The universeID of each game is used to build an
     * API request URL, and the resulting response is retreived (excludes
     * likes count in response).
     * 
     * @throws Exception 
     * @since 10-4-2026
     */
    public void updateGameData() throws Exception{
        
        ArrayList<RobloxGame> gameList = new ArrayList<RobloxGame>();
        for (RobloxGame robloxGame : ourCollection){
            gameList.add(robloxGame);
        }

        for (int start = 0; start < gameList.size(); start += 50){
            List<RobloxGame> gameSubList = gameList.subList(start, Math.min(start + 50, gameList.size()));

            StringBuilder ids = new StringBuilder();

            for (RobloxGame robloxGame : gameSubList){
                if (ids.length() > 0) ids.append(",");
                ids.append(robloxGame.getID());
            }

            String json = getData("https://games.roblox.com/v1/games?universeIds=" + ids);

            if (json == null){
                System.out.println("Could not retrieve a Sublist starting at index" + start);
                continue;
            }
        
            JsonArray parsingData = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("data");

            for (JsonElement element : parsingData){
                JsonObject gameData = element.getAsJsonObject();
                long id = gameData.get("id").getAsLong();

                for(RobloxGame robloxGame : gameSubList){
                    if(robloxGame.getID() == id){
                        robloxGame.setName(gameData.get("name").getAsString());
                        robloxGame.setPlaying(gameData.get("playing").getAsInt());
                        robloxGame.setVisits(gameData.get("visits").getAsLong());
                        robloxGame.setFavoritedCount(gameData.get("favoritedCount").getAsInt());
                        robloxGame.setGenre(gameData.get("genre").getAsString());
                        robloxGame.setRootPlaceID(gameData.get("rootPlaceId").getAsLong());
                        robloxGame.setIsContentRestricted(gameData.get("isContentRestricted").getAsBoolean());
                        robloxGame.updateMaxPlaying(robloxGame.getMaxPlaying(), robloxGame.getPlaying());

                        //record the time when this game's information is being recorded
                        robloxGame.recordFormattedTime();

                        break;
                    }

                }
            }
            Thread.sleep(300);
        }





    }

    /*
    Title: Java's JsonParser.parse() Method Explained
    Author: Alexander Obregon
    Date: Oct 13, 2024
    Availability: https://medium.com/@AlexanderObregon/javas-jsonparser-parse-method-explained-76b126f6110e
    IEEE Citation: [4] A. Obregon, “Java’s jsonparser.parse() method explained | medium,” Medium, 
                    https://medium.com/@AlexanderObregon/javas-jsonparser-parse-method-explained-76b126f6110e (accessed Oct. 12, 2026). 

    Note on Usage: Web Article for basic understanding of syntax and strucutre of method calls used within this method.
    */
    /**
     * Retrieves updated upvotes and downvotes data for each RobloxGame 
     * currently stored in the collection. The universeID of each game 
     * is used to build an API request URL, and the resulting response 
     * is retreived.
     * 
     * @throws Exception 
     * @since 10-26-2026
     */
    public void updateGameDataLikes() throws Exception{
        
        ArrayList<RobloxGame> gameList = new ArrayList<RobloxGame>();

        for (RobloxGame robloxGame : ourCollection){
            gameList.add(robloxGame);
        }

        int failedGroupingOfRequests = 0;

        for (int start = 0; start < gameList.size(); start += 50){
            List<RobloxGame> gameSubList = gameList.subList(start, Math.min(start + 50, gameList.size()));

            StringBuilder ids = new StringBuilder();
            for(RobloxGame robloxGame : gameSubList){
                if (ids.length() > 0) ids.append(",");
                ids.append(robloxGame.getID());
            }

            String json = getData(VOTES_URL + ids);
            if(json == null){
                failedGroupingOfRequests++;
                continue;
            }

            JsonArray data = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("data");
            for(JsonElement element : data){
                JsonObject voteData = element.getAsJsonObject();
                long id = voteData.get("id").getAsLong();
                for(RobloxGame robloxGame : gameSubList){
                    if(robloxGame.getID() == id){
                        robloxGame.setUpVotes(voteData.get("upVotes").getAsInt());
                        robloxGame.setDownVotes(voteData.get("downVotes").getAsInt());
                        break;
                    }
                }
            }
            Thread.sleep(300);
        }
        if (failedGroupingOfRequests > 0){
            System.out.println("Could not retrieve Vote Data for " + failedGroupingOfRequests + " Group(ings) of games.");
        }
    }

    //lets RobloxGameCollection be read by other classes
    public RobloxGameCollection getCollection() {
        return ourCollection;
    }


}