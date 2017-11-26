package ru.kpfu.entites;

/**
 * Created by Evgenia on 24.11.2017.
 */
public class Good {
    private int id;
    private int catalogId;
    private String name;
    private float price;
    private int quantity;
    private String userLogin;
    private String img;


    public Good(int catalogId, int quantity, String user_login) {
//    public Good(int catalogId, int quantity, String user_login) {
        this.catalogId = catalogId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.userLogin = user_login;
    }

    public Good(int catalogId, String img, int id, String name, float price, int quantity) {
        this.catalogId = catalogId;
        this.img=img;
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }
}