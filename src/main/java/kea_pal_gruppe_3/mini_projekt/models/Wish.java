package kea_pal_gruppe_3.mini_projekt.models;

import kea_pal_gruppe_3.mini_projekt.services.WishService;

public class Wish {

    private int idWish;
    private String wish;
    private String url;
    private boolean isReserved;

    private int addExtraToId = 0;

    private WishService service;

    public Wish (int idWish, String wish, String url, boolean isReserved){
        this.idWish = idWish;
        this.wish = wish;
        this.url = url;
        this.isReserved = isReserved;
    }

    public Wish (String wish, String url,boolean isReserved, boolean hasMoreWishes) {

        service = new WishService();

        if (hasMoreWishes) {
            addExtraToId++;
        }

        this.idWish = service.calculateNextIdWish(addExtraToId);

        this.wish = wish;
        this.url = url;
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

    public String printIsReservedToButton() {
        if (isReserved) {
            return "UNRESERVE";
        }
        else {
            return "RESERVE";
        }
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
