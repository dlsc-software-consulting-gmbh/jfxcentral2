package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.detailsbox.DetailsBoxBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DetailsContentPane extends PaneBase {

    private final MenuView menuView = new MenuView();

    private final CommentsView commentsView = new CommentsView();
    private final FeaturesContainer featuresContainer = new FeaturesContainer();
    private final VBox detailBoxesContainer = new VBox();

    private final InvalidationListener sceneListener = it -> {
        Scene scene = getScene();
        if (scene != null) {
            featuresContainer.visibleProperty().bind(Bindings.createBooleanBinding(() -> scene.getWidth() >= 1440, scene.widthProperty()));
            featuresContainer.managedProperty().bind(featuresContainer.visibleProperty());
        }
    };

    private final WeakInvalidationListener weakSceneListener = new WeakInvalidationListener(sceneListener);
    private final HBox contentBox;
    private final VBox centerBox;

    public DetailsContentPane() {
        getStyleClass().add("details-content-pane");

        menuView.sizeProperty().bind(sizeProperty());
        menuView.orientationProperty().bind(Bindings.createObjectBinding(() -> getSize().equals(Size.LARGE) ? Orientation.VERTICAL : Orientation.HORIZONTAL, sizeProperty()));
        HBox.setHgrow(menuView, Priority.NEVER);

        commentsView.sizeProperty().bind(sizeProperty());

        featuresContainer.setSize(Size.SMALL);
        HBox.setHgrow(featuresContainer, Priority.NEVER);

        sceneProperty().addListener(weakSceneListener);

        detailBoxesContainer.getStyleClass().add("boxes-container");

        Bindings.bindContent(detailBoxesContainer.getChildren(), detailBoxes);

        centerBox = new VBox();
        centerBox.getStyleClass().add("center-box");

        contentBox = new HBox();
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public CommentsView getCommentsView() {
        return commentsView;
    }

    public FeaturesContainer getFeaturesContainer() {
        return featuresContainer;
    }

    @Override
    protected void layoutBySize() {
        Size size = getSize();

        if (size.equals(Size.SMALL) || size.equals(Size.MEDIUM)) {
            centerBox.getChildren().setAll(menuView, detailBoxesContainer, commentsView);
            contentBox.getChildren().setAll(centerBox);
        } else {
            centerBox.getChildren().setAll(detailBoxesContainer, commentsView);
            contentBox.getChildren().setAll(menuView, centerBox, featuresContainer);
        }
    }

    private final ObservableList<DetailsBoxBase<?>> detailBoxes = FXCollections.observableArrayList();

    public ObservableList<DetailsBoxBase<?>> getDetailBoxes() {
        return detailBoxes;
    }
}
