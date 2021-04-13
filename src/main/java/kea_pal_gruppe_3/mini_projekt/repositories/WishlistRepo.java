package kea_pal_gruppe_3.mini_projekt.repositories;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;

//@Author Laust
public class WishlistRepo {

    private Wishlist wishlist;

    public ArrayList<Wishlist> getAllWishlists() {

        // An arraylist to gather every wishes pr. wishlist into wishlist
        ArrayList<Wish> wishes = new ArrayList<>();
        // Wishlist to be returned
        ArrayList<Wishlist> wishlists = new ArrayList<>();

        try {
            wishlists = talkToDatabase(wishes, wishlists);
        }
        catch(SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("\nSomething went wrong at talkToDataBase...");
        }
        return wishlists;
    }

    private ArrayList<Wishlist> talkToDatabase(ArrayList<Wish> wishes, ArrayList<Wishlist> wishlists) throws SQLException {
        // Communicates with MySQL
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniprojekt",
                "root", "Hemmeligt");
        // Makes an satement to each tables and gather the results into resultsets
        PreparedStatement wishlistTable = connection.prepareStatement("SELECT * FROM wishlist;");
        ResultSet wishListRes = wishlistTable.executeQuery();

        PreparedStatement wishTable = connection.prepareStatement("SELECT * FROM wish;");
        ResultSet wishRes = wishTable.executeQuery();

        // Fills in the Wishlist to be returned without wishes
        while(wishListRes.next()) {
            wishlists.add(new Wishlist(wishListRes.getString(1),wishListRes.getString(2), new ArrayList<>()));
        }

        int prevId = 1;
        int currentWishlist = 1;
        // Puts wishes into wishlists
        while(wishRes.next()){
            wishes.add(new Wish(wishRes.getString(3), wishRes.getString(4)));

            if (wishRes.getInt(2) != prevId) {
                wishlists.get(currentWishlist).setWishlist(wishes);
                wishlist = new Wishlist(wishListRes.getString(2), wishListRes.getString(3), wishes);
                System.out.println("Wishlist added to wishlist arraylist!");
                currentWishlist++;
                wishes = new ArrayList<>();
            }
            prevId = wishRes.getInt(2);
        }
        return wishlists;
    }

    public Wishlist putInWishlist(String name, String author, ArrayList<Wish> wishlist) {

        // empty temp Wishlist to return
        Wishlist newWishlist = null;

        try {
            setDatabase(newWishlist,name,author,wishlist);
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

    private Wishlist setDatabase(Wishlist newWishlist, String name, String author, ArrayList<Wish> wishlist) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniprojekt",
                "root", "Hemmeligt");
        PreparedStatement wishListStatement = connection.prepareStatement("INSERT INTO wishlist(name, author)" +
                " VALUES (" + name + ", " + author + ");");
        wishListStatement.executeUpdate();
        System.out.println("Wishlist added to database!");

        for (int i = 0; i < wishlist.size(); i++) {
            PreparedStatement wishStatement = connection.prepareStatement("INSERT INTO wish(wish, url)" +
                    " VALUES (" + wishlist.get(i).getWish() + ", " + wishlist.get(i).getUrl() + ");");
            wishListStatement.executeUpdate();
            System.out.println("Wish added to database!");
        }

        return new Wishlist(name,author,wishlist);
    }
}
