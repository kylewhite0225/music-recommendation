import edu.princeton.cs.algs4.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
Author: Kyle White
Course: CS401 Algorithms
Date: 6/18/2021
 */

/*
The Reader class uses the file paths for the three provided data files to create sets and graphs
of users, artists, user-friend relationships, user-artist relationships, and graphIDs of the users.
 */

public class Reader {
    private HashMap<Integer, Artist> artists;
    private EdgeWeightedDigraph user_artists;
    private Graph user_friends;
    private HashMap<Integer, User> users;
    private HashMap<Integer, Integer> graphIDUserID;

    /*
    The default constructor accepts three parameters, artistList, user_artistsList, and
    user_friendsList, which are strings containing the filepath of each data file. It uses
    the private helper methods readArtists, readUserArtists, and readUserFriends to populate
    the data structures in this class.
    @param artistList   The filepath to the artist.dat file.
    @param user_artistsList The filepath to the user_artists.dat file.
    @param user_FriendsList The filepath to the user_friends.dat file.
    @throws IOException if there is an error during IO operations.
     */
    public Reader(String artistList, String user_artistsList, String user_friendsList) throws IOException {
        readArtists(artistList);
        readUserArtists(user_artistsList);
        readUserFriends(user_friendsList);
    }

    /*
    The private readUserFriends method accepts the user_friendsList file path as a parameter,
    and builds the user_friends graph and graphIDUserID HashMaps.
    @param user_FriendsList The filepath to the user_friends.dat file.
    @throws IOException if there is an error during IO operations.
     */
    private void readUserFriends(String user_friendsList) throws IOException {
        // Create new BufferedReader object with a FileReader as a parameter for file reading.
        BufferedReader reader = new BufferedReader(new FileReader(user_friendsList));
        String line = "";
        // Line format: userID \t friendID
        // Create new graph using the number of users as the number of vertices.
        user_friends = new Graph(users.size());
        // Create new HashMap for storing the associated userID with their vertex number in the graph.
        graphIDUserID = new HashMap<>();
        // Read through the file using reader.readLine method
        while((line = reader.readLine()) != null) {
            // Split the line into a String array by the tab delimiter
            String[] splitLine = line.split("\\t");
            // Skip the first line
            if(splitLine[0].equals("userID")) {
                continue;
            }
            // Parse the userID
            int userID = Integer.parseInt(splitLine[0]);
            // Get the graphID from the user object created in the readUserArtists method.
            int userGraphID = users.get(userID).getGraphID();
            // Parse the friendID
            int friendID = Integer.parseInt(splitLine[1]);
            // Get the graphID from the user object created in the readUserArtists method.
            int friendGraphID = users.get(friendID).getGraphID();
            // Use the user.addFriend method to add the friendID to the current user object
            users.get(userID).addFriend(friendID);
            // TODO
            // Try and figure out a way to filter out duplicates and only add unique values.
            // Bag is pretty useless.
            // Add an edge between the user and their friend.
            user_friends.addEdge(userGraphID, friendGraphID);
            // Add the current userID to the graphIDUserID HashMap using userGraphID as the key
            graphIDUserID.put(userGraphID, userID);
        }
    }

