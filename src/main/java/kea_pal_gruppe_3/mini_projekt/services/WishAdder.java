package kea_pal_gruppe_3.mini_projekt.services;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;

import java.sql.*;
import java.util.ArrayList;

public class WishAdder {

    WishlistRepo repo = new WishlistRepo();

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
        System.out.println("Wishlist added to database!");
    }

    private void executeUpdateWishes(ArrayList<Wish> wishlist,Connection connection,
                                     PreparedStatement statement) throws SQLException {

        int wishlistId = determineId_Wishlist();

        if (wishlistId != -1) {
            for (int i = 0; i < wishlist.size(); i++) {
                statement = connection.prepareStatement("INSERT INTO wish(id_wishlist,wish, url)" +
                        " VALUES (" + wishlistId + ",\"" + wishlist.get(i).getWish() + "\", \"" + wishlist.get(i).getUrl() + "\");");
                statement.executeUpdate();
                System.out.println("Wish added to database!");
            }
        }
        else {
            System.out.println("Couldn't determine wishlist_id...");
        }
    }

    private int determineId_Wishlist() throws SQLException {
        repo.getAllWishlists();
        ResultSet res = repo.getRes();

        while(res.next()) {
            if (res.isLast()) {
                return res.getInt(1);
            }
        }
        return -1;
    }

}
