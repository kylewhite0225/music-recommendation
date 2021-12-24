import java.util.ArrayList;
import java.util.HashMap;

/*
Author: Kyle White
Course: CS401 Algorithms
Date: 6/18/2021
 */

/*
The User class holds the userID, a list of friend IDs, a list of listened artist IDs,
and a graphID which is the association parameter for the user's vertex in the user-listens graph.
 */

public class User {
    private int userID;
    private ArrayList<Integer> friendIDs;
    private ArrayList<Integer> artistIDs;
    private HashMap<Integer, Double> artistListens;
    private int graphID;

    /*
    Constructor accepts an integer userID as a parameter and sets the userID, and instantiates
    both the friendID list and artistID list.
    @param userID   ID of the new user
     */
    public User(int userID) {
        this.userID = userID;
        this.friendIDs = new ArrayList<>();
        this.artistIDs = new ArrayList<>();
        this.artistListens = new HashMap<>();
    }

    /*
    The addFriend method adds a new friend to the user's list of friends.
    @param id   ID of the new friend to be added.
     */
    public void addFriend(int id) {
        friendIDs.add(id);
    }

    /*
    The addArtist method adds a new artist to the user's listening list.
    @param id   ID of the artist to add.
     */
    public void addArtist(int id) {
        artistIDs.add(id);
    }

    /*
    Accessor method which returns the user's list of friends.
    @return friendIDs   The ArrayList of friend user IDs
     */
    public ArrayList<Integer> getFriends() {
        return friendIDs;
    }

    /*
    Accessor method which returns the user's list of artists that they listen to.
    @return artistIDs   The ArrayList of artist IDs.
     */
    public ArrayList<Integer> getArtists() {
        return artistIDs;
    }

    /*
    Accessor method that returns the userID.
    @return userID  ID of the user.
     */
    public int getID() {
        return userID;
    }

    /*
    Accessor method for the graphID of the user.
    @return graphID The graph vertex number of the user.
     */
    public int getGraphID() {
        return graphID;
    }

    /*
    Setter method for the graphID of the user.
    @param graphID  The graph vertex number of the user.
     */
    public void setGraphID(int graphID) {
        this.graphID = graphID;
    }

    /*
    This method adds an artist listen count of the user to the artistListens HashMap.
    @param artistID The artist ID of the artist this user listens to.
    @param listens  This user's listen count for the given artist.
     */
    public void addArtistListens(int artistID, double listens) {
        artistListens.put(artistID, listens);
    }

    /*
    This method accesses an artist listen count by the user.
    @param artistID ID of the artist.
    @return artistListens   The listen count by this user of this artist.
    @throws IndexOutOfBoundsException   if the user does not listen to the parameter artistID.
     */
    public double getArtistListens(int artistID) throws IndexOutOfBoundsException {
        if (artistListens.containsKey(artistID)) {
            return artistListens.get(artistID);
        } else {
            throw new IndexOutOfBoundsException("This user does not listen to this artist.");
        }
    }

    /*
    Accessor method for artistListens.
    @return artistListens
     */
    public HashMap<Integer, Double> getArtistListenList() {
        return artistListens;
    }
}
