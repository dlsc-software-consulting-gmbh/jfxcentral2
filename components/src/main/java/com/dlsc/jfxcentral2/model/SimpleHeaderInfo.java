package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;

public class SimpleHeaderInfo {

    private String name;
    private String website;
    private boolean saved;
    private boolean liked;
    private String description;

    public SimpleHeaderInfo() {
    }

    public SimpleHeaderInfo(String name, String description, String website, boolean saved, boolean liked) {
        this.name = name;
        this.description = description;
        this.website = website;
        this.saved = saved;
        this.liked = liked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public static SimpleHeaderInfo of(ModelObject mo) {
        if (mo instanceof Person person) {
            return new SimpleHeaderInfo(person.getName(), person.getDescription(), person.getWebsite(), false, false);
        } else if (mo instanceof Library library) {
            return new SimpleHeaderInfo(library.getName(), library.getDescription(), library.getHomepage(), false, false);
        } else if (mo instanceof Tool tool) {
            return new SimpleHeaderInfo(tool.getName(), tool.getDescription(), tool.getHomepage(), false, false);
        } else if (mo instanceof Book book) {
            return new SimpleHeaderInfo(book.getName(), book.getDescription(), book.getUrl(), false, false);
        } else if (mo instanceof Tip tip) {
            return new SimpleHeaderInfo(tip.getName(), tip.getDescription(), null, false, false);
        } else if (mo instanceof Blog blog) {
            return new SimpleHeaderInfo(blog.getName(), blog.getDescription(), blog.getUrl(), false, false);
        } else if (mo instanceof Tutorial tutorial) {
            return new SimpleHeaderInfo(tutorial.getName(), tutorial.getDescription(), tutorial.getUrl(), false, false);
        }

        System.err.println("warning: unmapped simple header info creation, type: " + mo.getClass().getName());

        return new SimpleHeaderInfo(mo.getName(), mo.getDescription(), null, false, false);
    }
}