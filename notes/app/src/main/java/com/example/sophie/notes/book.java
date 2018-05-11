package com.example.sophie.notes;

public class book {
    private String title;
    private String category;
    private String description;
    private int Thumbnail;

    public book(){

    }

    public book(String title, String category, String description, int thumbnail) {
        this.title = title;
        this.category = category;
        this.description = description;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
