package com.dlsc.jfxcentral2.mobile.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import one.jpro.jproutils.treeshowing.TreeShowing;

import java.net.URL;
import java.util.Objects;

public class WelcomePageView extends PageView {

    private static final String DEFAULT_STYLE_CLASS = "welcome-page-view";

    public record PageDate(String title, String description, String imageUrl) {
    }

    public static final PageDate[] PAGES = {
            // new PageDate("JavaFX Books", "A collection of books about JavaFX.", "books.png"),
            new PageDate("Videos", "Watch JavaFX videos, conference talks, demos, and tutorials for all expertise levels.", "videos.png"),
            new PageDate("Libraries", "Explore JavaFX third-party libraries, featuring components, game engines, styles, 3D graphics, and frameworks to enhance your projects.", "libraries.png"),
            // new PageDate("JavaFX Tutorials", "A collection of tutorials for JavaFX.", "tutorials.png"),
            new PageDate("Tools", "Explore JavaFX tools including plugins, layout, CSS tools, testing, and packaging.", "tools.png"),
            new PageDate("Links of The Week", "Miscellaneous stuff found on the web that is related to JavaFX.", "news.png"),
            // new PageDate("JavaFX Blogs", "A collection of blogs about JavaFX.", "blogs.png"),
            new PageDate("People", "A curated list of people connected to JavaFX. They develop libraries, applications, tools or they present at conferences and evangelise JavaFX.", "people.png"),
            new PageDate("Companies", "Explore companies in JavaFX, especially those with significant influence and contributions to the JavaFX community, shaping and advancing the ecosystem.", "companies.png")
    };

    private final WelcomePageCell[] cells = new WelcomePageCell[PAGES.length];

    public WelcomePageView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setMouseTransparent(true);
        setSwitchPageDuration(Duration.seconds(1));
        setPageFactory(pageIndex -> {
            int index = pageIndex % PAGES.length;
            if (cells[index] == null) {
                PageDate pageDate = PAGES[index];
                WelcomePageCell cell = new WelcomePageCell();
                cell.getStyleClass().add(pageDate.title().toLowerCase().replace(" ", "-"));
                URL resource = WelcomePageView.class.getResource(pageDate.imageUrl());
                cell.setImage(new Image(Objects.requireNonNull(resource).toExternalForm()));
                cell.setTitle(pageDate.title());
                cell.setDescription(pageDate.description());
                cells[index] = cell;
            }
            return cells[index];
        });
        setCurrentPageIndex((int) (Math.random() * PAGES.length));


        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), evt -> {
            gotoNextPage();
        }));

        TreeShowing.treeShowing(this).addListener((obs, ov, nv) -> {
            if (nv) {
                timeline.play();
                System.out.println(">> Start Animation");
            } else {
                timeline.stop();
                System.out.println(">> Stop Animation");
            }
        });
    }

}
