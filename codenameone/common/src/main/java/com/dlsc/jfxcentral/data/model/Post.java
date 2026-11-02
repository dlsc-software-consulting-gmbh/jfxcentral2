package com.dlsc.jfxcentral.data.model;


import com.dlsc.jfxcentral.data.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/// One entry of a blog's feed. The desktop build read these with Rome; the
/// Codename One build parses the feed with `com.codename1.io.services.RSSService`,
/// so the entry keeps only the values the views show.
public class Post extends ModelObject {

    private final Blog blog;
    private final String title;
    private final String author;
    private final String link;
    private final Date publishedDate;

    public Post(Blog blog, String title, String author, String link, Date publishedDate) {
        this.blog = blog;
        this.title = title;
        this.author = author;
        this.link = link;
        this.publishedDate = publishedDate;
    }

    @Override
    public boolean matches(String searchPattern) {
        return super.matches(searchPattern)
                || StringUtils.containsAnyIgnoreCase(title, searchPattern)
                || StringUtils.containsAnyIgnoreCase(author, searchPattern);
    }

    public Blog getBlog() {
        return blog;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public LocalDate getDate() {
        if (publishedDate != null) {
            return ZonedDateTime.ofInstant(publishedDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
        }
        return LocalDate.now();
    }
}
