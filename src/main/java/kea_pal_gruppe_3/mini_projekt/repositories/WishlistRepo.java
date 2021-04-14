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
            System.out.println("\nSomething went wrong at talkToDataBase... " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return wishlists;
    }

    private ArrayList<Wishlist> talkToDatabase(ArrayList<Wish> wishes, ArrayList<Wishlist> wishlists) throws SQLException {

        // Communicates with MySQL
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniprojekt",
                "root", "Hemmeligt");
        // Makes an statement to each tables and gather the results into resultsets

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM wishlist\n" +
                "INNER JOIN wish\n" +
                "ON wishlist.id_wishlist = wish.id_wishlist;");
        System.out.println("\nStatement prepared...");
        ResultSet res = statement.executeQuery();
        System.out.println("Result gathered...\n");

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
            System.out.println("\nName and auhtors = " + name + " - " + author);
            prev = res.getInt(1);
            System.out.println("Previous = " + prev + "\n");
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
