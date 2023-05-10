package com.dlsc.jfxcentral2.model;

public class App extends SimpleHeaderInfo {

    public App() {
    }

    public App(String name, String website, boolean saved, boolean liked) {
        this(name, null, website, saved, liked);
    }

    public App(String name, String description, String website, boolean saved, boolean liked) {
        super(name, description, website, saved, liked);
    }
}