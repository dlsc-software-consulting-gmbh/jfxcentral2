package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.detailsbox.DetailsBoxBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DetailsContentPane extends PaneBase {

    private final MenuView menuView = new MenuView();
    private final CommentsView commentsView = new CommentsView();
    private final FeaturesContainer featuresContainer = new FeaturesContainer();
    private final VBox detailBoxesContainer = new VBox();

    public DetailsContentPane() {
        getStyleClass().add("details-content-pane");

        menuView.sizeProperty().bind(sizeProperty());
        BorderPane.setAlignment(menuView, Pos.TOP_CENTER);

        commentsView.sizeProperty().bind(sizeProperty());
        BorderPane.setAlignment(featuresContainer, Pos.TOP_CENTER);

        featuresContainer.setSize(Size.SMALL);
        BorderPane.setAlignment(featuresContainer, Pos.TOP_CENTER);

        Bindings.bindContent(detailBoxesContainer.getChildren(), detailBoxes);
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
        if (getSize().equals(Size.SMALL)) {
            menuView.setOrientation(Orientation.HORIZONTAL);

            VBox box = new VBox(menuView, detailBoxesContainer, commentsView);
            box.getStyleClass().add("inner-box");
            getChildren().setAll(box);
        } else {
            menuView.setOrientation(Orientation.VERTICAL);

            VBox box = new VBox(detailBoxesContainer, commentsView);
            box.getStyleClass().add("inner-box");

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(box);
            borderPane.setLeft(menuView);
            borderPane.setRight(featuresContainer);

            getChildren().setAll(borderPane);
        }
    }

    private final ObservableList<DetailsBoxBase<?>> detailBoxes = FXCollections.observableArrayList();

    public ObservableList<DetailsBoxBase<?>> getDetailBoxes() {
        return detailBoxes;
    }
}
