package com.dlsc.jfxcentral2.app.utils;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Set;

public class PrettyScrollPane extends ScrollPane {

    private final Region shadow = new Region();
    private final ScrollBar vBar = new ScrollBar();
    private final ScrollBar hBar = new ScrollBar();
    private final Button scrollToTopButton = new Button("Scroll Up");

    public PrettyScrollPane() {
        super();
        init();
    }

    public PrettyScrollPane(Node content) {
        super(content);
        init();
    }

    private final BooleanProperty showScrollToTopButton = new SimpleBooleanProperty(this, "showScrollToTopButton", true);

    public final boolean isShowScrollToTopButton() {
        return showScrollToTopButton.get();
    }

    public final BooleanProperty showScrollToTopButtonProperty() {
        return showScrollToTopButton;
    }

    public final void setShowScrollToTopButton(boolean showScrollToTopButton) {
        this.showScrollToTopButton.set(showScrollToTopButton);
    }

    private final BooleanProperty scrollToTopButtonNeeded = new SimpleBooleanProperty();

    private void init() {
        skinProperty().addListener(it -> {
            // first bind, then add new scrollbars, otherwise the new bars will be found
            bindScrollBars();
            getChildren().addAll(vBar, hBar, shadow, scrollToTopButton);
        });

        scrollToTopButton.setGraphic(new FontIcon());
        scrollToTopButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        scrollToTopButton.getStyleClass().addAll("scroll-to-top-button");
        scrollToTopButton.setVisible(false);
        scrollToTopButton.setOpacity(0);
        scrollToTopButton.setOnAction(evt -> {
            KeyValue value = new KeyValue(vvalueProperty(), 0);
            KeyFrame frame = new KeyFrame(Duration.millis(150), value);
            Timeline timeline = new Timeline(frame);
            timeline.play();
        });

        scrollToTopButtonNeeded.bind(vvalueProperty().greaterThan(0));
        scrollToTopButtonNeeded.addListener((it, oldNeeded, newNeeded) -> {
            if (isShowScrollToTopButton()) {
                if (newNeeded) {
                    showButton();
                } else {
                    hideButton();
                }
            }
        });

        showScrollToTopButton.addListener(it -> {
            if (isShowScrollToTopButton()) {
                if (scrollToTopButtonNeeded.get()) {
                    showButton();
                }
            } else {
                hideButton();
            }
        });

        getStyleClass().add("pretty-scroll-pane");
        setFitToWidth(true);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setHbarPolicy(ScrollBarPolicy.NEVER);

        vbarPolicyProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != ScrollBarPolicy.NEVER) {
                setVbarPolicy(ScrollBarPolicy.NEVER);
            }
        });

        hbarPolicyProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != ScrollBarPolicy.NEVER) {
                setHbarPolicy(ScrollBarPolicy.NEVER);
            }
        });

        setPannable(true);

        vBar.setManaged(false);
        vBar.setOrientation(Orientation.VERTICAL);
        vBar.getStyleClass().addAll("my-scroll-bar", "vertical-scroll-bar");
        vBar.visibleProperty().bind(hoverProperty());

        hBar.setManaged(false);
        hBar.setOrientation(Orientation.HORIZONTAL);
        hBar.getStyleClass().addAll("my-scroll-bar", "horizontal-scroll-bar");
        hBar.visibleProperty().bind(hBar.visibleAmountProperty().lessThan(1).or(alwaysShowHorizontalScrollBar));

        shadow.setManaged(false);
        shadow.getStyleClass().add("shadow");
        shadow.setMouseTransparent(true);
        shadow.visibleProperty().bind(vvalueProperty().greaterThan(0).or(alwaysShowFullShadowProperty()));

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        vvalueProperty().addListener(it -> {
            if (lastOffset != computeOffset()) {
                requestLayout();
            }
        });
        showShadowProperty().addListener(it -> requestLayout());
    }

    private final BooleanProperty alwaysShowVerticalScrollBar = new SimpleBooleanProperty(this, "alwaysShowVerticalScrollBar", false);

    public boolean isAlwaysShowVerticalScrollBar() {
        return alwaysShowVerticalScrollBar.get();
    }

    public BooleanProperty alwaysShowVerticalScrollBarProperty() {
        return alwaysShowVerticalScrollBar;
    }

    public void setAlwaysShowVerticalScrollBar(boolean alwaysShowVerticalScrollBar) {
        this.alwaysShowVerticalScrollBar.set(alwaysShowVerticalScrollBar);
    }

    private final BooleanProperty alwaysShowHorizontalScrollBar = new SimpleBooleanProperty(this, "alwaysShowHorizontalScrollBar", false);

    public boolean isAlwaysShowHorizontalScrollBar() {
        return alwaysShowHorizontalScrollBar.get();
    }

    public BooleanProperty alwaysShowHorizontalScrollBarProperty() {
        return alwaysShowHorizontalScrollBar;
    }

    public void setAlwaysShowHorizontalScrollBar(boolean alwaysShowHorizontalScrollBar) {
        this.alwaysShowHorizontalScrollBar.set(alwaysShowHorizontalScrollBar);
    }

    private void hideButton() {
        FadeTransition transition = new FadeTransition(Duration.millis(150), scrollToTopButton);
        transition.setToValue(0);
        transition.setOnFinished(evt -> scrollToTopButton.setVisible(false));
        transition.play();
    }

    private void showButton() {
        scrollToTopButton.setVisible(true);
        FadeTransition transition = new FadeTransition(Duration.millis(150), scrollToTopButton);
        transition.setToValue(1);
        transition.play();
    }

    public final ScrollBar getVerticalScrollBar() {
        return vBar;
    }

    public final ScrollBar getHorizontalScrollBar() {
        return hBar;
    }

    private final BooleanProperty showShadow = new SimpleBooleanProperty(this, "showShadow", true);

    public final BooleanProperty showShadowProperty() {
        return showShadow;
    }

    public final boolean isShowShadow() {
        return showShadow.get();
    }

    public final void setShowShadow(boolean show) {
        showShadow.set(show);
    }

    private final BooleanProperty alwaysShowFullShadow = new SimpleBooleanProperty(this, "alwaysShowFullShadow", false);

    public final boolean isAlwaysShowFullShadow() {
        return alwaysShowFullShadow.get();
    }

    public final BooleanProperty alwaysShowFullShadowProperty() {
        return alwaysShowFullShadow;
    }

    public final void setAlwaysShowFullShadow(boolean alwaysShowFullShadow) {
        this.alwaysShowFullShadow.set(alwaysShowFullShadow);
    }

    private void bindScrollBars() {
        Set<Node> nodes = lookupAll("ScrollBar");
        for (Node node : nodes) {
            if (node instanceof ScrollBar bar) {
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    bindScrollBars(vBar, bar);
                } else if (bar.getOrientation().equals(Orientation.HORIZONTAL)) {
                    bindScrollBars(hBar, bar);
                }
            }
        }
    }

    private void bindScrollBars(ScrollBar scrollBarA, ScrollBar scrollBarB) {
        scrollBarA.valueProperty().bindBidirectional(scrollBarB.valueProperty());
        scrollBarA.minProperty().bindBidirectional(scrollBarB.minProperty());
        scrollBarA.maxProperty().bindBidirectional(scrollBarB.maxProperty());
        scrollBarA.visibleAmountProperty().bindBidirectional(scrollBarB.visibleAmountProperty());
        scrollBarA.unitIncrementProperty().bindBidirectional(scrollBarB.unitIncrementProperty());
        scrollBarA.blockIncrementProperty().bindBidirectional(scrollBarB.blockIncrementProperty());
    }

    private static final int SHADOW_HEIGHT = 30;


    private final ObjectProperty<Insets> verticalScrollBarPadding = new SimpleObjectProperty<>(this, "verticalScrollBarPadding", new Insets(5));

    public Insets getVerticalScrollBarPadding() {
        return verticalScrollBarPadding.get();
    }

    public ObjectProperty<Insets> verticalScrollBarPaddingProperty() {
        return verticalScrollBarPadding;
    }

    public void setVerticalScrollBarPadding(Insets verticalScrollBarPadding) {
        this.verticalScrollBarPadding.set(verticalScrollBarPadding);
    }

    private final ObjectProperty<Insets> horizontalScrollBarPadding = new SimpleObjectProperty<>(this, "horizontalScrollBarPadding", new Insets(5));

    public Insets getHorizontalScrollBarPadding() {
        return horizontalScrollBarPadding.get();
    }

    public ObjectProperty<Insets> horizontalScrollBarPaddingProperty() {
        return horizontalScrollBarPadding;
    }

    public void setHorizontalScrollBarPadding(Insets horizontalScrollBarPadding) {
        this.horizontalScrollBarPadding.set(horizontalScrollBarPadding);
    }

    @Override
    protected void layoutChildren() {
        Insets insets = getInsets();
        double w = getWidth();
        double h = getHeight();
        double vBarWidth = vBar.prefWidth(-1);
        double vBarX = w - vBarWidth - insets.getRight() - getVerticalScrollBarPadding().getRight();
        double vBarY = insets.getTop() + getVerticalScrollBarPadding().getTop();
        vBar.resizeRelocate(vBarX, vBarY, vBarWidth, h - insets.getTop() - insets.getBottom() - getVerticalScrollBarPadding().getTop() - getVerticalScrollBarPadding().getBottom());

        double hBarHeight = hBar.prefHeight(-1);
        hBar.resizeRelocate(insets.getLeft() + getHorizontalScrollBarPadding().getLeft(), h - hBarHeight - insets.getBottom() - getHorizontalScrollBarPadding().getBottom(), w - insets.getLeft() - insets.getRight() - getHorizontalScrollBarPadding().getLeft() - getHorizontalScrollBarPadding().getRight(), hBarHeight);

        if (isShowShadow()) {
            double offset = computeOffset();
            shadow.resizeRelocate(-10, insets.getTop() - shadow.prefHeight(-1) - SHADOW_HEIGHT + offset, w + 20, shadow.prefHeight(-1) - 1);
            lastOffset = offset;
        }

        double pw = scrollToTopButton.prefWidth(-1);
        double ph = scrollToTopButton.prefHeight(-1);

        scrollToTopButton.resizeRelocate(vBarX - pw - 10, h - ph - 35, pw, ph);

        super.layoutChildren();
    }

    private double lastOffset;

    private double computeOffset() {
        if (isAlwaysShowFullShadow()) {
            return SHADOW_HEIGHT;
        } else {
            Node content = getContent();
            if (content != null) {
                return Math.min(getVvalue() * getContent().prefHeight(-1), SHADOW_HEIGHT);
            }

            return 0;
        }
    }
}
