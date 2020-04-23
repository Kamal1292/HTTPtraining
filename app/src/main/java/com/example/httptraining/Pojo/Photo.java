package com.example.httptraining.Pojo;

public class Photo {

    private String albumId;
    private String photoId;
    private String photoTitle;
    private String url;

    public Photo(String albumId, String photoId, String photoTitle, String url) {
        this.albumId = albumId;
        this.photoId = photoId;
        this.photoTitle = photoTitle;
        this.url = url;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public String getUrl() {
        return url;
    }
}
