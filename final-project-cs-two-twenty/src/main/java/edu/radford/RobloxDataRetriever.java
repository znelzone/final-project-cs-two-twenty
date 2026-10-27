package edu.radford;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

/**
 * Retreives data about Roblox Games from the Roblox API.
 * Sends HTTP requests to Roblox API endpoints
 * 
 * @author Zackary Nelson, Bradley Patton, Peyton Slusser, Ulises Royal
 * @version 10-26-2026
 * @since 9-3-2026
 */
public class RobloxDataRetriever{

//will mainly be the class where we utilize the
//roblox api to recieve data about our games
//more than likely this will be the class that
//will be creating RobloxGame objects
    private RobloxGameCollection ourCollection;
    private HttpClient client;
    private String VOTES_URL = "https://games.roblox.com/v1/games/votes?universeIds=";

    /**
     * Constructs a RobloxDataRetriever, creating the HTTP client used
     * to send request to the Roblox API. Also creates the collection
     * used to store RobloxGame objects.
     * 
     * Currently calls initializeGamesTemp() to fill our collection with
     * while game creation automation is being developed.
     * 
     * @version 10-5-2026
     * @since 9-26-2026
     */
    public RobloxDataRetriever() throws IOException, ClassNotFoundException{
    client = HttpClient.newHttpClient();

    try {
       ourCollection = RobloxGameCollection.load("robloxGames.dat");
       
       //if editing RobloxGame
    //    ourCollection = new RobloxGameCollection();
    //    initializeGamesTemp();
       
       updateGameData();
       updateGameDataLikes();

       //for debugging
    //    ourCollection.toString();
       

    } catch (Exception e) {
        //for debugging purposes
        e.printStackTrace();
    }


    //temporary saving of our collection until the final save is in the place that calls retreiver constructor
    ourCollection.save("robloxGames.dat");


    
    }


    /**
     * Creates a String representing a URL API request that concatinates
     * a universeID into the URL.
     * 
     * @param long long representation of a universeId
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
     * @param long long representation of a universeId
     * @return String containing a URL concatination
     * @since 10-26-2026
     */
    public String buildLikesRequestURL(long id){
        return VOTES_URL + id;
    }



    /**
     * A temporary method designed to create ten games for the Roblox API
     * to gather other Roblox games using recomended data from the respective
     * ten games. Each game is added to the RobloxGameCollection.
     * 
     * @since 9-30-2026
     */
    public void initializeGamesTemp(){

        //the Hollow
        RobloxGame hollow = new RobloxGame(null,0,10770728771L,0,null,0,0, 0, 0, 0, null);
        ourCollection.addGame(hollow);
        //rivals
        RobloxGame rivals = new RobloxGame(null, 0, 6035872082L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(rivals);
        //sniperArena
        RobloxGame sniperArena = new RobloxGame(null, 0, 9534705677L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(sniperArena);
        //driving Empire
        RobloxGame drivingEmpire = new RobloxGame(null, 0, 1202096104L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(drivingEmpire);
        //doors
        RobloxGame doors = new RobloxGame(null, 0, 2440500124L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(doors);
        //howtotrainyourdragon
        RobloxGame howToTrainYourDragon = new RobloxGame(null, 0, 7450497506L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(howToTrainYourDragon);
        //superGolf
        RobloxGame superGolf = new RobloxGame(null, 0, 1424449565L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(superGolf);
        //workAtPizzaPlace
        RobloxGame workAtPizzaPlace = new RobloxGame(null, 0, 47545L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(workAtPizzaPlace);
        //ronopoly
        RobloxGame ronopoly = new RobloxGame(null, 0, 2621511041L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(ronopoly);
        //isle
        RobloxGame isle = new RobloxGame(null, 0, 1116949753L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(isle);
        //buildABoatForTreasure
        RobloxGame buildABoatForTreasure = new RobloxGame(null, 0, 210851291L, 0, null, 0, 0, 0, 0, 0, null);
        ourCollection.addGame(buildABoatForTreasure);
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

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if(response.statusCode() == 200){
            return response.body();
        }

        //if there was no response from the endpoint
        //ie anything other than the 200 status code
        //return null
        return null;
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
        for(RobloxGame robloxGame : ourCollection){
            long universeID = robloxGame.getID();
            String url = buildMainRequestURL(universeID);
            String json = getData(url);

            if(json == null){
                //if the response from the website didn't contain a 200
                //status code ie didn't send anything back for the program
                //to use
                System.out.println("Cound not retrieve data for: " + universeID);
            } else {
                //if a response came back

                //printing the json response
                // System.out.println("Universe ID: " + universeID);
                // System.out.println(json + "\n");

                //converts API response from java string into JSON structure
                JsonObject response = JsonParser.parseString(json).getAsJsonObject();

                //get(0) means selecting the first game in the list
                JsonObject gameData = response.getAsJsonArray("data").get(0).getAsJsonObject();

                String name = gameData.get("name").getAsString();
                int players = gameData.get("playing").getAsInt();
                long visits = gameData.get("visits").getAsLong();
                int favorites = gameData.get("favoritedCount").getAsInt();
                String genre = gameData.get("genre").getAsString();
                long root = gameData.get("rootPlaceId").getAsLong();
                Boolean contentRestriction = gameData.get("isContentRestricted").getAsBoolean();

                robloxGame.setName(name);
                robloxGame.setPlaying(players);
                robloxGame.setVisits(visits);
                robloxGame.setFavoritedCount(favorites);
                robloxGame.setGenre(genre);
                robloxGame.setRootPlaceID(root);
                robloxGame.setIsContentRestricted(contentRestriction);

                //should hopefully use the update maxPlaying method to update the historical max player count found
                robloxGame.updateMaxPlaying(robloxGame.getMaxPlaying(), players);
                // System.out.println(robloxGame.getMaxPlaying());

            }
            
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
        for(RobloxGame robloxGame : ourCollection){
            
            long universeID = robloxGame.getID();
            String url = buildLikesRequestURL(universeID);
            
            
            String json = getData(url);

            if(json == null){
                //if the response from the website didn't contain a 200
                //status code ie didn't send anything back for the program
                //to use
                System.out.println("Cound not retrieve data for: " + VOTES_URL);
            } else {
                //if a response came back
                
                //printing the json response
                // System.out.println("UpVotes: ");
                // System.out.println(json + "\n");

                //converts API response from java string into JSON structure
                JsonObject response = JsonParser.parseString(json).getAsJsonObject();

                //get(0) means selecting the first game in the list
                JsonObject gameData = response.getAsJsonArray("data").get(0).getAsJsonObject();

                int upVote = gameData.get("upVotes").getAsInt();
                int downVote = gameData.get("downVotes").getAsInt();

                robloxGame.setUpVotes(upVote);
                robloxGame.setDownVotes(downVote);

            }
            
        }

    }




    //lets RobloxGameCollection be read by other classes
    public RobloxGameCollection getCollection() {
        return ourCollection;
    }


}