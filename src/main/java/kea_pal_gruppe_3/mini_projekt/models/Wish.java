package kea_pal_gruppe_3.mini_projekt.models;

public class Wish {

    private int Id_wish;
    private int Id_wishlist;
    private String wish;
    private String url;

    public Wish (String wish, String url){

        this.wish = wish;
        this.url = url;
    }


    public String getWish() {
        return wish;
    }

    public String getUrl() {
        return url;
    }
}
