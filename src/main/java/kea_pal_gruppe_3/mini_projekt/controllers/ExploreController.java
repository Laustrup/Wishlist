package kea_pal_gruppe_3.mini_projekt.controllers;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ExploreController {

    private WishlistRepo wishlistRepo = new WishlistRepo();

    @GetMapping("/explore")
    public String renderView(Model model) {

        ArrayList<Wishlist> allWishLists = wishlistRepo.getAllWishlists();

        model.addAttribute("list", allWishLists);

        //Debugging
        System.out.println("Size of array is: " + allWishLists.size());
        for (int i = 0; i < allWishLists.size(); i++) {
            System.out.println("array index " + i + " is: wishList id: " + allWishLists.get(i).getId());
        }

        return "explore";
    }


    @GetMapping("/wishlist/{list.getId}")
    public String getWishlist(@PathVariable("list.getId") int id, Model model) {

        ArrayList<Wishlist> allWishLists = wishlistRepo.getAllWishlists();

        ArrayList<Wish> tmp = null;

        for (int i = 0; i < allWishLists.size(); i++) {
            if (allWishLists.get(i).getId() == id) {
                tmp = allWishLists.get(i).getListOfWishes();
                System.out.println("got id [" + id + "] Matching id with [" + allWishLists.get(i).getId() + "]");
                break;
            }
        }

        //Debugging
        System.out.println("you made it here!");
        model.addAttribute("list", tmp);
        return "wishlist";

    }
}
