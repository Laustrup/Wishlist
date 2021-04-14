package kea_pal_gruppe_3.mini_projekt.controllers;
//Lavet af Patrick

import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WishController {

    private WishlistRepo wishlistRepo = new WishlistRepo();

    @GetMapping("/create_wish.html")
    public String wishForm(){

        return "create_wish.html";
    }

    @PostMapping("/get_wish")
    public String getWishFromForm(@RequestParam (name = "wish_name") String wishName,
                                  @RequestParam (name = "wish_url") String wishURL,
                                  RedirectAttributes redirect){

        //Wishlist wishlist = wishlistRepo.putInWishlist(wishName,wishURL,);

        redirect.addAttribute("wishName",wishName);
        redirect.addAttribute("wishURL",wishURL);

        return "redirect:/wish_succes.html";
    }

    @GetMapping("/wish_succes.html")
    public String wishSucces(@RequestParam String wishName, @RequestParam String wishURL, Model model){

        model.addAttribute("wish_name", wishName);
        model.addAttribute("wish_url", wishURL);

        return "wish_succes.html";
    }
}
