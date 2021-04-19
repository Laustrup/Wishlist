package kea_pal_gruppe_3.mini_projekt.models;

import kea_pal_gruppe_3.mini_projekt.repositories.WishlistRepo;

public class Wish {

    private int idWish;
    private String wish;
    private String url;
    private boolean isReserved;

    private int addExtraToId = 0;

    private WishlistRepo repo;

    public Wish (int idWish, String wish, String url, boolean isReserved){
        this.idWish = idWish;
        this.wish = wish;
        this.url = url;
        this.isReserved = isReserved;
    }

    public Wish (String wish, String url,boolean isReserved, boolean hasMoreWishes) throws ExceptionInInitializerError {

        repo = new WishlistRepo();

        if (hasMoreWishes) {
            addExtraToId++;
        }

        this.idWish = repo.calculateNextIdWish(addExtraToId);
        if (idWish == -1) {
            throw new ExceptionInInitializerError();
        }
        this.wish = wish;
        this.url = url;
        this.isReserved = isReserved;
        this.isReserved = isReserved;

    }

    public void setAddExtraToIdToZero() {
        addExtraToId = 0;
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
