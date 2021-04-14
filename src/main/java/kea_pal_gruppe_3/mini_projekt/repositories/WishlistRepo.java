package kea_pal_gruppe_3.mini_projekt.repositories;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.services.WishAdder;
import kea_pal_gruppe_3.mini_projekt.services.WishGather;

import java.sql.*;
import java.util.ArrayList;

//@Author Laust
public class WishlistRepo {

    private Wishlist wishlist;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet res;

    private WishGather wishGather = new WishGather();
    private WishAdder wishAdder = new WishAdder();

    public ArrayList<Wishlist> getAllWishlists() {

        // An arraylist to gather every wishes pr. wishlist into wishlist
        ArrayList<Wish> wishes = new ArrayList<>();
        // Wishlist to be returned
        ArrayList<Wishlist> wishlists = new ArrayList<>();

        try {
            executeQuery();
            wishlists = wishGather.talkToDatabase(wishes, wishlists,res);
        }
        catch(SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("\nSomething went wrong... " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return wishlists;
    }

    private void executeQuery() throws SQLException {
        // Communicates with MySQL
        connection = DriverManager.getConnection("jdbc:mysql://13.53.216.245:3306/miniprojekt",
                "remote", "1234");
        // Makes an statement to each tables and gather the results into resultsets

        statement = connection.prepareStatement("SELECT * FROM wishlist\n" +
                "INNER JOIN wish\n" +
                "ON wishlist.id_wishlist = wish.id_wishlist;");
        System.out.println("\nStatement prepared...");
        res = statement.executeQuery();
        System.out.println("Result gathered...\n");
    }

    public Wishlist putInWishlist(String name, String author, ArrayList<Wish> wishlist) {

        // empty temp Wishlist to return
        Wishlist newWishlist = null;

        try {
            wishAdder.setDatabase(name,author,wishlist,connection,statement);
        }
        catch (SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("\nSomething went wrong at setDataBase...");
        }

        // returns an Employee based on the infos, in order to show the user via. model, the created employee.
        return newWishlist;
    }

}
