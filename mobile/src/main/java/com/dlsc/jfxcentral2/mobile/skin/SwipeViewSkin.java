package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.jfxcentral2.mobile.componenets.SwipeView;
import com.dlsc.jfxcentral2.utils.NumberUtil;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

public class SwipeViewSkin extends SkinBase<SwipeView> {

    private double initialX;
    private double initialY;

    private boolean isPlayingAnimation;

    private StackPane currentPane;
    private StackPane nextPane;
    private StackPane previousPane;

    public SwipeViewSkin(SwipeView control) {
        super(control);

        initialize();
        registerListeners();
    }

    /**
     * Initializes the SwipeViewSkin, setting up the initial panes and clipping.
     */
    private void initialize() {
        SwipeView control = getSkinnable();
        int currentIndex = control.getCurrentIndex();
        System.out.println("currentIndex: " + currentIndex);

        // create a rectangle for clipping
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(control.widthProperty());
        clip.heightProperty().bind(control.heightProperty());
        control.setClip(clip);

        // create current page
        currentPane = createPane(currentIndex);
        getChildren().add(currentPane);

        // create next page
        if (currentIndex < control.getPageCount() - 1) {
            nextPane = createPane(currentIndex + 1);
            setPosition(nextPane, 1);
            getChildren().add(nextPane);
        }

        // create previous page
        if (currentIndex > 0) {
            previousPane = createPane(currentIndex - 1);
            setPosition(previousPane, -1);
            getChildren().add(previousPane);
        }
    }

    /**
     * Registers listeners for the SwipeView. And handles the mouse events for swiping.
     */
    private void registerListeners() {
        SwipeView control = getSkinnable();

        control.widthProperty().addListener((observable, oldValue, newValue) -> updatePanePositions(0, 0));
        control.heightProperty().addListener((observable, oldValue, newValue) -> updatePanePositions(0, 0));
        control.swipeOrientationProperty().addListener((observable, oldValue, newValue) -> updatePanePositions(0, 0));

        control.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        control.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        control.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);

