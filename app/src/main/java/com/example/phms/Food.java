package com.example.phms;

public class Food {

    private String name, calories;

    public Food() {
    }

    public Food(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcalories() {
        return calories;
    }

    public void setcalories(String calories) {
        this.calories = calories;
    }
}