package kea_pal_gruppe_3.mini_projekt.controllers;

import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class ViewController {


private WishlistRepo test = new WishlistRepo();

    @GetMapping("/view.html")
    public String renderView(Model model) {

        ArrayList<Wishlist> allWishLists = test.getAllWishlists();

        model.addAttribute("list",allWishLists);

        return "view.html";
    }


}
