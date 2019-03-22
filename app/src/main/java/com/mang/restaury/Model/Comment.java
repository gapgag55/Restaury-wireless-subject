package com.mang.restaury.Model;

public class Comment {

    private String fullname;
    private String date;
    private String comment;
    private int stars;

    public Comment() {}

    public Comment(String fullname, String date, String comment, int stars) {
        this.fullname = fullname;
        this.date = date;
        this.comment = comment;
        this.stars = stars;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
