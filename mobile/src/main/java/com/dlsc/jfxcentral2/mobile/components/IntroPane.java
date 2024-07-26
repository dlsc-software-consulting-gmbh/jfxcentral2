package com.dlsc.jfxcentral2.mobile.components;

import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IntroPane extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "intro-pane";
    public static final String DOT = "dot";
    public static final String SELECTED = "selected";
    private final PageView pageView;

    public record IntroCardData(String title, String description, String imageUrl) {
    }

    public final List<IntroCardData> introCardData = new ArrayList<>(List.of(
            new IntroCardData("JavaFX Books", "Explore our JavaFX book collection, from beginner guides to advanced tutorials, including game development and use-cases like JavaFX for Raspberry Pi.", "books.png"),
            new IntroCardData("Videos", "Watch JavaFX videos, conference talks, demos, and tutorials for all expertise levels.", "videos.png"),
            new IntroCardData("Libraries", "Explore JavaFX third-party libraries, featuring components, game engines, styles, 3D graphics, and frameworks to enhance your projects.", "libraries.png"),
            new IntroCardData("JavaFX Tutorials", "Browse tutorials ranging from JavaFX basics to advanced application and game development.", "tutorials.png"),
            new IntroCardData("Tools", "Explore JavaFX tools including plugins, layout, CSS tools, testing, and packaging.", "tools.png"),
            new IntroCardData("Links of The Week", "Miscellaneous stuff found on the web that is related to JavaFX.", "news.png"),
            new IntroCardData("Tips & Tricks", "Discover practical JavaFX tips in this section, featuring articles that share real-world techniques and tricks for effective JavaFX development.", "tips.png"),
            new IntroCardData("JavaFX Blogs", "Visit our blog section for articles from JavaFX experts, featuring practical tips, cutting-edge insights, and more from the world of JavaFX.", "blogs.png"),
            new IntroCardData("People", "A curated list of people connected to JavaFX. They develop libraries, applications, tools or they present at conferences and evangelise JavaFX.", "people.png"),
            new IntroCardData("Companies", "Explore companies in JavaFX, especially those with significant influence and contributions to the JavaFX community, shaping and advancing the ecosystem.", "companies.png")
    ));

    public IntroPane() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // shuffle pages
        Collections.shuffle(introCardData);

        // page view
        pageView = new PageView();
        pageView.setSwitchPageDuration(Duration.seconds(0.5));
        pageView.setPageFactory(pageIndex -> {
            int index = pageIndex % introCardData.size();

            IntroCardData cardData = introCardData.get(index);
            IntroCard cell = new IntroCard();
            cell.getStyleClass().add(cardData.title().toLowerCase().replace(" ", "-"));
            URL resource = IntroPane.class.getResource(cardData.imageUrl());
            cell.setImage(new Image(Objects.requireNonNull(resource).toExternalForm()));
            cell.setTitle(cardData.title());
            cell.setDescription(cardData.description());

            return cell;
        });
        pageView.setCurrentPageIndex(0);
        HBox progressBox = createProgressBox();

        VBox.setVgrow(pageView, Priority.ALWAYS);
        getChildren().addAll(pageView, progressBox);
    }

    private HBox createProgressBox() {
        List<Region> dots = new ArrayList<>();
        for (int i = 0; i < introCardData.size(); i++) {
            Region dot = new Region();
            dot.getStyleClass().add(DOT);
            int finalI = i;
            dot.setOnMousePressed(evt -> pageView.setCurrentPageIndex(finalI));
            dots.add(dot);
        }

        dots.get(0).getStyleClass().add(SELECTED);

        pageView.currentPageIndexProperty().addListener((obs, oldPage, newPage) -> {
            int oldIndex = oldPage.intValue() % introCardData.size();
            int newIndex = newPage.intValue() % introCardData.size();

            dots.get(oldIndex).getStyleClass().remove(SELECTED);
            dots.get(newIndex).getStyleClass().add(SELECTED);
        });

        HBox dotBox = new HBox();
        dotBox.getStyleClass().add("dot-box");
        dotBox.getChildren().addAll(dots);

        return dotBox;
    }

}
