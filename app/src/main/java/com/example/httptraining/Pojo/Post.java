package com.example.httptraining.Pojo;

public class Post {
    private String userID;
    private String postID;
    private String title;
    private String post;

    public Post(String userID, String postID, String title, String post) {
        this.userID = userID;
        this.postID = postID;
        this.title = title;
        this.post = post;
    }

    public String getUserID() {
        return userID;
    }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getPost() {
        return post;
    }
}
