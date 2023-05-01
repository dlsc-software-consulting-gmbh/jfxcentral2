package com.dlsc.jfxcentral2.app.pages;


import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import one.jpro.routing.View;

public abstract class DefaultPage extends View {

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public Size getSize() {
        return sizeProperty().get();
    }

    public DefaultPage(ObjectProperty<Size> size) {
        this.size.bind(size);
    }

    public Node wrapContent(Node content) {
        VBox vbox = new VBox();
        vbox.getStyleClass().add("ui");
        updateStyleClassBasedOnSize(vbox);
        sizeProperty().addListener(it -> updateStyleClassBasedOnSize(vbox));

        // menubar
        TopMenuBar topMenuBar = new TopMenuBar();
        topMenuBar.sizeProperty().bind(sizeProperty());

        // footer
        FooterView footerView = new FooterView();
        footerView.sizeProperty().bind(sizeProperty());

        // sponsors
        SponsorsView sponsorsView = new SponsorsView();
        sponsorsView.sizeProperty().bind(sizeProperty());

        // copyright
        CopyrightView copyrightView = new CopyrightView();
        copyrightView.sizeProperty().bind(sizeProperty());

        StackPane topStackPane = new StackPane(content, topMenuBar);
        topStackPane.getStyleClass().add("top");
        updateStyleClassBasedOnSize(topStackPane);
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);

        StackPane.setAlignment(vbox, Pos.TOP_CENTER);

        vbox.getChildren().addAll(topStackPane, sponsorsView, footerView, copyrightView);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(vbox.widthProperty());
        clip.heightProperty().bind(vbox.heightProperty());
        vbox.setClip(clip);

        StackPane root = new StackPane(vbox);
        root.setOnContextMenuRequested(evt -> {
            ContextMenu menu = new ContextMenu();
            for (Size size : Size.values()) {
                MenuItem item = new MenuItem(size.name());
                item.setOnAction(e -> sizeProperty().set(size));
                menu.getItems().add(item);
            }
            menu.show(root.getScene().getWindow());
        });
        root.getStyleClass().add("background");

        return root;
    }

    private void updateStyleClassBasedOnSize(Node node) {
        node.getStyleClass().removeAll("lg", "md", "sm");
        switch (getSize()) {
            case SMALL -> node.getStyleClass().add("sm");
            case MEDIUM -> node.getStyleClass().add("md");
            case LARGE -> node.getStyleClass().add("lg");
        }
    }
}
