package kea_pal_gruppe_3.mini_projekt.controllers;

import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class ViewController {

    private WishlistRepo wishlistRepo = new WishlistRepo();

    @GetMapping("/view")
    public String renderView(Model model) {

        ArrayList<Wishlist> allWishLists = wishlistRepo.getAllWishlists();

        model.addAttribute("list", allWishLists);

        return "view";
    }


    @GetMapping("/wishlist/{getId_wishlist}")
    public String getWishlist(@PathVariable("getId_wishlist") int id, Model model) {

       // return wishlistService.getWishlist(id).toString();

        ArrayList<Wishlist> allWishLists = wishlistRepo.getAllWishlists();

        Wishlist tmp = null;

        for (int i = 0; i < allWishLists.size(); i++) {
            if (allWishLists.get(i).getId_wishlist() == id)
                tmp = allWishLists.get(i);
        }

        model.addAttribute("list", tmp);
        return "wish-overview";

    }
}
