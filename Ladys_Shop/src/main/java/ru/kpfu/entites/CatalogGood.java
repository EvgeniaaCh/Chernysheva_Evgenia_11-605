package ru.kpfu.entites;

/**
 * Created by Evgenia on 24.11.2017.
 */
public class CatalogGood {
    private int catalogId;
    private String name;
    private int price;
    private String description;
    private String img;

    public CatalogGood(int catalogId, String name, int price, String description, String img) {
        this.catalogId = catalogId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img = img;
    }
    public CatalogGood(String name, int price, String description, String img) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.img = img;
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

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }
}
