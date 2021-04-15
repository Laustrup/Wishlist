package kea_pal_gruppe_3.mini_projekt.models;

import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;

public class Wish {

    private int idWish;
    private String wish;
    private String url;

    private WishlistRepo repo;

    public Wish (int idWish, String wish, String url){
        this.idWish = idWish;
        this.wish = wish;
        this.url = url;

    }

    public Wish (String wish, String url){
        repo = new WishlistRepo();

        this.idWish = repo.determineIdWish();
        this.wish = wish;
        this.url = url;

    }

    public int getIdWish() {
        return idWish;
    }

    public String getWish() {
        return wish;
    }

    public String getUrl() {
        return url;
    }
}
