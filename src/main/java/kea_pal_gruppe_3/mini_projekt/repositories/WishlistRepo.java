package kea_pal_gruppe_3.mini_projekt.repositories;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;

import java.sql.*;
import java.util.ArrayList;

// Patrick & Laust
public class WishlistRepo {

    private Wishlist wishlist;

    public ArrayList<Wishlist> getAllWishlists() {

        ArrayList<Wish> wishes = new ArrayList<>();
        ArrayList<Wishlist> wishlists = new ArrayList<>();

        try {
            // Communicates with MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://13.53.216.245:3306/miniprojekt", "remote", "1234");
            //PreparedStatement statement = connection.prepareStatement("SELECT * FROM wishlist INNER JOIN wish ON wishlist.wishlist_id = wish.id");
            PreparedStatement wishlistTable = connection.prepareStatement("SELECT * FROM wishlist;");
            ResultSet wishListRes = wishlistTable.executeQuery();

            PreparedStatement wishTable = connection.prepareStatement("SELECT * FROM wish;");
            ResultSet wishRes = wishTable.executeQuery();

            while(wishListRes.next()) {

                wishlists.add(wishListRes.getString(1),wishListRes.getString(2), null);
            }

            int prevId = 0;
            int currentWishlist = 0;
            // Creates an wishlist and puts it into the arraylist
            while(wishRes.next()){

                wishes.add(new Wish(wishRes.getString(2), wishRes.getString(3)));

                if (wishRes.getInt(1) != prevId) {
                    wishlists.get(currentWishlist).setWishlist(wishes);
                    wishlist = new Wishlist(wishListRes.getString(1), wishListRes.getString(2), wishes);
                    System.out.println("Wishlist added to wishlist arraylist!");
                    currentWishlist++;
                    wishes = new ArrayList<>();
                }
                prevId = wishRes.getInt(1);
            }
        }

        catch(SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }
        return wishlists;
    }

    public Wishlist putInWishlist(String name, String author, ArrayList<Wish> wishlist) {

        // empty temp Wishlist to return
        Wishlist newWishlist = null;

        try {
            // Communicates with MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://13.53.216.245:3306/miniprojekt", "remote", "1234");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO wishlist(EMPNO, ENAME, JOB, MGR," +
                    "HIREDATE, SAL, COMM, DEPTNO)" +
                    " VALUES (" + empNo + ", '" + eName + "', '" + job + "', " + mgr +
                    ", '" + hireDate + "', " + sal + ", " + comm + ", " + deptno +");");
            statement.executeUpdate();

            // Creates an employee from the temp employee to be returned
            newEmployee = new Employee(Integer.parseInt(empNo),Integer.parseInt(mgr),Integer.parseInt(sal),Integer.parseInt(comm),
                    Integer.parseInt(deptno),eName,job,hireDate);
            System.out.println("Employee added to database!");

        }
        catch (SQLException e){
            System.out.println("\nSomething went wrong...\n" + e.getMessage());
        }

        // returns an Employee based on the infos, in order to show the user via. model, the created employee.
        return newEmployee;
    }
}