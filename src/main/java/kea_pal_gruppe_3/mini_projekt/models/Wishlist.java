package kea_pal_gruppe_3.mini_projekt.models;

import java.util.ArrayList;

public class Wishlist {

    private int idWishlist;
    private String name;
    private String author;
    private ArrayList<Wish> listOfWishes;

    public Wishlist(int idWishlist, String name, String author, ArrayList<Wish> wishlist) {

        this.idWishlist = idWishlist;
        this.name = name;
        this.author = author;
        this.listOfWishes = wishlist;
    }

    public int getId() {
        return idWishlist;
    }

    public ArrayList<Wish> getListOfWishes() {
        return listOfWishes;
    }

    public void setListOfWishes(ArrayList<Wish> listOfWishes) {
        this.listOfWishes = listOfWishes;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String printWishes() {

        String res = new String();

        for (int i = 0; i < listOfWishes.size(); i++) {
            res += listOfWishes.get(i).getWish() + " ";
        }
        return res;
    }
}