    /*
    The readUserArtists method accepts the user_artistsList filepath as a parameter,
    and builds the user_artists EdgeWeightedDigraph and the users HashMap.
    @param user_artistsList The user_artists.dat filepath.
    @throws IOException if there are any errors during IO operations.
     */
    private void readUserArtists(String user_artistsList) throws IOException {
        BufferedReader reader = new BufferedReader((new FileReader(user_artistsList)));
        String line = "";
        // Hashmap of userID (Integer) keys and Bag<DirectedEdge> values which contain the
        // user-artist edges that will go into the user_artists EdgeWeightedDigraph
        HashMap<Integer, Bag<DirectedEdge>> edges = new HashMap<>();
        users = new HashMap<>();
        // Create a counter to associate each user with their place in the users graph
        int count = 0;
        // Iterate through each line of the .dat file.
        while ((line = reader.readLine()) != null) {
            // A Bag of DirectedEdges that will be iterated through after completing each user's listen list
            Bag<DirectedEdge> bagOfEdges;
            // Split line by tab
            String[] splitLine = line.split("\\t");
            // Skip first line
            if(splitLine[0].equals("userID")) {
                continue;
            }
            // Parse userID, artistID, and weight from line
            int userID = Integer.parseInt(splitLine[0]);
            int artistID = Integer.parseInt(splitLine[1]);
            double weight = Double.parseDouble(splitLine[2]);
            // If the user HashMap does not contain the current user, add the current artist
            // to the user and then add the user into the HashMap
            if (!users.containsKey(userID)) {
                // Create new User object
                User user = new User(userID);
                // The user's position in the friendship graph is the current count
                user.setGraphID(count);
                // Add the current artist to the user's listening profile
                user.addArtist(artistID);
                // Add the userID and user objects to the users HashMap
                users.put(userID, user);
                // Increase count
                count++;
                // Add this user's listen count to the user's artistListens HashMap
                user.addArtistListens(artistID, weight);
            } else {
                // Else, add the artist to the listen list of the user matching the ID already
                // in the HashMap.
                users.get(userID).addArtist(artistID);
                // Add this user's listen count to the user's artistListens HashMap
                users.get(userID).addArtistListens(artistID, weight);
            }
            // Create a new DirectedEdge between the user and artist, with the weight as
            // the listening time
            DirectedEdge edge = new DirectedEdge(userID, artistID, weight);
            // If the edges HashMap does not contain the user
            if (!edges.containsKey(userID)) {
                // Create a new Bag for storing DirectedEdges
                bagOfEdges = new Bag<>();
                // Add the current edge to the Bag
                bagOfEdges.add(edge);
                // Place the userID / bagOfEdges combo into the edges HashMap.
                edges.put(userID, bagOfEdges);
            } else {
                // Else, add the edge to the Bag of the user matching the ID in the HashMap.
                edges.get(userID).add(edge);
            }
            // Update the total listen weight of the artist in this line.
            artists.get(artistID).addListens(weight);
        }

        // Gather the number of vertices required (number of users = number of entries in the
        // edges hashmap, number of artists = number of artists in the artists HashMap).
        int vertices = edges.size() + artists.size();
        // Instantiate a new EdgeWeightedDigraph using this number.
        user_artists = new EdgeWeightedDigraph(vertices);
        // Use the HashMap forEach function to iterate through the key-value pairs in the HashMap
        edges.forEach((k, v) -> {
            // Use a for each loop to iterate through the Bag of DirectedEdges that is stored in
            // the HashMap for each userID.
            for (DirectedEdge e: v) {
                // Add each edge in the bag to the EdgeWeightedDigraph
                user_artists.addEdge(e);
            }
        });
    }

    /*
    The readArtists method accepts the artistList filepath as a parameter,
    reads each line of the fine, and then builds the HashMap of artists.
    @param artistList   The filepath of artists.dat.
    @throws IOException if there are any errors during IO operations.
     */
    private void readArtists(String artistList) throws IOException {
        // Create new BufferedReader object for file reading.
        BufferedReader reader = new BufferedReader(new FileReader(artistList));
        artists = new HashMap<>();
        String line = "";
        // Iterate through file line by line
        while ((line = reader.readLine()) != null) {
            // Split line by tab delimiter
            String[] splitLine = line.split("\\t");
            // Skip the first line.
            if (splitLine[0].equals("id")) {
                continue;
            }
            // Parse the artist ID from the line
            int id = Integer.parseInt(splitLine[0]);
            // Parse the URL from the line and create a new URL object.
            URL url = new URL(splitLine[2]);
            // Create a new Artist object with the ID, name, and URL.
            Artist artist = new Artist(id, splitLine[1], url);
            // Add the artist to the artists HashMap
            artists.put(id, artist);
        }
    }

    /*
    Accessor method for the artists HashMap.
    @returns artists    The artists HashMap<Integer, Artist>
     */
    public HashMap<Integer, Artist> getArtists() {
        return artists;
    }

    /*
    Accessor method for the user_artist EdgeWeightedDigraph.
    @returns user_artists   The user-artist listen relationship Edge Weighted Digraph
     */
    public EdgeWeightedDigraph getUserArtists() {
        return user_artists;
    }

    /*
    Accessor method for the user_friends graph.
    @returns user_friends   The user-friend relationship graph.
     */
    public Graph getUserFriends() {
        return user_friends;
    }

    /*
    Accessor method for the users HashMap.
    @return users   The user ID HashMap<Integer, User>
     */
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    /*
    Accessor method for the graphIDUserID HashMap which associates each userID with their
    vertex number in the user_friends graph (because the user IDs are not perfectly consecutive).
    @return graphIDUserID   The association HashMap<Integer, Integer> to keep track of user graph position.
     */
    public HashMap<Integer, Integer> getGraphIDUserID() {
        return graphIDUserID;
    }
}
