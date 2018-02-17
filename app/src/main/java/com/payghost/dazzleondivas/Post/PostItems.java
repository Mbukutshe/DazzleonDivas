package com.payghost.dazzleondivas.Post;

/**
 * Created by Payghost on 11/7/2017.
 */

public class PostItems {
    private String image;
    private String title;
    private String description;

    public PostItems(){}

    public PostItems(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

