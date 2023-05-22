package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.detailsbox.DetailsBoxBase;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CategoryContentPane extends PaneBase {

    private final CommentsView commentsView = new CommentsView();
    private final FeaturesContainer featuresContainer = new FeaturesContainer();

    public CategoryContentPane(Node... nodes) {
        getStyleClass().add("category-content-pane");

        getNodes().setAll(nodes);

        commentsView.sizeProperty().bind(sizeProperty());
        featuresContainer.sizeProperty().bind(sizeProperty());

        VBox nodesBox = new VBox();
        nodesBox.getStyleClass().add("nodes-box");
        nodesBox.setAlignment(Pos.TOP_CENTER);
        Bindings.bindContent(nodesBox.getChildren(), nodesProperty());

        VBox contentBox = new VBox(nodesBox, featuresContainer);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.getStyleClass().add("content-box");

        getChildren().setAll(contentBox);
    }

    public CategoryContentPane() {
    }

    private final ListProperty<Node> nodes = new SimpleListProperty<>(this, "nodes", FXCollections.observableArrayList());

    public ObservableList<Node> getNodes() {
        return nodes.get();
    }

    public ListProperty<Node> nodesProperty() {
        return nodes;
    }

    public void setNodes(ObservableList<Node> nodes) {
        this.nodes.set(nodes);
    }

    public CommentsView getCommentsView() {
        return commentsView;
    }

    public FeaturesContainer getFeaturesContainer() {
        return featuresContainer;
    }

    private final ObservableList<DetailsBoxBase<?>> detailBoxes = FXCollections.observableArrayList();

    public ObservableList<DetailsBoxBase<?>> getDetailBoxes() {
        return detailBoxes;
    }
}
