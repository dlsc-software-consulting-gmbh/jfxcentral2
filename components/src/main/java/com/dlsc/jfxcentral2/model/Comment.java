package com.dlsc.jfxcentral2.model;

import java.time.ZonedDateTime;

public class Comment {
    private String id;
    private String content;
    /**
     * The user who wrote this comment.
     */
    private User user;
    private ZonedDateTime date;
    private int likes;
    /**
     * Indicates whether this comment has been deleted.
     * If the comment has been deleted, The user will see the placeholder "comment deleted".
     */
    private boolean deleted;
    /**
     * Indicates whether the current user has liked this comment.
     */
    private boolean liked;

    public Comment() {
    }

    public Comment(String id, String content, User user, ZonedDateTime date, int likes, boolean deleted, boolean liked) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.date = date;
        this.likes = likes;
        this.deleted = deleted;
        this.liked = liked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
