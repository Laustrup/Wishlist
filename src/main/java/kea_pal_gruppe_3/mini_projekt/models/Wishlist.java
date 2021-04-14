package kea_pal_gruppe_3.mini_projekt.models;

import java.util.ArrayList;

public class Wishlist {

    private String name;
    private String author;
    private ArrayList<Wish> wishlist;

    public Wishlist(String name, String author, ArrayList<Wish> wishlist) {
        this.name = name;
        this.author = author;
        this.wishlist = wishlist;
    }

    public ArrayList<Wish> getWishlist() {
        return wishlist;
    }

    public void setWishlist(ArrayList<Wish> wishlist) {
        this.wishlist = wishlist;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

}
