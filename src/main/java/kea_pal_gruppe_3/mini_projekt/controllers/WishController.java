package kea_pal_gruppe_3.mini_projekt.controllers;
//Lavet af Patrick

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class WishController {

    private WishlistRepo repo = new WishlistRepo();
    private ArrayList<Wish> wishes = new ArrayList<>();
    private Wish wish = new Wish(null,null,false,false);

    private boolean hasMoreWishes = false;
    private boolean areThereAnyWishes = false;

    @GetMapping("/create_wish")
    public String wishForm(){

        areThereAnyWishes = false;
        return "create_wish";
    }

    @PostMapping("/get_wish")
    public String getWishFromForm(@RequestParam (name = "wishlist_name") String wishlistName,
                                  @RequestParam (name = "author_name") String authorName,
                                  Model model){
        if (areThereAnyWishes) {


            model.addAttribute("wishlist_name", wishlistName);
            model.addAttribute("author_name", authorName);

            repo.putInWishlist(wishlistName, authorName, wishes);


            wish.setAddExtraToIdToZero();
            hasMoreWishes = false;
            wishes = new ArrayList<>();
            areThereAnyWishes = false;

            return "get_wish";
        }
        else {

            model.addAttribute("errormessage", "You need to make a wish, in order to make a wishlist...");
            return "create_wish";
        }

    }

    @PostMapping("/add_wish")
    public String addWish(@RequestParam (name = "wish_name") String wishName,
                          @RequestParam (name = "wish_url") String wishURL,
                          Model model){

        if (wishURL.equals("")) {
            wishURL = null;
        }

        try {
            wishes.add(new Wish(wishName, wishURL,false, hasMoreWishes));
            hasMoreWishes = true;
            System.out.println("Wish added to wishes in /add_wish!");
            model.addAttribute("wishes",wishes);
            areThereAnyWishes = true;
        }
        catch (ExceptionInInitializerError e) {
            System.out.println("Couldn't automatically make idWish...");
            model.addAttribute("error", ",Sorry, couldn't add wish...");
        }

        return "create_wish";
    }

}

