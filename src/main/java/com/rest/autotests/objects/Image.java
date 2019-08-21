package com.rest.autotests.objects;

public class Image {
    private String id;
    private String description;
    private String userId;
    private int likesCount;
    private String imageUrl;
    private String path;

    public Image(String description, String path) {
        this.description = description;
        this.path = path;
    }

    public Image(String id, String description, String userId, int likesCount, String imageUrl) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.likesCount = likesCount;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
