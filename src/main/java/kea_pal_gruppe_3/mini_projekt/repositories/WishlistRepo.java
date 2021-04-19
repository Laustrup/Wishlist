package kea_pal_gruppe_3.mini_projekt.repositories;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@Author Laust
public class WishlistRepo {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private int wishlistId = 0;
    private int previousWishlistId = 1;

    private String name = new String();
    private String author = new String();

    private Map<Integer, Wishlist> map = new HashMap<>();

    // An arraylist to gather every wishes pr. wishlist into wishlist
    private ArrayList<Wish> wishes = new ArrayList<>();
    // Wishlist to be returned
    private ArrayList<Wishlist> allWishlists;

    public ArrayList<Wishlist> getAllWishlists() {

        System.out.println("\ngetAllWishlists() beginning ***************************************************************");

        allWishlists = new ArrayList<>();

        try {
            executeQuerySelectAll();
            allWishlists = gatherFromDatabase();
        }
        catch(SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("\nSomething went wrong... " + e.getMessage() + "\n");
            e.printStackTrace();
        }

        System.out.println("getAllWishlists() ending ***************************************************************\n");
        return allWishlists;
    }

    public void executeQuerySelectAll() throws SQLException {
        // Communicates with MySQL
        connection = DriverManager.getConnection("jdbc:mysql://13.53.214.68:3306/miniprojekt",
                "remote", "1234");
        // Makes an statement to each tables and gather the results into resultsets

        statement = connection.prepareStatement("SELECT * FROM wishlist " +
                "INNER JOIN wish " +
                "ON wishlist.id_wishlist = wish.id_wishlist;");
        System.out.println("\nStatement prepared...");
        resultSet = statement.executeQuery();
        System.out.println("Result gathered...\n");
    }

    private ArrayList<Wishlist> gatherFromDatabase() throws SQLException {

        Wish currentWish;

        while(resultSet.next()) {
            if (resultSet.getInt(1) > previousWishlistId) {
                System.out.println(resultSet.getInt(1) + " is current wishlistId and " +
                        previousWishlistId + " is previous, isLast is " + resultSet.isLast());
                addToWishlists();
            }

            if (!resultSet.isLast()) {
                currentWish = new Wish(resultSet.getInt(4), resultSet.getString(6),
                                        resultSet.getString(7), false);
                wishes.add(currentWish);
                System.out.println("Wish added to wishes... " + resultSet.getString(6) + " - " + resultSet.getString(7));
            }

            previousWishlistId = updateCurrentData();

            if (resultSet.isLast()) {
                System.out.println(resultSet.getInt(1) + " is current wishlistId and " +
                        previousWishlistId + " is previous, isLast is " + resultSet.isLast());
                currentWish = new Wish(resultSet.getInt(4), resultSet.getString(6),
                                        resultSet.getString(7), false);
                wishes.add(currentWish);
                System.out.println("Wish added to wishes... " + resultSet.getString(6) + " - " + resultSet.getString(7));
                addToWishlists();
            }

        }

        return allWishlists;
    }

    private int updateCurrentData() throws SQLException {
        name = resultSet.getString(2);
        author = resultSet.getString(3);
        System.out.println("\nName and authors = " + name + " - " + author);
        previousWishlistId = resultSet.getInt(1);
        System.out.println("Previous = " + previousWishlistId + "\n");
        wishlistId = resultSet.getInt(1);

        return previousWishlistId;
    }

    private void addToWishlists() {

        Wishlist currentWishlist = new Wishlist(wishlistId,name, author, wishes);
        System.out.println("Wishlist id is " + wishlistId);
        System.out.println("\ncurrentWishlist created!");

        map.put(currentWishlist.getId(),currentWishlist);
        System.out.println(currentWishlist.getId() + " is the id of the wishlist added to map.");

        allWishlists.add(currentWishlist);
        System.out.println("\nWishlist updated with wishes!");
        wishes = new ArrayList<>();
        System.out.println("Wishes zeroed!\n");


    }

    //TODO Classdiagram figure out the parameter inputs
    public Wishlist putInWishlist(String name, String author, ArrayList<Wish> wishes) {

        this.name = name;
        this.author = author;
        this.wishes = wishes;

        // empty temp Wishlist to return
        Wishlist newWishlist = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://13.53.214.68:3306/miniprojekt",
                    "remote", "1234");
            setDatabase();
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

    private Wishlist setDatabase() throws SQLException {

        executeUpdateWishlist();

        executeUpdateWishes();

        return new Wishlist(wishlistId,name,author,wishes);
    }

    private void executeUpdateWishlist() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://13.53.214.68:3306/miniprojekt",
                "remote", "1234");
        statement = connection.prepareStatement("INSERT INTO wishlist(name, author)" +
                " VALUES (\"" + name + "`\", \"" + author + "\");");
        statement.executeUpdate();
        System.out.println(name + " added to database!");
    }

    private void executeUpdateWishes() throws SQLException {

        wishlistId = determineId_Wishlist();
        System.out.println("wishlistId is determined to be " + wishlistId + " and wishes.size() equals " + wishes.size());

        if (wishlistId != -1) {
            for (int i = 0; i < wishes.size(); i++) {

                System.out.println("\nVariables are " + wishes.get(i).getIdWish() +
                        " - " + wishes.get(i).getWish() + " - " + wishes.get(i).getUrl());
                statement = connection.prepareStatement("INSERT INTO wish(id_wishlist,wish, url)" +
                        " VALUES (" + wishlistId + ",\"" + wishes.get(i).getWish() +
                        "\", \"" + wishes.get(i).getUrl() + "\");");
                System.out.println("Statement prepared!");
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
           while(resultSet.next()) {
                if (resultSet.isLast()) {
                    return resultSet.getInt(1)+1;
                }
           }
           return -1;
    }

    public Map<Integer, Wishlist> getMap() {
        return map;
    }

    public ResultSet getResultSet () {
        return resultSet;
    }

}
