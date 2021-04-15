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

        model.addAttribute("list",allWishLists);

        return "view";
    }

    /*
    @GetMapping("/wishlist/{id}")
    @ResponseBody
    public String getWishlist(@PathVariable("id") int id) {
        return wishlistService.getWishlist(id).toString();
    }*/

    @GetMapping("/wish-overview")
    public String showOverview(Model model){
       // ArrayList<Wishlist> wishlists = test.getAllWishlists();

       // model.addAttribute("list", wishlists.get(1));

        return "wish-overview";
    }


}
