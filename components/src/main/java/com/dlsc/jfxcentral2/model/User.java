package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

import java.util.List;

public record User(String id, String name, Image avatar, List<Badge> badges) {

}
