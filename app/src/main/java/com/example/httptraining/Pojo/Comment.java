package com.example.httptraining.Pojo;

public class Comment {

    private String postID;
    private String commentID;
    private String commentName;
    private String commentatorEmail;
    private String commentBody;

    public Comment(String postID, String commentID, String commentName, String commentatorEmail, String commentBody) {
        this.postID = postID;
        this.commentID = commentID;
        this.commentName = commentName;
        this.commentatorEmail = commentatorEmail;
        this.commentBody = commentBody;
    }

    public String getPostID() {
        return postID;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getCommentName() {
        return commentName;
    }

    public String getCommentatorEmail() {
        return commentatorEmail;
    }

    public String getCommentBody() {
        return commentBody;
    }
}
