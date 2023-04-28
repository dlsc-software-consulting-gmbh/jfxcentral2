package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

public class App extends TileData {
    public App() {
    }

    public App(String title, String description, Image thumbnail, String url, boolean save, boolean like) {
        super(title, description, thumbnail, url, save, like);
    }
}
