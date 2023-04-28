package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

import java.util.List;

public record Person(String name, Image avatar, String description, String twitterUrl, String linkedInUrl, String websiteUrl, String mailUrl, String githubUrl, List<Badge> badges) {
}
