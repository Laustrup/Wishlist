package kea_pal_gruppe_3.mini_projekt.models;

import java.util.ArrayList;

public class Wishlist {

    private String name;
    private ArrayList<Wish> wishlist = new ArrayList<>();

    public Wishlist(String name, ArrayList<Wish> wishlist) {
        this.name = name;
        this.wishlist = wishlist;
    }
}
