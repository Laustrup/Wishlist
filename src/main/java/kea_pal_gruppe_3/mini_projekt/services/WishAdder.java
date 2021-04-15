package kea_pal_gruppe_3.mini_projekt.services;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;

public class WishAdder {

    WishGather wishGather = new WishGather();

    public Wishlist setDatabase(String name, String author,
                                ArrayList<Wish> wishlist, Connection connection,
                                PreparedStatement statement) throws SQLException {

        executeUpdateWishlist(name,author,connection,statement);

        executeUpdateWishes(wishlist,connection,statement);

        return new Wishlist(name,author,wishlist);
    }

    private void executeUpdateWishlist(String name, String author, Connection connection,
                               PreparedStatement statement) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://13.53.216.245:3306/miniprojekt",
                "remote", "1234");
        statement = connection.prepareStatement("INSERT INTO wishlist(name, author)" +
                " VALUES (\"" + name + "`\", \"" + author + "\");");
        statement.executeUpdate();
        System.out.println(name + " added to database!");
    }

    private void executeUpdateWishes(ArrayList<Wish> wishes,Connection connection,
                                     PreparedStatement statement) throws SQLException {

        int wishlistId = determineId_Wishlist();
        System.out.println("wishlistId is determined to be " + wishlistId + " and wishes.size() equals " + wishes.size());

        if (wishlistId != -1) {
            for (int i = 0; i < wishes.size(); i++) {
                System.out.println("Variables are " + wishes.get(i).getWish() + wishes.get(i).getUrl());
                statement = connection.prepareStatement("INSERT INTO wish(id_wishlist,wish, url)" +
                        " VALUES (" + wishlistId + ",\"" + wishes.get(i).getWish() + "\", \"" + wishes.get(i).getUrl() + "\");");
                statement.executeUpdate();
                System.out.println(wishes.get(i).getWish() + " added to database!");
            }
        }
        else {
            System.out.println("Couldn't determine wishlist_id...");
        }
    }

    private int determineId_Wishlist() throws SQLException {

        ResultSet res = wishGather.executeQueryOfWishlist();

        while(res.next()) {
            if (res.isLast()) {
                return res.getInt(1);
            }
        }
        return -1;
    }

}