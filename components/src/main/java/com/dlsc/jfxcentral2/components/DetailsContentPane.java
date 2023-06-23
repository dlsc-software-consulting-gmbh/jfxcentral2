package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.detailsbox.DetailsBoxBase;
import com.dlsc.jfxcentral2.model.NameProvider;
import com.dlsc.jfxcentral2.model.Size;
import com.jpro.webapi.WebAPI;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DetailsContentPane extends PaneBase {

    private final MenuView menuView = new MenuView();

    private final CommentsView commentsView = new CommentsView();
    private final FeaturesContainer featuresContainer = new FeaturesContainer();
    private final VBox detailBoxesContainer = new VBox();
    private final HBox contentBox;
    private final VBox centerBox;

    public DetailsContentPane() {
        getStyleClass().add("details-content-pane");

        menuView.sizeProperty().bind(sizeProperty());
        menuView.orientationProperty().bind(Bindings.createObjectBinding(() -> getSize().equals(Size.LARGE) ? Orientation.VERTICAL : Orientation.HORIZONTAL, sizeProperty()));

        /*
         * We do not show the menu view in medium or small size if the menu only contains
         * a single item. In large size we always show it, otherwise the layout would not
         * be symmetrical.
         */
        menuView.visibleProperty().bind(sizeProperty().isEqualTo(Size.LARGE));
        menuView.managedProperty().bind(menuView.visibleProperty());

        HBox.setHgrow(menuView, Priority.NEVER);

        commentsView.sizeProperty().bind(sizeProperty());
        commentsView.setVisible(WebAPI.isBrowser());
        commentsView.setManaged(WebAPI.isBrowser());

        detailBoxes.addListener((Observable it) -> updateMenuView());
        centerNodes.addListener((Observable it) -> updateMenuView());

        centerNodesProperty().addListener((Observable it) -> layoutBySize());

        /*
         * When the page has enough horizontal space to be in "large" size then we can place
         * the features container to the right of the detail boxes. But the features have to be
         * small. When the page is in medium size then we show the features below the detail
         * boxes in medium size. When the page is small then again do we use the small size of the features.
         */
        featuresContainer.sizeProperty().bind(Bindings.createObjectBinding(() -> switch (getSize()) {
            case SMALL, LARGE -> Size.SMALL;
            case MEDIUM -> Size.MEDIUM;
        }, sizeProperty()));

        featuresContainer.orientationProperty().bind(Bindings.createObjectBinding(() -> getSize().equals(Size.LARGE) ? Orientation.VERTICAL : Orientation.HORIZONTAL, sizeProperty()));
        HBox.setHgrow(featuresContainer, Priority.NEVER);

        detailBoxesContainer.getStyleClass().add("boxes-container");

        Bindings.bindContent(detailBoxesContainer.getChildren(), detailBoxes);

        centerBox = new VBox();
        centerBox.getStyleClass().add("center-box");
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        contentBox = new HBox();
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    private void updateMenuView() {
        menuView.getItems().clear();

        centerNodes.forEach(item -> {
            if (item instanceof NameProvider nameProvider) {
                MenuView.Item overviewItem = new MenuView.Item(nameProvider.getName().toUpperCase(), null, null);
                menuView.getItems().add(overviewItem);
            }
        });

        detailBoxes.forEach(box -> {
            String title = box.getTitle();
            MenuView.Item boxItem = new MenuView.Item(title.toUpperCase(), null, null);
            menuView.getItems().add(boxItem);
        });
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
            centerBox.getChildren().setAll(getCenterNodes());
            centerBox.getChildren().addAll(detailBoxesContainer, commentsView);
            VBox intermediateBox = new VBox(menuView, centerBox, featuresContainer);
            intermediateBox.getStyleClass().add("intermediate-box");
            intermediateBox.setAlignment(Pos.TOP_CENTER);
            HBox.setHgrow(intermediateBox, Priority.ALWAYS);
            contentBox.getChildren().setAll(intermediateBox);
        } else {
            centerBox.getChildren().setAll(getCenterNodes());
            centerBox.getChildren().addAll(detailBoxesContainer, commentsView);
            contentBox.getChildren().setAll(menuView, centerBox, featuresContainer);
        }
    }

    private final ListProperty<Node> centerNodes = new SimpleListProperty<>(this, "centerNodes", FXCollections.observableArrayList());

    public ObservableList<Node> getCenterNodes() {
        return centerNodes.get();
    }

    public ListProperty<Node> centerNodesProperty() {
        return centerNodes;
    }

    public void setCenterNodes(ObservableList<Node> centerNodes) {
        this.centerNodes.set(centerNodes);
    }

    private final ObservableList<DetailsBoxBase<?>> detailBoxes = FXCollections.observableArrayList();

    public ObservableList<DetailsBoxBase<?>> getDetailBoxes() {
        return detailBoxes;
    }
}
