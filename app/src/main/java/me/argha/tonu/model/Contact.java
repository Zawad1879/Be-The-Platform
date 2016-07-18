package me.argha.tonu.model;

/**
 * Created by sbsat on 7/1/2016.
 */
public class Contact {
    String name;
    String number;
    int id;

    public Contact(int id, String name, String number) {
        this.name = name;
        this.number = number;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
