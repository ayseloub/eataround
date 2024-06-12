package com.example.foodaround.ui;

public class RestoItem {
    private String name;
    private String description;
    private int imageResource;
    private float rating;
    public RestoItem(String name, String description, int
            imageResource, float rating) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.rating = rating;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;

    }
    public int getImageResource() {
        return imageResource;
    }
    public float getRating() {
        return rating;
    }
}