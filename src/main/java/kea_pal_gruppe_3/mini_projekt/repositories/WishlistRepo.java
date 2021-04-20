package kea_pal_gruppe_3.mini_projekt.repositories;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistRepo {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private int wishlistId = 0;
    private int previousWishlistId = 1;

    private String name = new String();
    private String author = new String();

    private Map<Integer, Wishlist> map = new HashMap<>();

    private ArrayList<Wish> wishes = new ArrayList<>();
    private ArrayList<Wishlist> allWishlists;

    private boolean isReserved = false;

    public ArrayList<Wishlist> getAllWishlists() {

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

        return allWishlists;
    }

    public void executeQuerySelectAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://13.53.214.68:3306/miniprojekt",
                "remote", "1234");

        statement = connection.prepareStatement("SELECT * FROM wishlist " +
                "INNER JOIN wish " +
                "ON wishlist.id_wishlist = wish.id_wishlist;");
        resultSet = statement.executeQuery();
    }

    private ArrayList<Wishlist> gatherFromDatabase() throws SQLException {

        Wish currentWish;

        while(resultSet.next()) {
            if (resultSet.getInt(1) > previousWishlistId) {
                System.out.println(resultSet.getInt(1) + " is current wishlistId and " +
                        previousWishlistId + " is previous, isLast is " + resultSet.isLast());
                addToWishlists();
            }

            isReserved = resultSet.getBoolean(8);

            if (!resultSet.isLast()) {
                currentWish = new Wish(resultSet.getInt(4), resultSet.getString(6),
                                        resultSet.getString(7), isReserved);
                wishes.add(currentWish);
            }

            updateCurrentData();

            if (resultSet.isLast()) {
                currentWish = new Wish(resultSet.getInt(4), resultSet.getString(6),
                                        resultSet.getString(7), isReserved);
                wishes.add(currentWish);
                addToWishlists();
            }

        }

        return allWishlists;
    }

    private void updateCurrentData() throws SQLException {

        name = resultSet.getString(2);
        author = resultSet.getString(3);
        previousWishlistId = resultSet.getInt(1);
        wishlistId = resultSet.getInt(1);

    }

    private void addToWishlists() {

        Wishlist currentWishlist = new Wishlist(wishlistId,name, author, wishes);

        map.put(currentWishlist.getId(),currentWishlist);

        allWishlists.add(currentWishlist);
        wishes = new ArrayList<>();

    }

    public Wishlist putInWishlist(String name, String author, ArrayList<Wish> wishes) {

        this.name = name;
        this.author = author;
        this.wishes = wishes;

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
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
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

        if (wishlistId != -1) {
            for (int i = 0; i < wishes.size(); i++) {
                statement = connection.prepareStatement("INSERT INTO wish(id_wishlist,wish, url,isReserved)" +
                        " VALUES (" + wishlistId + ",\"" + wishes.get(i).getWish() +
                        "\", \"" + wishes.get(i).getUrl() + "\", FALSE" + ");");
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
