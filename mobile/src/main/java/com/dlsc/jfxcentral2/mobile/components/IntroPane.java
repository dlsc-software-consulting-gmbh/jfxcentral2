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
            new IntroCardData("Books", "Beginner guides, advanced tutorials, game development.", "books.png"),
            new IntroCardData("Videos", "Watch videos of conference talks, demos, and tutorials.", "videos.png"),
            new IntroCardData("Libraries", "Explore third-party libraries that you can add to your own applications.", "libraries.png"),
            new IntroCardData("Tutorials", "See a listing of places where you can find good online tutorials.", "tutorials.png"),
            new IntroCardData("Tools", "Find out which tools are available for styling, testing, packaging, etc...", "tools.png"),
            new IntroCardData("Links of The Week", "Miscellaneous stuff found on the web that is related to JavaFX.", "news.png"),
            new IntroCardData("Tips & Tricks", "Real-world tips and tricks for effective user interface development.", "tips.png"),
            new IntroCardData("Blogs", "A collection of blogs related to JavaFX", "blogs.png"),
            new IntroCardData("People", "People that influence the JavaFX ecosystem.", "people.png"),
            new IntroCardData("Companies", "Companies that contribute to the JavaFX ecosystem.", "companies.png")
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
