package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

import java.util.ArrayList;
import java.util.List;

@Mapped
public class LibraryInfo {

    private List<Image> images = new ArrayList<>();

    private List<Video> videos = new ArrayList<>();

    public LibraryInfo() {
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
