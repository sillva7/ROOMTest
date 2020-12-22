package com.example.roomtest.DB;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String lastName;
    private String email;
    private String number;


    public Contact(int id, String name, String lastName, String email, String number) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
    }

    @Ignore
    public Contact(String name, String lastName, String email, String number) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
