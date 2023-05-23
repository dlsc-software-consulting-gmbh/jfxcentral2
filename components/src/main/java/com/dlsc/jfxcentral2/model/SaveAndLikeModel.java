package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.ModelObject;

import java.time.LocalDate;

public class SaveAndLikeModel {

    private String title;
    private Class<? extends ModelObject> typeClazz;
    private boolean saved;
    private LocalDate savedDate;
    private boolean liked;
    private LocalDate likedDate;

    public SaveAndLikeModel() {
    }

    public SaveAndLikeModel(String title, Class<? extends ModelObject> typeClazz, boolean saved, LocalDate savedDate, boolean liked, LocalDate likedDate) {
        this.title = title;
        this.typeClazz = typeClazz;
        this.saved = saved;
        this.savedDate = savedDate;
        this.liked = liked;
        this.likedDate = likedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<? extends ModelObject> getTypeClazz() {
        return typeClazz;
    }

    public void setTypeClazz(Class<? extends ModelObject> typeClazz) {
        this.typeClazz = typeClazz;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public LocalDate getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(LocalDate savedDate) {
        this.savedDate = savedDate;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public LocalDate getLikedDate() {
        return likedDate;
    }

    public void setLikedDate(LocalDate likedDate) {
        this.likedDate = likedDate;
    }

    @Override
    public String toString() {
        return "SaveAndLikeModel{" +
                "title='" + title + '\'' +
                ", typeClazz=" + typeClazz +
                ", saved=" + saved +
                ", savedDate=" + savedDate +
                ", liked=" + liked +
                ", likedDate=" + likedDate +
                '}';
    }
}
