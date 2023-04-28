package com.dlsc.jfxcentral2.model;

public class Book extends SimpleHeaderInfo {
    public Book() {
    }

    public Book(String name, String description, String website, boolean saved, boolean liked) {
        super(name, description, website, saved, liked);
    }
}
