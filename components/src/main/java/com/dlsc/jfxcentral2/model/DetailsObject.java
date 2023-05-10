package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

import java.util.List;

public class DetailsObject {

    public record Description(String text, boolean isMarkdown) {
    }

    private String title;
    private Image titleImage;
    private Description description;
    private Image mainPreview;
    private String mainPreviewDescription;
    private List<Image> previews;
    private boolean isSaved;
    private boolean isLiked;

    public DetailsObject() {
    }

    public DetailsObject(String title, Image titleImage, Description description, Image mainPreview, String mainPreviewDescription, List<Image> previews, boolean isSaved, boolean isLiked) {
        this.title = title;
        this.titleImage = titleImage;
        this.description = description;
        this.mainPreview = mainPreview;
        this.mainPreviewDescription = mainPreviewDescription;
        this.previews = previews;
        this.isSaved = isSaved;
        this.isLiked = isLiked;
    }

    public String getMainPreviewDescription() {
        return mainPreviewDescription;
    }

    public void setMainPreviewDescription(String mainPreviewDescription) {
        this.mainPreviewDescription = mainPreviewDescription;
    }

    public Image getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(Image titleImage) {
        this.titleImage = titleImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Image getMainPreview() {
        return mainPreview;
    }

    public void setMainPreview(Image mainPreview) {
        this.mainPreview = mainPreview;
    }

    public List<Image> getPreviews() {
        return previews;
    }

    public void setPreviews(List<Image> previews) {
        this.previews = previews;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
