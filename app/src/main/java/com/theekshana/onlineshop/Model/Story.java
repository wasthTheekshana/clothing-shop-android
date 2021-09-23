package com.theekshana.onlineshop.Model;

public class Story {

    String image;
    String storyName;
    String storytId;

    public Story(String image, String storyName, String storytId) {
        this.image = image;
        this.storyName = storyName;
        this.storytId = storytId;
    }

    public Story() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStorytId() {
        return storytId;
    }

    public void setStorytId(String storytId) {
        this.storytId = storytId;
    }
}
