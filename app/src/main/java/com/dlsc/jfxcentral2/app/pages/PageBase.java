package com.dlsc.jfxcentral2.app.pages;


import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.TopPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.View;

import java.util.Objects;

public abstract class PageBase extends View {

    private final TopMenuBar.Mode menuMode;

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public Size getSize() {
        return sizeProperty().get();
    }

    public PageBase(ObjectProperty<Size> size, TopMenuBar.Mode menuMode) {
        this.size.bind(size);
        this.menuMode = Objects.requireNonNull(menuMode);
    }

    public Node wrapContent(Region content) {
        VBox vbox = new VBox();
        vbox.getStyleClass().add("ui");
        vbox.setFillWidth(true);
        updateStyleClassBasedOnSize(vbox);
        sizeProperty().addListener(it -> updateStyleClassBasedOnSize(vbox));

        // menubar
        TopMenuBar topMenuBar = new TopMenuBar();
        topMenuBar.sizeProperty().bind(sizeProperty());
        topMenuBar.setMode(menuMode);

        // footer
        FooterView footerView = new FooterView();
        footerView.sizeProperty().bind(sizeProperty());

        // sponsors
        SponsorsView sponsorsView = new SponsorsView();
        sponsorsView.sizeProperty().bind(sizeProperty());

        // copyright
        CopyrightView copyrightView = new CopyrightView();
        copyrightView.sizeProperty().bind(sizeProperty());

        TopPane topStackPane = new TopPane(topMenuBar, content);
        updateStyleClassBasedOnSize(topStackPane);
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);

        StackPane.setAlignment(vbox, Pos.TOP_CENTER);

        vbox.getChildren().addAll(topStackPane, sponsorsView, footerView, copyrightView);

        StackPane glassPane = new StackPane();
        glassPane.getStyleClass().add("glass-pane");
        glassPane.setMouseTransparent(true);
        glassPane.visibleProperty().bind(topMenuBar.usedProperty().or(blockingProperty()));

        StackPane root = new StackPane(vbox, glassPane);
        root.getStyleClass().add("background");

        return root;
    }

    private final BooleanProperty blocking = new SimpleBooleanProperty(this, "blocking");

    public boolean isBlocking() {
        return blocking.get();
    }

    public BooleanProperty blockingProperty() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking.set(blocking);
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
