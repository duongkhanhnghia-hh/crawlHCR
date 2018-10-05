package model;

public class User {

    private int id;
    private String user_name;
    private String country;

    public User(int id, String user_name, String country) {
        this.id = id;
        this.user_name = user_name;
        this.country = country;
    }

    public User(String user_name, String country) {
        this.user_name = user_name;
        this.country = country;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
