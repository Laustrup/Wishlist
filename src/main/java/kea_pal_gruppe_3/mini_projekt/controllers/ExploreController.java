package kea_pal_gruppe_3.mini_projekt.controllers;

import kea_pal_gruppe_3.mini_projekt.models.Wish;
import kea_pal_gruppe_3.mini_projekt.models.Wishlist;
import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;
import kea_pal_gruppe_3.mini_projekt.services.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class ExploreController {

    private WishlistRepo wishlistRepo = new WishlistRepo();
    private Wishlist currentWishlist;
    private WishService wishService = new WishService();

    @GetMapping("/explore")
    public String renderExplore(Model model) {

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
    public String renderIndividualList(@PathVariable("list.getId") int id, Model model) {

        Map<Integer, Wishlist> allWishLists = wishlistRepo.getMap();
        ArrayList<Wish> tmp = allWishLists.get(id).getListOfWishes();
        currentWishlist = allWishLists.get(id);

        model.addAttribute("list", tmp);
        return "wishlist";
    }

    @PostMapping("/change_reserved_status")
    public String changeReservedStatus(@RequestParam(name = "wish") String idWish) {

        wishService.changeReservedStatus(currentWishlist.getId(),Integer.parseInt(idWish),true);

        return "wishlist";
    }

    @PostMapping("/change_unreserved_status")
    public String changeUnreservedStatus(@RequestParam(name = "wish") String idWish) {
        wishService.changeReservedStatus(currentWishlist.getId(),Integer.parseInt(idWish),false);

        return "wishlist";
    }

    @PostMapping("/reserveWish")
    public String reserveWish(@RequestParam(name = "wishID") int wishID, RedirectAttributes redirectAttributes){

        return "redirect:/explore";
    }

}
