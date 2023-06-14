package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.TopPane;
import com.dlsc.jfxcentral2.components.hamburger.HamburgerMenuView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public Node wrapContent(Region... content) {
        VBox vbox = new VBox();
        vbox.getStyleClass().add("ui");
        vbox.setFillWidth(true);
        VBox.setVgrow(vbox, Priority.ALWAYS);
        updateStyleClassBasedOnSize(vbox);
        sizeProperty().addListener(it -> updateStyleClassBasedOnSize(vbox));

        // menubar
        TopMenuBar topMenuBar = new TopMenuBar(isMobile());
        topMenuBar.sizeProperty().bind(sizeProperty());
        topMenuBar.setMode(menuMode);
        topMenuBar.showHamburgerMenuProperty().bindBidirectional(showHamburgerMenuProperty());

        if (getClass().equals(StartPage.class)) {
            topMenuBar.getStyleClass().add("start-page");
        }

        // footer
        FooterView footerView = new FooterView();
        footerView.sizeProperty().bind(sizeProperty());

        // sponsors
        SponsorsView sponsorsView = new SponsorsView();
        sponsorsView.sizeProperty().bind(sizeProperty());

        // copyright
        CopyrightView copyrightView = new CopyrightView();
        copyrightView.sizeProperty().bind(sizeProperty());

        // hamburger menu
        HamburgerMenuView hamburgerMenuView = new HamburgerMenuView(isMobile());
        hamburgerMenuView.sizeProperty().bind(sizeProperty());
        hamburgerMenuView.visibleProperty().bind(sizeProperty().isNotEqualTo(Size.LARGE).and(showHamburgerMenuProperty()));
        hamburgerMenuView.managedProperty().bind(sizeProperty().isNotEqualTo(Size.LARGE).and(showHamburgerMenuProperty()));
        hamburgerMenuView.setOnClose(() -> setShowHamburgerMenu(false));

        StackPane.setAlignment(hamburgerMenuView, Pos.TOP_CENTER);

        VBox uiBox = new VBox(content);
        VBox.setVgrow(uiBox, Priority.ALWAYS);
        uiBox.setAlignment(Pos.BOTTOM_CENTER);

        StackPane.setAlignment(uiBox, Pos.TOP_CENTER);

        TopPane topStackPane = new TopPane(topMenuBar, uiBox);
        topStackPane.sizeProperty().bind(sizeProperty());
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);
        VBox.setVgrow(topStackPane, Priority.ALWAYS);

        StackPane.setAlignment(vbox, Pos.TOP_CENTER);

        vbox.getChildren().addAll(topStackPane, sponsorsView, footerView, copyrightView);

        StackPane glassPane = new StackPane();
        glassPane.getStyleClass().add("glass-pane");
        glassPane.setMouseTransparent(true);
        glassPane.visibleProperty().bind(topMenuBar.usedProperty().or(blockingProperty()));

        StackPane root = new StackPane(vbox, glassPane, hamburgerMenuView);
        root.getStyleClass().add("background");

        return root;
    }

    // flag to control the visibility of the hamburger menu

    private final BooleanProperty showHamburgerMenu = new SimpleBooleanProperty(this, "showHamburgerMenu");

    public boolean isShowHamburgerMenu() {
        return showHamburgerMenu.get();
    }

    public BooleanProperty showHamburgerMenuProperty() {
        return showHamburgerMenu;
    }

    public void setShowHamburgerMenu(boolean showHamburgerMenu) {
        this.showHamburgerMenu.set(showHamburgerMenu);
    }

    // blocking property to control the glass pane

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

    /**
     * Useful utility method to create a random sublist of a given list.
     *
     * @param list the original list
     * @param newSize the requested size for the random sublist
     * @return a new list containing a fixed number of
     * @param <T> the type of the items in the list
     */
    public <T> List<T> randomSubList(List<T> list, int newSize) {
        list = new ArrayList<>(list);
        Collections.shuffle(list);
        return list.subList(0, Math.min(newSize, list.size()));
    }
}
