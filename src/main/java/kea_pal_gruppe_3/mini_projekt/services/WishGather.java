package kea_pal_gruppe_3.mini_projekt.services;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;

public class WishGather {

    public ArrayList<Wishlist> talkToDatabase(ArrayList<Wish> wishes, ArrayList<Wishlist> wishlists,
                                              ResultSet res) throws SQLException {

        int prev = 1;
        Wishlist wishToAdd = new Wishlist(null,null,null);
        // Fills in the Wishlist to be returned without wishes
        gatherFromDatabase(res,wishes,wishlists,prev,wishToAdd);

        return wishlists;
    }

    private ArrayList<Wishlist> gatherFromDatabase(ResultSet res, ArrayList<Wish> wishes, ArrayList<Wishlist> wishlists,
                                                   int prev, Wishlist wishToAdd) throws SQLException {

        String name = new String();
        String author = new String();

        while(res.next()) {
            if (res.getInt(1) > prev || res.isLast()) {
                if (res.isLast()) {
                    wishes.add(new Wish(res.getString(6),res.getString(7)));
                    System.out.println("Wish added to wishes... " + res.getString(6) + " - " + res.getString(7));
                }
                wishToAdd = new Wishlist(name, author, wishes);
                System.out.println("\nwishToAdd created!");

                wishlists.add(wishToAdd);
                System.out.println("\nWishlist updated with wishes!");
                wishes = new ArrayList<>();
                System.out.println("Wishes zeroed!\n");
            }

            if (!res.isLast()) {
                wishes.add(new Wish(res.getString(6),res.getString(7)));
                System.out.println("Wish added to wishes... " + res.getString(6) + " - " + res.getString(7));
            }


            //Before nextline, the following values are kept of this line
            name = res.getString(2);
            author = res.getString(3);
            System.out.println("\nName and authors = " + name + " - " + author);
            prev = res.getInt(1);
            System.out.println("Previous = " + prev + "\n");
        }
        return wishlists;
    }
}
