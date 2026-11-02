package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileRouter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MobilePageBase extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "mobile-page-base";

    /**
     * Default value indicating whether swipe back is enabled.
     * If enabled, the user can swipe from left to right to go back to the previous view.
     */
    private static final boolean DEFAULT_SWIPE_BACK_ENABLED = true;

    /**
     * Default value indicating whether swipe forward is enabled.
     * If enabled, the user can swipe from right to left to go forward to the next view.
     */
    private static final boolean DEFAULT_SWIPE_FORWARD_ENABLED = true;

    /**
     * Default horizontal threshold for swipe gestures.
     * If the horizontal movement is greater than this value, the swipe gesture is recognized.
     */
    private static final double SWIPE_HOR_THRESHOLD = 60;

    /**
     * Default vertical threshold for swipe gestures.
     * If the vertical movement is greater than this value, the horizontal swipe gesture is ignored.
     */
    private static final double SWIPE_VERTICAL_THRESHOLD = 30;

    private final MobileRouter mobileRouter = MobileRouter.getInstance();
    private final SizeSupport sizeSupport = new SizeSupport(this);

    private double initialX;
    private double initialY;
    private boolean isSwiping;

    public MobilePageBase() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
        isSwiping = true;
    }

    private void handleMouseReleased(MouseEvent event) {
        if (!isSwiping) {
            return;
        }

        double deltaX = event.getSceneX() - initialX;
        double deltaY = event.getSceneY() - initialY;

        // If the vertical movement is greater, ignore the horizontal movement.
        if (Math.abs(deltaY) > SWIPE_VERTICAL_THRESHOLD) {
            isSwiping = false;
            return;
        }

        if (Math.abs(deltaX) > SWIPE_HOR_THRESHOLD) {
            if (deltaX > 0 && isSwipeBackEnabled() && mobileRouter.canGoBackProperty().get()) {
                onSwipeRight();
            } else if (deltaX < 0 && isSwipeForwardEnabled() && mobileRouter.canGoForwardProperty().get()) {
                onSwipeLeft();
            }
        }
        isSwiping = false;
    }
    protected void onSwipeLeft() {
        mobileRouter.goToForward();
    }

    protected void onSwipeRight() {
        mobileRouter.goToBack();
    }

    // size

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    // swipe back enabled

    private BooleanProperty swipeBackEnabled;

    /**
     * A flag that indicates whether swipe back is enabled.
     * If enabled, the user can swipe from the left edge of the screen to go back to the previous view.
     * If disabled, the swipe gesture is ignored.
     * The default value is {@code true}.
     */
    public final BooleanProperty swipeBackEnabledProperty() {
        if (swipeBackEnabled == null) {
            swipeBackEnabled = new SimpleBooleanProperty(this, "swipeBackEnabled", DEFAULT_SWIPE_BACK_ENABLED);
        }
        return swipeBackEnabled;
    }

    public final boolean isSwipeBackEnabled() {
        return swipeBackEnabled == null ? DEFAULT_SWIPE_BACK_ENABLED : swipeBackEnabled.get();
    }

    public final void setSwipeBackEnabled(boolean swipeBackEnabled) {
        swipeBackEnabledProperty().set(swipeBackEnabled);
    }

    // swipe forward enabled

    private BooleanProperty swipeForwardEnabled;

    /**
     * A flag that indicates whether swipe forward is enabled.
     * If enabled, the user can swipe from the right edge of the screen to go forward to the next view.
     * If disabled, the swipe gesture is ignored.
     * The default value is {@code true}.
     */
    public final BooleanProperty swipeForwardEnabledProperty() {
        if (swipeForwardEnabled == null) {
            swipeForwardEnabled = new SimpleBooleanProperty(this, "swipeForwardEnabled", DEFAULT_SWIPE_FORWARD_ENABLED);
        }
        return swipeForwardEnabled;
    }

    public final boolean isSwipeForwardEnabled() {
        return swipeForwardEnabled == null ? DEFAULT_SWIPE_FORWARD_ENABLED : swipeForwardEnabled.get();
    }

    public final void setSwipeForwardEnabled(boolean swipeForwardEnabled) {
        swipeForwardEnabledProperty().set(swipeForwardEnabled);
    }

    // view will appear

    private ObjectProperty<Runnable> viewWillAppear;

    /**
     * A callback that is invoked when the view will appear.
     * This is typically called just before the view is added to the scene.
     */
    public final ObjectProperty<Runnable> viewWillAppearProperty() {
        if (viewWillAppear == null) {
            viewWillAppear = new SimpleObjectProperty<>(this, "viewWillAppear");
        }
        return viewWillAppear;
    }

    public final Runnable getViewWillAppear() {
        return viewWillAppear == null ? null : viewWillAppear.get();
    }

    public final void setViewWillAppear(Runnable viewWillAppear) {
        viewWillAppearProperty().set(viewWillAppear);
    }

    // view did appear

    private ObjectProperty<Runnable> viewDidAppear;

    /**
     * A callback that is invoked when the view appears.
     * This is typically called after the view has been added to the scene.
     */
    public final ObjectProperty<Runnable> viewDidAppearProperty() {
        if (viewDidAppear == null) {
            viewDidAppear = new SimpleObjectProperty<>(this, "viewDidAppear");
        }
        return viewDidAppear;
    }

    public final Runnable getViewDidAppear() {
        return viewDidAppear == null ? null : viewDidAppear.get();
    }

    public final void setViewDidAppear(Runnable viewDidAppear) {
        viewDidAppearProperty().set(viewDidAppear);
    }

    // view will disappear

    private ObjectProperty<Runnable> viewWillDisappear;

    /**
     * A callback that is invoked when the view will disappear.
     * This is typically called just before the view is removed from the scene.
     */
    public final ObjectProperty<Runnable> viewWillDisappearProperty() {
        if (viewWillDisappear == null) {
            viewWillDisappear = new SimpleObjectProperty<>(this, "viewWillDisappear");
        }
        return viewWillDisappear;
    }

    public final Runnable getViewWillDisappear() {
        return viewWillDisappear == null ? null : viewWillDisappear.get();
    }

    public final void setViewWillDisappear(Runnable viewWillDisappear) {
        viewWillDisappearProperty().set(viewWillDisappear);
    }

    // view did disappear

    private ObjectProperty<Runnable> viewDidDisappear;

    /**
     * A callback that is invoked when the view disappears.
     * This is typically called after the view has been removed from the scene.
     */
    public final ObjectProperty<Runnable> viewDidDisappearProperty() {
        if (viewDidDisappear == null) {
            viewDidDisappear = new SimpleObjectProperty<>(this, "viewDidDisappear");
        }
        return viewDidDisappear;
    }

    public final Runnable getViewDidDisappear() {
        return viewDidDisappear == null ? null : viewDidDisappear.get();
    }

    public final void setViewDidDisappear(Runnable viewDidDisappear) {
        viewDidDisappearProperty().set(viewDidDisappear);
    }
}

