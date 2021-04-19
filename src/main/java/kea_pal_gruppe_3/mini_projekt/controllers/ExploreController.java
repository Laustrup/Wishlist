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
    public String changeReservedStatus(@RequestParam(name = "wishID") int idWish) {
        boolean isReserved = false;

        for (int i = 0; i < currentWishlist.getListOfWishes().size(); i++) {
            if (currentWishlist.getListOfWishes().get(i).getIdWish() == idWish){
                if (currentWishlist.getListOfWishes().get(i).isReserved()) {
                    isReserved = true;
                    currentWishlist.getListOfWishes().get(i).setReserved(false);

                    System.out.println("Status of former isReserved is " + isReserved);
                    break;
                }
                else {
                    currentWishlist.getListOfWishes().get(i).setReserved(true);
                }
            }
        }

        wishService.changeReservedStatus(currentWishlist.getId(),idWish,isReserved);

        return "wishlist";
    }

    @PostMapping("/reserveWish")
    public String reserveWish(@RequestParam(name = "wishID") int wishID, RedirectAttributes redirectAttributes){

        return "redirect:/explore";
    }

}
