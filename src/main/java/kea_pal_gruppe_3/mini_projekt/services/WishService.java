package kea_pal_gruppe_3.mini_projekt.services;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishService {

    private WishlistRepo repo;
    private ResultSet repoResultSet;

    private Wishlist wishList;
    private ArrayList<Wish> wishes;

    private Map<Integer, Wishlist> map = new HashMap<>();

    private int nextReservedStatus;

    public int calculateNextIdWish(int extraToAdd) {

        repo = new WishlistRepo();

        try {
            repo.executeQuerySelectAll();
            repoResultSet = repo.getResultSet();

            while (repoResultSet.next()) {
                if (repoResultSet.isLast()) {
                    System.out.println(repoResultSet.getInt(4)+1 + extraToAdd + " is the next idWish...\n");
                    return repoResultSet.getInt(4)+1+extraToAdd;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void changeReservedStatus(int idWishlist, int idWish, boolean alreadyReserved) {
        collectFromDatabase(idWishlist,alreadyReserved);

        for (int i = 0; i < wishes.size();i++) {
            if (wishes.get(i).getIdWish() == idWish) {
                try {
                    updateColumn(idWish);
                }
                catch(SQLException e){
                    System.out.println("\nSomething went wrong...\n" + e.getMessage());
                }
            }
        }
    }

    private void collectFromDatabase(int idWishlist, boolean alreadyReserved) {
        repo.getAllWishlists();
        map = repo.getMap();

        wishList = map.get(idWishlist);
        wishes = wishList.getListOfWishes();

        if (alreadyReserved) {
            nextReservedStatus = 0;
        }
        else {
            nextReservedStatus = 1;
        }
    }

    private void updateColumn(int idWish) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://13.53.214.68:3306/miniprojekt",
                "remote", "1234");
        PreparedStatement statement = connection.prepareStatement("UPDATE wish SET isReserved = " + nextReservedStatus +
                " WHERE id_wish = " + idWish + ";");
        statement.executeUpdate();
    }
}
