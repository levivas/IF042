package com.if42.tester.entity;

public class UserRating {
    private User user;
    private Double markPercents;

    public UserRating(User user, Double markPercents) {
        this.user = user;
        this.markPercents = markPercents;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMarkPercents(Double markPercents) {
        this.markPercents = markPercents;
    }

    public User getUser() {
        return user;
    }

    public Double getMarkPercents() {
        return markPercents;
    }
}
