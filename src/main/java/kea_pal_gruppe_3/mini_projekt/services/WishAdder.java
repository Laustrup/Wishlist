package kea_pal_gruppe_3.mini_projekt.services;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishAdder {

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
                " VALUES (" + name + ", " + author + ");");
        statement.executeUpdate();
        System.out.println("Wishlist added to database!");
    }

    private void executeUpdateWishes(ArrayList<Wish> wishlist,Connection connection,
                                     PreparedStatement statement) throws SQLException {
        for (int i = 0; i < wishlist.size(); i++) {
            PreparedStatement wishStatement = connection.prepareStatement("INSERT INTO wish(wish, url)" +
                    " VALUES (" + wishlist.get(i).getWish() + ", " + wishlist.get(i).getUrl() + ");");
            statement.executeUpdate();
            System.out.println("Wish added to database!");
        }
    }

}
