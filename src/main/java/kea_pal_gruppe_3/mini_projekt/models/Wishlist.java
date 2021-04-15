package kea_pal_gruppe_3.mini_projekt.models;

import java.util.ArrayList;

public class Wishlist {

    private int idWishlist;
    private String name;
    private String author;
    private ArrayList<Wish> wishlist;

    public Wishlist(int idWishlist, String name, String author, ArrayList<Wish> wishlist) {

        this.idWishlist = idWishlist;
        this.name = name;
        this.author = author;
        this.wishlist = wishlist;
    }

    public int getIdWishlist() {
        return idWishlist;
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

    public String printWishes() {

        String res = new String();

        for (int i = 0; i < wishlist.size(); i++) {
            res += wishlist.get(i).getWish() + "\n";
        }
        return res;
    }

}
