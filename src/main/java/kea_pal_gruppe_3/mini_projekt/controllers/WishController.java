package kea_pal_gruppe_3.mini_projekt.controllers;
//Lavet af Patrick

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class WishController {

    private WishlistRepo wishlistRepo = new WishlistRepo();
    private ArrayList<Wish> wishes = new ArrayList<>();


    @GetMapping("/create_wish")
    public String wishForm(){

        return "create_wish";
    }


    @PostMapping("/get_wish")
    public String getWishFromForm(@RequestParam (name = "wishlist_name") String wishlistName,
                                  @RequestParam (name = "author_name") String authorName,
                                  RedirectAttributes redirect, Model model){
/*
        redirect.addAttribute("wishlist_name", wishlistName);
        redirect.addAttribute("wish_url",wishURL);
        redirect.addAttribute("wish_name",wishName);
        redirect.addAttribute("author_name", authorName);
*/

        model.addAttribute("wishlist_name", wishlistName);
        model.addAttribute("author_name", authorName);

        wishlistRepo.putInWishlist(wishlistName, authorName, wishes);

        return "get_wish";
    }

    @PostMapping("/add_wish")
    public String addWish(@RequestParam (name = "wish_name") String wishName,
                          @RequestParam (name = "wish_url") String wishURL){

        wishes.add(new Wish(wishName, wishURL));
        System.out.println("wish added to wishes in /add_wish");

        return "create_wish.html";
    }

    @GetMapping("/wish_succes")
    public String wishSucces(@RequestParam String wishlistName,
                             @RequestParam String wishURL,
                             @RequestParam String wishName,
                             @RequestParam String authorName,
                             Model model){
        System.out.println("wish succes bol mig");

        model.addAttribute("wishlist_name", wishlistName);
        model.addAttribute("author_name", authorName);
        model.addAttribute("wish_url", wishURL);
        model.addAttribute("wish_name", wishName);



        System.out.println("vi klarede den! måske?");
        return "wish_succes.html";
    }

    @PostMapping("/creating_wish")
    public String creatingWish() {
        return "wish_created.html";
    }
}
