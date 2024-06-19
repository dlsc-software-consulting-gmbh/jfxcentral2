package com.dlsc.jfxcentral2.mobile;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Optional;

public class BottomBar extends HBox {

    public BottomBar() {
        getStyleClass().add("bottom-bar");

        CustomToggleButton homeButton = new CustomToggleButton("Home", new FontIcon(MaterialDesign.MDI_HOME));
        homeButton.setOnAction(e -> Optional.ofNullable(getOnHomeAction()).ifPresent(Runnable::run));

        CustomToggleButton newsButton = new CustomToggleButton("News", new FontIcon(MaterialDesign.MDI_NEWSPAPER));
        newsButton.setOnAction(e -> Optional.ofNullable(getOnNewsAction()).ifPresent(Runnable::run));

        CustomToggleButton videosButton = new CustomToggleButton("Videos", new FontIcon(MaterialDesign.MDI_VIDEO));
        videosButton.setOnAction(e -> Optional.ofNullable(getOnVideosAction()).ifPresent(Runnable::run));

        CustomToggleButton libraryButton = new CustomToggleButton("Library", new FontIcon(MaterialDesign.MDI_BOOKMARK));
        libraryButton.setOnAction(e -> Optional.ofNullable(getOnLibraryAction()).ifPresent(Runnable::run));

        getChildren().addAll(homeButton, newsButton, videosButton, libraryButton);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(homeButton, newsButton, videosButton, libraryButton);

        setMaxHeight(Region.USE_PREF_SIZE);

        // select home button by default
        homeButton.fire();
    }

    private final ObjectProperty<Runnable> onHomeAction = new SimpleObjectProperty<>(this, "onHomeAction");

    public final ObjectProperty<Runnable> onHomeActionProperty() {
        return onHomeAction;
    }

    public final Runnable getOnHomeAction() {
        return onHomeAction.get();
    }

    public final void setOnHomeAction(Runnable value) {
        onHomeAction.set(value);
    }

    private final ObjectProperty<Runnable> onNewsAction = new SimpleObjectProperty<>(this, "onNewsAction");

    public final ObjectProperty<Runnable> onNewsActionProperty() {
        return onNewsAction;
    }

    public final Runnable getOnNewsAction() {
        return onNewsAction.get();
    }

    public final void setOnNewsAction(Runnable value) {
        onNewsAction.set(value);
    }

    private final ObjectProperty<Runnable> onVideosAction = new SimpleObjectProperty<>(this, "onVideosAction");

    public final ObjectProperty<Runnable> onVideosActionProperty() {
        return onVideosAction;
    }

    public final Runnable getOnVideosAction() {
        return onVideosAction.get();
    }

    public final void setOnVideosAction(Runnable value) {
        onVideosAction.set(value);
    }

    private final ObjectProperty<Runnable> onLibraryAction = new SimpleObjectProperty<>(this, "onLibraryAction");

    public final ObjectProperty<Runnable> onLibraryActionProperty() {
        return onLibraryAction;
    }

    public final Runnable getOnLibraryAction() {
        return onLibraryAction.get();
    }

    public final void setOnLibraryAction(Runnable value) {
        onLibraryAction.set(value);
    }

}
