package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Blog;

public class BlogDetailHeader extends SimpleDetailHeader<Blog>  {

    public BlogDetailHeader(Blog blog) {
        super(blog);
        getStyleClass().add("blog-detail-header");
        setWebsite(blog.getUrl());
    }
}
