import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Author: Kyle White
Course: CS401 Algorithms
Date: 6/18/2021
 */

public class LastFMRecommenderTest {
    private static LastFMRecommender rec;
    private static HashMap<Integer, Artist> artists;
    private List<Artist> top10;
    private List<Integer> friends;
    private List<Artist> commonArtists;
    private List<Integer> commonFriends;
    private List<Artist> rec10;

    @BeforeAll
    public static void setUp() {
        rec = new LastFMRecommender();
        artists = rec.getArtists();
    }

    @Test
    public void testTop10() {
        top10 = (List<Artist>) rec.listTop10();
        assertTrue(top10.contains(artists.get(289)));
        assertTrue(top10.contains(artists.get(72)));
        assertTrue(top10.contains(artists.get(89)));
        assertTrue(top10.contains(artists.get(292)));
        assertTrue(top10.contains(artists.get(498)));
        assertTrue(top10.contains(artists.get(67)));
        assertTrue(top10.contains(artists.get(288)));
        assertTrue(top10.contains(artists.get(701)));
        assertTrue(top10.contains(artists.get(227)));
        assertTrue(top10.contains(artists.get(300)));
    }

    @Test
    public void testFriends() {
        friends = (List<Integer>) rec.listFriends(95);
        assertEquals(6, friends.size());
        assertTrue(friends.contains(527));
        assertTrue(friends.contains(671));
        assertTrue(friends.contains(701));
        assertTrue(friends.contains(966));
        assertTrue(friends.contains(1104));
        assertTrue(friends.contains(1369));
    }

    @Test
    public void testFriendsException() {
        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.listFriends(3000);
        });
        assertTrue(e.getMessage().contains("User does not exist."));
    }

    @Test
    public void testCommonArtists() {
        commonArtists = (List<Artist>) rec.listArtists(2, 4);
        assertEquals(7, commonArtists.size());
        assertTrue(commonArtists.contains(artists.get(51)));
        assertTrue(commonArtists.contains(artists.get(53)));
        assertTrue(commonArtists.contains(artists.get(64)));
        assertTrue(commonArtists.contains(artists.get(65)));
        assertTrue(commonArtists.contains(artists.get(70)));
        assertTrue(commonArtists.contains(artists.get(72)));
        assertTrue(commonArtists.contains(artists.get(77)));
    }

    @Test
    public void testCommonArtistsException() {
        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.listArtists(3000, 2);
        });
        assertTrue(e.getMessage().contains("A user does not exist."));

        e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.listArtists(2, 3000);
        });
        assertTrue(e.getMessage().contains("A user does not exist."));

        e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.listArtists(3000, 3000);
        });
        assertTrue(e.getMessage().contains("A user does not exist."));
    }

    @Test
    public void testCommonFriends() {
        commonFriends = (List<Integer>) rec.commonFriends(4, 31);
        assertEquals(4, commonFriends.size());
        assertTrue(commonFriends.contains(99));
        assertTrue(commonFriends.contains(211));
        assertTrue(commonFriends.contains(520));
        assertTrue(commonFriends.contains(534));
    }

    @Test
    public void testCommonFriendsException() {
        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.commonFriends(3000, 2);
        });
        assertTrue(e.getMessage().contains("A user does not exist."));

        e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.commonFriends(2, 3000);
        });
        assertTrue(e.getMessage().contains("A user does not exist."));

        e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.commonFriends(3000, 3000);
        });
        assertTrue(e.getMessage().contains("A user does not exist."));
    }

// OLD TEST METHOD BEFORE THE RECOMMEND10 METHOD WAS UPDATED TO ONLY
// INCLUDE USER LISTENS, NOT OVERALL LISTEN COUNTS. THIS TEST WILL FAIL IF UNCOMMENTED.
//    @Test
//    public void testRec10() {
//        rec10 = (List<Artist>) rec.recommend10(4);
//        assertTrue(rec10.contains(artists.get(289)));
//        assertTrue(rec10.contains(artists.get(72)));
//        assertTrue(rec10.contains(artists.get(89)));
//        assertTrue(rec10.contains(artists.get(292)));
//        assertTrue(rec10.contains(artists.get(67)));
//        assertTrue(rec10.contains(artists.get(288)));
//        assertTrue(rec10.contains(artists.get(701)));
//        assertTrue(rec10.contains(artists.get(300)));
//        assertTrue(rec10.contains(artists.get(333)));
//        assertTrue(rec10.contains(artists.get(378)));
//    }

    @Test
    public void testRec10() {
        rec10 = (List<Artist>) rec.recommend10(2);
        assertTrue(rec10.contains(artists.get(51)));
        assertTrue(rec10.contains(artists.get(72)));
        assertTrue(rec10.contains(artists.get(1246)));
        assertTrue(rec10.contains(artists.get(1104)));
        assertTrue(rec10.contains(artists.get(67)));
        assertTrue(rec10.contains(artists.get(511)));
        assertTrue(rec10.contains(artists.get(159)));
        assertTrue(rec10.contains(artists.get(1001)));
        assertTrue(rec10.contains(artists.get(993)));
        assertTrue(rec10.contains(artists.get(2562)));
    }

    @Test
    public void testRec10Exception() {
        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            rec.recommend10(3000);
        });
        assertTrue(e.getMessage().contains("User does not exist."));
    }
}
