import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Graph;

import java.io.IOException;
import java.util.*;

/*
Author: Kyle White
Course: CS401 Algorithms
Date: 6/18/2021
 */

/*
The LastFFMRecommender class provides several methods for listing friends, the top 10
artists in the catalog, finding common friends between users, and finding the top 10
artists within a friend network. Private fields include a Graph of user-friend relationships,
an EdgeWeightedDigraph of user-artist relationships, HashMaps of artists, users, and
user-graph IDs to keep track of a user's position in the userFriends graph. There
is a Comparator<Artist> for sorting purposes.
 */

public class LastFMRecommender {
    private Graph userFriends;
    private EdgeWeightedDigraph userArtists;
    private HashMap<Integer, Integer> graphIDUserID;
    private HashMap<Integer, Artist> artists;
    private HashMap<Integer, User> users;
    private Comparator<Artist> artistComparator;

    /*
    Default constructor uses a Reader object to read each data file which. The Reader
    provides methods to return the data storage fields of the LastFMRecommender class.
     */
    public LastFMRecommender() {
        try {
            // Pass the data file paths to the Reader.
            Reader reader = new Reader("artists.dat", "user_artists.dat", "user_friends.dat");
            // Set fields
            this.userFriends = new Graph(reader.getUserFriends());
            this.userArtists = new EdgeWeightedDigraph(reader.getUserArtists());
            this.artists = new HashMap<>(reader.getArtists());
            this.users = new HashMap<>(reader.getUsers());
            this.graphIDUserID = new HashMap<>(reader.getGraphIDUserID());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /*
    The listFriends method prints the list of friends of the user ID passed
    as a parameter.
    @param user The integer userID of the user
    @throws IndexOutOfBoundsException   if the user does not exist.
     */
    public Iterable<Integer> listFriends(int user) throws IndexOutOfBoundsException {
        System.out.println("Friends of User # " + user +" :\n");
        // Create a list of friends of the user, passing the user parameter
        // into the HashMap containing users, then getting the friends list from the
        // user object.
        // If the user does not exist, throw IndexOutOfBoundsException
        if(users.containsKey(user)) {
            List<Integer> userFriendsList = users.get(user).getFriends();
            // Return this list for app to print.
            return userFriendsList;
        } else {
            throw new IndexOutOfBoundsException("User does not exist.");
        }
        // Iterate and print the user IDs. (REMOVED, RETURNS LIST NOW)
//        for (int i = 0; i < userFriends.size(); i++) {
//            System.out.println(userFriends.get(i));
//        }
    }

    /*
    The commonFriends method accepts user1 and user2 as parameters, and prints
    a list of friends common between the two users.
    @param user1    The first user
    @param user2    The second user, which is used to find common friends.
    @throws IndexOutOfBoundsException   if a user does not exist.
     */
    public Iterable<Integer> commonFriends(int user1, int user2) throws IndexOutOfBoundsException {
        // If either user does not exist, throw exception.
        if(!users.containsKey(user1) || !users.containsKey(user2)) {
            throw new IndexOutOfBoundsException("A user does not exist.");
        }
        // Create lists of the friends of each user.
        List<Integer> user1Friends = users.get(user1).getFriends();
        List<Integer> user2Friends = users.get(user2).getFriends();
        // Obtain the intersection of the two lists using retainAll method.
        user1Friends.retainAll(user2Friends);
        System.out.println("There are " + user1Friends.size() + " common friends between user "
                + user1 + " and user " + user2 + ":\n");
        // Return this list so app can print it.
        return user1Friends;
//        // Iterate through the list and print out the user IDs. (REMOVED, RETURNS LIST NOW)
//        for (int i: user1Friends) {
//            System.out.println(i + "\n");
//        }
    }

    /*
    The listArtists method accepts user1 and user2 as parameters, and
    prints the list of artists listened to by both users.
    @param user1    The first user
    @param user2    The second user, which is used to find common artists.
    @throws IndexOutOfBoundsException   if a user does not exist.
     */
    public Iterable<Artist> listArtists(int user1, int user2) throws IndexOutOfBoundsException {
        // If either user does not exist, throw exception.
        if(!users.containsKey(user1) || !users.containsKey(user2)) {
            throw new IndexOutOfBoundsException("A user does not exist.");
        }
        // Create lists of the listened artists of each user.
        List<Integer> user1Artists = users.get(user1).getArtists();
        List<Integer> user2Artists = users.get(user2).getArtists();
        // Obtain the intersection of the two lists using retainAll method.
        user1Artists.retainAll(user2Artists);
        System.out.println("There are " + user1Artists.size() + " common artists between user "
                + user1 + " and user " + user2 + ":\n");
        // Iterate through the common artist ID list and populate a List<Artist> to return.
        List<Artist> commonArtists = new ArrayList<>();
        for (int i: user1Artists) {
            commonArtists.add(artists.get(i));
        }
        // Return the list of artists to print in the app.
        return commonArtists;
        // Iterate through the list and print out the artist IDs. (REMOVED, RETURNS LIST NOW)
//        for (int i: user1Artists) {
//            System.out.println(artists.get(i).toString());
//            System.out.println("----------------------------------------");
//        }
    }

    /*
    The listTop10 method prints out the top 10 of all artists.
     */
    public Iterable<Artist> listTop10() {
        // Instantiate a Comparator<Artist> object using a lambda expression
        // to define the comparison properties.
        artistComparator = (o1, o2) -> {
            if (o1.getListens() > o2.getListens()) {
                // if current object is greater, then return 1
                // this is for sorting in reverse order
                return -1;
            }
            else if (o1.getListens() < o2.getListens()) {
                // if current object is lesser, then return -1
                return 1;
            }
            else {
                // if current object is equal to o, then return 0
                return 0;
            }
        };
        // Create a new ArrayList<Artist> for sorting by weight (listens)
        ArrayList<Artist> artistsByWeight = new ArrayList<>();
        // Use the HashMap forEach method to iterate through the HashMap, adding each
        // artist to the ArrayList
        artists.forEach((k, v) -> {
            artistsByWeight.add(v);
        });
        // Use Collections.sort method to sort the ArrayList by the
        // parameters defined in the artistComparator
        Collections.sort(artistsByWeight, artistComparator);
        // Return this list for the client to print.
        return artistsByWeight;
//        System.out.println("The Top 10 Artists Are:\n");
//        // Iterate and print (REMOVED, RETURNS LIST NOW)
//        for (int i = 0; i < 10; i++) {
//            System.out.println(artistsByWeight.get(i));
//            System.out.println("---------------------------------------------");
//        }
    }


// OLD RECOMMEND10 METHOD THAT USED THE OVERALL ARTIST LISTEN COUNT, NOT
// THE USER FRIEND GROUP'S LISTEN COUNT NUMBERS.
//    /*
//    The recommend10 method accepts user as a parameter and recommends the top 10 artists
//    listened to by the user's friends (not including the artist list of the user themself,
//    but not excluding artists that may overlap).
//    @param user The user whose friend's most popular artists will be printed.
//    @throws IndexOutOfBoundsException   if the user does not exist.
//     */
//    public Iterable<Artist> recommend10OLD(int user) throws IndexOutOfBoundsException {
//        // Verify if user exists, throw exception if not.
//        if(!users.containsKey(user)) {
//            throw new IndexOutOfBoundsException("User does not exist.");
//        }
//        // Instantiate a Comparator<Artist> object using a lambda expression
//        // to define the comparison properties.
//        artistComparator = (o1, o2) -> {
//            if (o1.getListens() > o2.getListens()) {
//                // if current object is greater, then return 1
//                // this is for sorting in reverse order
//                return -1;
//            }
//            else if (o1.getListens() < o2.getListens()) {
//                // if current object is lesser, then return -1
//                return 1;
//            }
//            else {
//                // if current object is equal to o, then return 0
//                return 0;
//            }
//        };
//
//        // Create a list of friends from the parameter user
//        List<Integer> friends = users.get(user).getFriends();
//        // Create a HashSet<Integer> of artistIDs
//        HashSet<Integer> artistIDs = new HashSet<Integer>();
//        // Iterate through the friends from the friends list of the user
//        for(int i: friends) {
//            // For each friend, create a List<Integer> of THEIR listened artists
//            List<Integer> currentUserArtists = users.get(i).getArtists();
//            // Add all of these artists to the artistIDs HashSet, creating a list of
//            // the parameter user's friend's listened artists.
//            artistIDs.addAll(currentUserArtists);
//        }
//        // Create a List of Artist objects that can be sorted
//        List<Artist> friendArtists = new ArrayList<>();
//        // Iterate through the artistIDs list, get each artist object from the artists
//        // HashMap, then add that to the friendArtists list
//        for(int i: artistIDs) {
//            friendArtists.add(artists.get(i));
//        }
//        // Sort friendArtists by the parameters defined in artistComparator
//        Collections.sort(friendArtists, artistComparator);
//        System.out.println("The top 10 artists in user " + user + "'s friend group are: \n");
//        // Return so the app can print.
//        return friendArtists;
////        // Iterate and print (REMOVED, RETURNS LIST NOW)
////        for (int i = 0; i < 10; i++) {
////            System.out.println(friendArtists.get(i));
////            System.out.println("---------------------------------------------");
////        }
//    }


    /*
    The recommend10 method accepts user as a parameter and recommends the top 10 artists
    listened to by the user's friends (not including the artist list of the user themself,
    but not excluding artists that may overlap).
    @param user The user who's friend's most popular artists will be printed.
    @throws IndexOutOfBoundsException   if the user does not exist.
     */
    public Iterable<Artist> recommend10(int user) throws IndexOutOfBoundsException {
        // Verify if user exists, throw exception if not.
        if(!users.containsKey(user)) {
            throw new IndexOutOfBoundsException("User does not exist.");
        }
        // Instantiate a Comparator<Artist> object using a lambda expression
        // to define the reverse sorting comparison properties.
        artistComparator = (o1, o2) -> {
            if (o1.getListens() > o2.getListens()) {
                // if current object is greater, then return 1
                // this is for sorting in reverse order
                return -1;
            }
            else if (o1.getListens() < o2.getListens()) {
                // if current object is lesser, then return -1
                return 1;
            }
            else {
                // if current object is equal to o, then return 0
                return 0;
            }
        };
        // Create a list of friends from the parameter user
        List<Integer> friends = users.get(user).getFriends();
        // Create an artistListens HashMap to keep track of user's friend's listen counts.
        HashMap<Integer, Double> artistListens = new HashMap<>();
        // Create a HashSet<Integer> of artistIDs
        HashSet<Integer> artistIDs = new HashSet<Integer>();
        // Iterate through the friends from the friends list of the user
        for(int i: friends) {
            // For each friend, create a List<Integer> of THEIR listened artists
            List<Integer> currentUserArtists = users.get(i).getArtists();
            // Add all of these artists to the artistIDs HashSet, creating a list of
            // the parameter user's friend's listened artists.
            artistIDs.addAll(currentUserArtists);
        }
        // Create a List of Artist objects that can be sorted
        List<Artist> friendArtists = new ArrayList<>();
        // Iterate through the artistIDs list, get each artist object from the artists
        // HashMap, then add that to the friendArtists list
        for(int i: artistIDs) {
            friendArtists.add(artists.get(i));
        }

        // For each friend, get their artistListens HashMap and iterate through that HashMap
        // to add that friend's weight to the artistListens HashMap.
        for(int i: friends) {
            // For each friend, get their artist listen count
            HashMap<Integer, Double> friendArtistListens = users.get(i).getArtistListenList();
            // Use HashMap.forEach method to iterate through their listen counts.
            friendArtistListens.forEach((k, v) -> {
                // If the artist exists, add the listens.
                if (artistListens.containsKey(k)) {
                    artistListens.put(k, artistListens.get(k) + v);
                } else {
                    // If the artist does not exist, create a new key, value pair in the artistListens
                    // HashMap.
                    artistListens.put(k, v);
                }
            });
        }
        // For each artist in friendArtists, set their listens to the friend group's listens.
        for(Artist a: friendArtists) {
            a.setListens(artistListens.get(a.getId()));
        }
        // Sort friendArtists by the parameters defined in artistComparator
        Collections.sort(friendArtists, artistComparator);
        System.out.println("The top 10 artists in user " + user + "'s friend group are: \n");
        // Return so the app can print.
        return friendArtists;
    }

    /*
    Accessor method for the artists field.
    @return artists
     */
    public HashMap<Integer, Artist> getArtists() {
        return artists;
    }

    /*
    Accessor method for the users field.
    @return users
     */
    public HashMap<Integer, User> getUsers() {
        return users;
    }
}

