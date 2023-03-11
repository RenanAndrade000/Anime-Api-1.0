package com.example.animesapi.model;

public class Anime {
    private String title;
    private String image_url;
    private String mal_id;


    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getMal_id() {
        return mal_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setMal_id(String mal_id) {
        this.mal_id = mal_id;
    }
}
