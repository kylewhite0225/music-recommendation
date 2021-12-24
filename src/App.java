import java.util.HashMap;
import java.util.List;

/*
Author: Kyle White
Class: CS401 Algorithms
Date: 6/18/2021
 */

/*
This class is for testing purposes, to print and verify that the
output of each method in LastFMRecommender is correct.
 */

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to the LastFM Recommender!\n");
        LastFMRecommender rec = new LastFMRecommender();

//        // Create top10 list, iterate, and print.
//        List<Artist> top10 = (List<Artist>) rec.listTop10();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(top10.get(i));
//            System.out.println("---------------------------------------------");
//        }
//
//        // Create list of friends of user 95, iterate, and print.
//        List<Integer> friends = (List<Integer>) rec.listFriends(95);
//        // System.out.println("Friends of user # 95: \n");
//        for (int i = 0; i < friends.size(); i++) {
//            System.out.println(friends.get(i));
//        }
//
//        // Create list of artists, retrieve common artists for users 2 and 4, iterate, and print.
//        HashMap<Integer, Artist> artists = rec.getArtists();
//        List<Artist> commonArtists = (List<Artist>) rec.listArtists(2, 4);
//        for (Artist i: commonArtists) {
//            System.out.println(i);
//            System.out.println("----------------------------------------");
//        }
//
//        // Create list of common friends for users 4 and 31, iterate, and print.
//        List<Integer> commonFriends = (List<Integer>) rec.commonFriends(4, 31);
//        for (int i: commonFriends) {
//            System.out.println(i);
//        }
//
//        // Create list of recommended artists for user 2, iterate, and print.
//        List<Artist> rec10 = (List<Artist>) rec.recommend10(2);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(rec10.get(i));
//            System.out.println("---------------------------------------------");
//        }

        // Create list of recommended artists for user 2, iterate, and print.
        List<Artist> rec10 = (List<Artist>) rec.recommend10(2);
        for (int i = 0; i < 10; i++) {
            System.out.println(rec10.get(i));
            System.out.println("---------------------------------------------");
        }
    }
}
