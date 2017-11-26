package ru.kpfu.entites;

/**
 * Created by Evgenia on 24.11.2017.
 */
public class User {
    private String login;
    private String password;
    private String name;
    private String gender;
    private String country;
    private String aboutYourself;
    private String newsletter;
    private String favoriteSinger;

    public User(String login, String password, String name, String gender, String country, String aboutYourself, String newsletter, String favoriteSinger) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.aboutYourself = aboutYourself;
        this.newsletter = newsletter;
        this.favoriteSinger = favoriteSinger;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getAboutYourself() {
        return aboutYourself;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public String getFavoriteSinger() {
        return favoriteSinger;
    }
}