        control.currentIndexProperty().addListener((observable, oldValue, newValue) -> updatePaneOnIndexChange(oldValue.intValue(), newValue.intValue()));
    }

    private void handleMousePressed(MouseEvent event) {
        if (isPlayingAnimation) {
            return;
        }
        initialX = event.getSceneX();
        initialY = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (isPlayingAnimation) {
            return;
        }

        SwipeView control = getSkinnable();

        double deltaX = event.getSceneX() - initialX;
        double deltaY = event.getSceneY() - initialY;

        // limit the swipe distance to the container width or height
        deltaX = Math.max(-control.getWidth(), Math.min(deltaX, control.getWidth()));
        deltaY = Math.max(-control.getHeight(), Math.min(deltaY, control.getHeight()));

        double containerSize = isSwipeHorizontal() ? control.getWidth() : control.getHeight();
        double maxEdgeSwipeRatio = NumberUtil.clamp(control.getEdgeSwipeMaxRatio(), 0, 1);

        if (isSwipeHorizontal()) {
            if ((control.getCurrentIndex() == 0 && deltaX > containerSize * maxEdgeSwipeRatio) ||
                    (control.getCurrentIndex() == control.getPageCount() - 1 && deltaX < -containerSize * maxEdgeSwipeRatio)) {
                deltaX = (deltaX > 0) ? containerSize * maxEdgeSwipeRatio : -containerSize * maxEdgeSwipeRatio;
            }
            updatePanePositions(deltaX, 0);
        } else {
            if ((control.getCurrentIndex() == 0 && deltaY > containerSize * maxEdgeSwipeRatio) ||
                    (control.getCurrentIndex() == control.getPageCount() - 1 && deltaY < -containerSize * maxEdgeSwipeRatio)) {
                deltaY = (deltaY > 0) ? containerSize * maxEdgeSwipeRatio : -containerSize * maxEdgeSwipeRatio;
            }
            updatePanePositions(0, deltaY);
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        if (isPlayingAnimation) {
            return;
        }

        SwipeView control = getSkinnable();

        double deltaX = event.getSceneX() - initialX;
        double deltaY = event.getSceneY() - initialY;

        boolean shouldSwitch = false;

        double containerSize = isSwipeHorizontal() ? control.getWidth() : control.getHeight();
        double pageSwitchMinRatio = NumberUtil.clamp(control.getPageSwitchMinRatio(), 0, 1);

        if (isSwipeHorizontal()) {
            if (deltaX < -containerSize * pageSwitchMinRatio && control.getCurrentIndex() < control.getPageCount() - 1) {
                animateSwitchToPage(control.getCurrentIndex() + 1, containerSize, true);
                shouldSwitch = true;
            } else if (deltaX > containerSize * pageSwitchMinRatio && control.getCurrentIndex() > 0) {
                animateSwitchToPage(control.getCurrentIndex() - 1, containerSize, true);
                shouldSwitch = true;
            }
        } else {
            if (deltaY < -containerSize * pageSwitchMinRatio && control.getCurrentIndex() < control.getPageCount() - 1) {
                animateSwitchToPage(control.getCurrentIndex() + 1, containerSize, false);
                shouldSwitch = true;
            } else if (deltaY > containerSize * pageSwitchMinRatio && control.getCurrentIndex() > 0) {
                animateSwitchToPage(control.getCurrentIndex() - 1, containerSize, false);
                shouldSwitch = true;
            }
        }

        if (!shouldSwitch) {
            animateToCurrentIndex();
        }
    }

    private void animateSwitchToPage(int newIndex, double containerSize, boolean isHorizontal) {
        SwipeView control = getSkinnable();

        Duration switchPageDuration = control.getSwitchPageDuration();
        TranslateTransition currentTransition = new TranslateTransition(switchPageDuration, currentPane);
        TranslateTransition adjacentTransition;

        boolean isScrollingForward = newIndex > control.getCurrentIndex();
        if (isScrollingForward) {
            if (isHorizontal) {
                currentTransition.setToX(-containerSize);
                adjacentTransition = new TranslateTransition(switchPageDuration, nextPane);
                adjacentTransition.setToX(0);
            } else {
                currentTransition.setToY(-containerSize);
                adjacentTransition = new TranslateTransition(switchPageDuration, nextPane);
                adjacentTransition.setToY(0);
            }
        } else {
            if (isHorizontal) {
                currentTransition.setToX(containerSize);
                adjacentTransition = new TranslateTransition(switchPageDuration, previousPane);
                adjacentTransition.setToX(0);
            } else {
                currentTransition.setToY(containerSize);
                adjacentTransition = new TranslateTransition(switchPageDuration, previousPane);
                adjacentTransition.setToY(0);
            }
        }

        ParallelTransition parallelTransition = new ParallelTransition(currentTransition, adjacentTransition);
        parallelTransition.setInterpolator(control.getSwitchPageInterpolator());
        isPlayingAnimation = true;
        parallelTransition.setOnFinished(event -> {
            control.setCurrentIndex(newIndex);
            isPlayingAnimation = false;
        });
        parallelTransition.play();
    }

    private StackPane createPane(int index) {
        SwipeView control = getSkinnable();
        Callback<Integer, Node> pageFactory = control.getPageFactory();
        if (pageFactory != null && index >= 0 && index < control.getPageCount()) {
            Node content = pageFactory.call(index);
            StackPane pane = new StackPane(content);
            pane.getStyleClass().add("swipe-pane-wrapper");
            return pane;
        }
        return null;
    }

    private void setPosition(StackPane pane, int relativePosition) {
        SwipeView control = getSkinnable();
        if (isSwipeHorizontal()) {
            pane.setTranslateX(relativePosition * control.getWidth());
        } else {
            pane.setTranslateY(relativePosition * control.getHeight());
        }
    }

    private void updatePanePositions(double deltaX, double deltaY) {
        SwipeView control = getSkinnable();
        boolean isHorScroll = isSwipeHorizontal();

        double containerSize = isHorScroll ? control.getWidth() : control.getHeight();
        if (isHorScroll) {
            if (previousPane != null) {
                previousPane.setTranslateX(deltaX - containerSize);
                previousPane.setTranslateY(0);
            }
            if (currentPane != null) {
                currentPane.setTranslateX(deltaX);
                currentPane.setTranslateY(0);
            }
            if (nextPane != null) {
                nextPane.setTranslateX(deltaX + containerSize);
                nextPane.setTranslateY(0);
            }
        } else {
            if (previousPane != null) {
                previousPane.setTranslateX(0);
                previousPane.setTranslateY(deltaY - containerSize);
            }
            if (currentPane != null) {
                currentPane.setTranslateX(0);
                currentPane.setTranslateY(deltaY);
            }
            if (nextPane != null) {
                nextPane.setTranslateX(0);
                nextPane.setTranslateY(deltaY + containerSize);
            }
        }
    }

    private void animateToCurrentIndex() {
        SwipeView control = getSkinnable();
        boolean isHorizontal = isSwipeHorizontal();
        double containerSize = isHorizontal ? control.getWidth() : control.getHeight();

        ParallelTransition parallelTransition = new ParallelTransition();

        Duration revertPageDuration = control.getRevertPageDuration();
        if (currentPane != null) {
            TranslateTransition transition = new TranslateTransition(revertPageDuration, currentPane);
            if (isHorizontal) {
                transition.setToX(0);
            } else {
                transition.setToY(0);
            }
            parallelTransition.getChildren().add(transition);
        }
        if (nextPane != null) {
            TranslateTransition transition = new TranslateTransition(revertPageDuration, nextPane);
            if (isHorizontal) {
                transition.setToX(containerSize);
            } else {
                transition.setToY(containerSize);
            }
            parallelTransition.getChildren().add(transition);
        }
        if (previousPane != null) {
            TranslateTransition transition = new TranslateTransition(revertPageDuration, previousPane);
            if (isHorizontal) {
                transition.setToX(-containerSize);
            } else {
                transition.setToY(-containerSize);
            }
            parallelTransition.getChildren().add(transition);
        }

        parallelTransition.setInterpolator(control.getRevertPageInterpolator());
        isPlayingAnimation = true;
        parallelTransition.setOnFinished(event -> isPlayingAnimation = false);
        parallelTransition.play();
    }

    private boolean isSwipeHorizontal() {
        return getSkinnable().getSwipeOrientation() == Orientation.HORIZONTAL;
    }

    private void updatePaneOnIndexChange(int oldIndex, int newIndex) {
        SwipeView control = getSkinnable();
        int currentIndex = control.getCurrentIndex();

        getChildren().clear();
        boolean isAdjacentPane = Math.abs(newIndex - oldIndex) == 1;
        if (isAdjacentPane) {
            handleAdjacentPane(oldIndex, newIndex, currentIndex, control);
        } else {
            handleNonAdjacentPane(currentIndex, control);
        }
    }

    private void handleNonAdjacentPane(int currentIndex, SwipeView swipeView) {
        currentPane = createPane(currentIndex);
        if (currentPane != null) {
            getChildren().add(currentPane);
        }

        if (currentIndex < swipeView.getPageCount() - 1) {
            nextPane = createPane(currentIndex + 1);
            if (nextPane != null) {
                setPosition(nextPane, 1);
                getChildren().add(nextPane);
            }
        } else {
            nextPane = null;
        }

        if (currentIndex > 0) {
            previousPane = createPane(currentIndex - 1);
            if (previousPane != null) {
                setPosition(previousPane, -1);
                getChildren().add(previousPane);
            }
        } else {
            previousPane = null;
        }
    }

    private void handleAdjacentPane(int oldIndex, int newIndex, int currentIndex, SwipeView swipeView) {
        if (newIndex > oldIndex) {
            previousPane = currentPane;
            if (previousPane != null) {
                setPosition(previousPane, -1);
                getChildren().add(previousPane);
            }

            currentPane = nextPane;
            if (currentPane != null) {
                setPosition(currentPane, 0);
                getChildren().add(currentPane);
            }

            int nextIndex = currentIndex + 1;
            boolean hasNextPage = nextIndex < swipeView.getPageCount() && nextIndex >= 0;
            nextPane = hasNextPage ? createPane(nextIndex) : null;
            if (nextPane != null) {
                setPosition(nextPane, 1);
                getChildren().add(nextPane);
            }

        } else {
            nextPane = currentPane;
            if (nextPane != null) {
                setPosition(nextPane, 1);
                getChildren().add(nextPane);
            }

            currentPane = previousPane;
            if (currentPane != null) {
                setPosition(currentPane, 0);
                getChildren().add(currentPane);
            }

            int previousIndex = currentIndex - 1;
            boolean hasPreviousPage = previousIndex >= 0 && previousIndex < swipeView.getPageCount();
            previousPane = hasPreviousPage ? createPane(previousIndex) : null;
            if (previousPane != null) {
                setPosition(previousPane, -1);
                getChildren().add(previousPane);
            }
        }
    }

}