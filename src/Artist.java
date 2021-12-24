import java.net.URL;

/*
Author: Kyle White
Course: CS401 Algorithms
Date: 6/18/2021
 */

/*
The Artist class holds the ID, name, URL, and listen count of an artist.
 */

public class Artist implements Comparable<Artist> {
    private int id;
    private String name;
    private URL url;
    private double listens;

    /*
    Constructor accepts id, name, and url as parameters to populate all fields and set listens to 0.
    @param id   Artist ID
    @param name Artist Name
    @param url  Artist's LastFM URL
     */
    public Artist(int id, String name, URL url) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.listens = 0;
    }

    /*
    Accessor method for the ID of the artist.
    @return id  Artist ID.
     */
    public int getId() {
        return id;
    }

    /*
    Setter method for the ID of the artist.
    @param id   Artist ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /*
    Accessor method for the name of the artist.
    @return name  Artist name.
     */
    public String getName() {
        return name;
    }

    /*
    Setter method for the name of the artist.
    @param name   Artist name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
    Accessor method for the URL of the artist.
    @return url  Artist's LastFM URL.
     */
    public URL getUrl() {
        return url;
    }

    /*
    Setter method for the URL of the artist.
    @param url   Artist's LastFM URL.
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /*
    Accessor method for the listen count of the artist.
    @return listens  Artist's listen count.
     */
    public double getListens() {
        return listens;
    }

    /*
    Setter method for the listen count of the artist.
    @param listens   Artist's listen count.
     */
    public void setListens(double listens) {
        this.listens = listens;
    }

    /*
    The addListens method accepts a double as a parameter and adds this to the listen count.
    @param listens  The listen count to add.
     */
    public void addListens(double listens) {
        this.listens += listens;
    }

    /*
    Override compareTo method for sorting purposes.
     */
    @Override
    public int compareTo(Artist o) {
        if (this.id > o.id) {
            // if current object is greater, then return 1
            return 1;
        }
        else if (this.id < o.id) {
            // if current object is lesser, then return -1
            return -1;
        }
        else {
            // if current object is equal to o, then return 0
            return 0;
        }
    }

    /*
    Override toString method for output of artist information.
     */
    @Override
    public String toString() {
        return "Artist Name: " + name + "\n" + "Artist ID: " + id +
                 "\n" + "Number of Listens: " + listens + "\n" +
                "LastFM URL: " + url;
    }
}
