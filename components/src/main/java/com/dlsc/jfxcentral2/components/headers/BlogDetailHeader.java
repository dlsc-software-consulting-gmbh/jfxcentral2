package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;

public class BlogDetailHeader extends SimpleDetailHeader<Blog>  {

    public BlogDetailHeader(Blog blog) {
        super(blog);

        getStyleClass().add("blog-detail-header");

        imageProperty().bind(ImageManager.getInstance().blogIconImageProperty(blog));
        setWebsite(blog.getUrl());
        setShareUrl("blogs/" + blog.getId());
        setShareText("Found this blog on @JFXCentral: " + blog.getName());
        setShareTitle("JavaFX blog: " + blog.getName());
        setBackText("ALL BLOGS");
        setBackUrl("/blogs");
    }
}
