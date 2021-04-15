package kea_pal_gruppe_3.mini_projekt.repositories;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;

//@Author Laust
public class WishlistRepo {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet res;

    private int wishlistId;

    public ArrayList<Wishlist> getAllWishlists() {

        // An arraylist to gather every wishes pr. wishlist into wishlist
        ArrayList<Wish> wishes = new ArrayList<>();
        // Wishlist to be returned
        ArrayList<Wishlist> wishlists = new ArrayList<>();

        try {
            executeQuerySelectAll();
            wishlists = talkToDatabase(wishes, wishlists, res);
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

    private void executeQuerySelectAll() throws SQLException {
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

    public ArrayList<Wishlist> talkToDatabase(ArrayList<Wish> wishes, ArrayList<Wishlist> wishlists,
                                              ResultSet res) throws SQLException {

        int prev = 1;
        Wishlist wishToAdd = new Wishlist(0, null,null,null);
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
                    wishes.add(new Wish(res.getInt(4), res.getString(6),res.getString(7)));
                    System.out.println("Wish added to wishes... " + res.getString(6) + " - " + res.getString(7));
                }
                wishToAdd = new Wishlist(res.getInt(1),name, author, wishes);
                System.out.println("\nwishToAdd created!");

                wishlists.add(wishToAdd);
                System.out.println("\nWishlist updated with wishes!");
                wishes = new ArrayList<>();
                System.out.println("Wishes zeroed!\n");
            }

            if (!res.isLast()) {
                wishes.add(new Wish(res.getInt(4), res.getString(6),res.getString(7)));
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

    public Wishlist putInWishlist(String name, String author, ArrayList<Wish> wishlist) {

        // empty temp Wishlist to return
        Wishlist newWishlist = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://13.53.216.245:3306/miniprojekt",
                    "remote", "1234");
            setDatabase(name,author,wishlist,connection,statement);
        }
        catch (SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("\nSomething went wrong at setDataBase..." + e.getMessage());
            e.printStackTrace();
        }

        // returns an Employee based on the infos, in order to show the user via. model, the created employee.
        return newWishlist;
    }

    public Wishlist setDatabase(String name, String author,
                                ArrayList<Wish> wishlist, Connection connection,
                                PreparedStatement statement) throws SQLException {

        executeUpdateWishlist(name,author,connection,statement);

        executeUpdateWishes(wishlist,connection,statement);

        return new Wishlist(wishlistId,name,author,wishlist);
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

        wishlistId = determineId_Wishlist();
        System.out.println("wishlistId is determined to be " + wishlistId + " and wishes.size() equals " + wishes.size());

        if (wishlistId != -1) {
            for (int i = 0; i < wishes.size(); i++) {
                System.out.println("\nVariables are " + wishes.get(i).getWish() + wishes.get(i).getUrl());
                statement = connection.prepareStatement("INSERT INTO wish(id_wishlist,wish, url)" +
                        " VALUES (" + wishes.get(i).getIdWish() + ",\"" + wishes.get(i).getWish() + "\", \"" + wishes.get(i).getUrl() + "\");");
                statement.executeUpdate();
                System.out.println(wishes.get(i).getWish() + " added to database!");
            }
        }
        else {
            System.out.println("Couldn't determine wishlist_id...");
        }
    }

    private int determineId_Wishlist() throws SQLException {

        executeQuerySelectAll();
           while(res.next()) {
                if (res.isLast()) {
                    return res.getInt(1)+1;
                }
            }
            return -1;
    }

    public int calculateNextIdWish(int extraToAdd) {

        try {
            executeQuerySelectAll();
            while (res.next()) {
                if (res.isLast()) {
                    System.out.println(res.getInt(4)+1 + extraToAdd + " is the next idWish...");
                    return res.getInt(4)+1+extraToAdd;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

}
