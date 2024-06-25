package com.dlsc.jfxcentral2.mobile.componenets;

import com.dlsc.jfxcentral2.mobile.skin.SwipeViewSkin;
import javafx.animation.Interpolator;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The SwipeView class is a custom JavaFX control that allows users to swipe between multiple pages.
 * <p>
 * It extends the Control class and provides properties and methods to customize the swipe behavior and appearance.
 * <p>
 * The SwipeView control uses a page factory to generate the content of each page. The page factory is a Callback that
 * takes an Integer (representing the index of the page) and returns a Node that is displayed as the content of the page.
 * <p>
 * The SwipeView control supports horizontal and vertical swipe orientations. The swipe orientation can be set using
 * the swipeOrientation property.
 * <p>
 * The SwipeView control provides properties to control the behavior of page switching. The switchPageDuration property
 * specifies the duration of the page switch animation. The switchPageInterpolator property specifies the interpolator
 * used for the page switch animation. The revertPageDuration property specifies the duration of the animation to
 * revert back to the current page when a swipe is not enough to switch to the next or previous page.
 * <p>
 * The SwipeView control also provides properties to control the maximum edge swipe ratio and the page switch threshold ratio.
 * The edgeSwipeMaxRatio determines the maximum amount of swiping allowed when there is no previous or next page.
 * The pageSwitchMinRatio determines the ratio of the scene width (or height) that must be swiped to trigger a page switch.
 * If the swipe distance exceeds this ratio, the view will automatically switch to the next or previous page.
 * <p>
 * The SwipeView control provides properties to get and set the current index and the page count. The currentIndexProperty
 * and currentIndex properties are used to get and set the current index. The pageCountProperty and pageCount properties are used
 * to get and set the page count.
 * <p>
 * The SwipeView control also provides methods to get and set the page factory, swipe orientation, edge swipe max ratio,
 * page switch min ratio, switch page duration, switch page interpolator, and revert page duration.
 * <p>
 * The SwipeView control supports CSS styling. The default style class for the SwipeView control is "swipe-view".
 * The switchPageDuration property and revertPageDuration property can also be styled using CSS.
 */
public class SwipeView extends Control {

    private static final String DEFAULT_STYLE_CLASS = "swipe-view";

    /**
     * The default supported swipe orientation.
     */
    private static final Orientation DEFAULT_SWIPE_ORIENTATION = Orientation.HORIZONTAL;

    /**
     * The default duration for switching pages.
     */
    private static final Duration DEFAULT_SWITCH_PAGE_DURATION = Duration.millis(300);

    /**
     * The default duration for reverting to the current page.
     */
    private static final Duration DEFAULT_REVERT_PAGE_DURATION = Duration.millis(100);

    /**
     * The default interpolator for switching pages and reverting to the current page.
     * A custom interpolator that provides a non-linear acceleration curve for animations.
     * This interpolator uses a square root function to create a smooth acceleration effect,
     * resulting in a slower start that gradually speeds up as the animation progresses.
     *
     * <p>The interpolation curve is defined as <code>Math.pow(t, 0.5)</code>, where <code>t</code> is the normalized
     * time of the animation (ranging from 0.0 to 1.0). This curve produces a gentle acceleration that
     * can be useful for creating natural-looking transitions in animations.</p>
     */
    private static final Interpolator DEFAULT_INTERPOLATOR = new Interpolator() {
        @Override
        protected double curve(double t) {
            return Math.pow(t, 0.5);
        }
    };

    /**
     * Value for indicating that the page count is indeterminate.
     */
    public static final int INDETERMINATE = Integer.MAX_VALUE;

    /**
     * The maximum ratio of the scene width (or height) that can be swiped when there is no previous or next page.
     * This prevents excessive swiping when at the boundaries of the content.
     */
    private static final double DEFAULT_EDGE_MAX_SWIPE_RATIO = 0.2;

    /**
     * The ratio of the scene width (or height) that must be swiped to trigger a page switch.
     * If the swipe distance exceeds this ratio, the view will automatically switch to the next or previous page.
     */
    private static final double DEFAULT_PAGE_SWITCH_RATIO = 0.15;

    public SwipeView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SwipeViewSkin(this);
    }

    // current index

    private final IntegerProperty currentIndex = new SimpleIntegerProperty(this, "currentIndex", 0) {

        @Override
        protected void invalidated() {
            if (!currentIndex.isBound()) {
                if (getCurrentIndex() < 0) {
                    setCurrentIndex(0);
                } else if (getCurrentIndex() > getPageCount() - 1) {
                    setCurrentIndex(getPageCount() - 1);
                }
            }
        }

        @Override
        public void bind(ObservableValue<? extends Number> rawObservable) {
            throw new UnsupportedOperationException("currentIndex supports only bidirectional binding");
        }
    };

    /**
     * Returns the current index property.
     *
     * @return the current index property
     */
    public final IntegerProperty currentIndexProperty() {
        return currentIndex;
    }

    public final int getCurrentIndex() {
        return currentIndex == null ? 0 : currentIndex.get();
    }

    public final void setCurrentIndex(int index) {
        currentIndexProperty().set(index);
    }

    // page count

    private int oldPageCount = INDETERMINATE;

    private final IntegerProperty pageCount = new SimpleIntegerProperty(this, "pageCount", INDETERMINATE) {
        @Override
        protected void invalidated() {
            if (!pageCount.isBound()) {
                if (getPageCount() < 1) {
                    setPageCount(oldPageCount);
                }
                oldPageCount = getPageCount();
            }
        }
    };


    /**
     * Retrieves the pageCount property of the SwipeView.
     * This property represents the number of pages in the SwipeView.
     *
     * @return The IntegerProperty containing the pageCount value.
     */
    public final IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public final void setPageCount(int value) {
        pageCount.set(value);
    }

    public final int getPageCount() {
        return pageCount.get();
    }

    // page factory

    private final ObjectProperty<Callback<Integer, Node>> pageFactory = new SimpleObjectProperty<>(this, "pageFactory");

    /**
     * Returns the property that represents the page factory of the SwipeView.
     * The page factory is used to generate the Node objects for each page in the SwipeView.
     *
     * @return the page factory property
     */
    public final ObjectProperty<Callback<Integer, Node>> pageFactoryProperty() {
        return pageFactory;
    }

    public final void setPageFactory(Callback<Integer, Node> value) {
        pageFactory.set(value);
    }

    public final Callback<Integer, Node> getPageFactory() {
        return pageFactory.get();
    }

    // swipe orientation

    private final ObjectProperty<Orientation> swipeOrientation = new SimpleObjectProperty<>(this, "swipeOrientation", DEFAULT_SWIPE_ORIENTATION);

    /**
     * Returns the property that represents the swipe orientation of the SwipeView.
     * The swipe orientation determines the direction in which the user can swipe to switch between pages.
     *
     * @return the swipe orientation property
     */
    public final ObjectProperty<Orientation> swipeOrientationProperty() {
        return swipeOrientation;
    }

    public final void setSwipeOrientation(Orientation value) {
        swipeOrientation.set(value);
    }

    public final Orientation getSwipeOrientation() {
        return swipeOrientation.get();
    }

    // edge swipe max ratio

    private DoubleProperty edgeSwipeMaxRatio;

    /**
     * Returns the property that represents the maximum edge swipe ratio of the SwipeView.
     * The edge swipe max ratio determines the maximum amount of swiping allowed when there is no previous or next page.
     *
     * @return the edge swipe max ratio property
     */
    public final DoubleProperty edgeSwipeMaxRatioProperty() {
        if (edgeSwipeMaxRatio == null) {
            edgeSwipeMaxRatio = new SimpleDoubleProperty(this, "edgeSwipeMaxRatio", DEFAULT_EDGE_MAX_SWIPE_RATIO);
        }
        return edgeSwipeMaxRatio;
    }

    public final double getEdgeSwipeMaxRatio() {
        return edgeSwipeMaxRatio == null ? DEFAULT_EDGE_MAX_SWIPE_RATIO : edgeSwipeMaxRatio.get();
    }

    public final void setEdgeSwipeMaxRatio(double value) {
        edgeSwipeMaxRatioProperty().set(value);
    }

    // page switch min ratio

    private DoubleProperty pageSwitchMinRatio;

    /**
     * Returns the property that represents the page switch min ratio of the SwipeView.
     * The page switch min ratio determines the ratio of the scene width (or height) that must be swiped to trigger a page switch.
     *
     * @return the page switch min ratio property
     */
    public final DoubleProperty pageSwitchMinRatioProperty() {
        if (pageSwitchMinRatio == null) {
            pageSwitchMinRatio = new SimpleDoubleProperty(this, "pageSwitchMinRatio", DEFAULT_PAGE_SWITCH_RATIO);
        }
        return pageSwitchMinRatio;
    }

    public final double getPageSwitchMinRatio() {
        return pageSwitchMinRatio == null ? DEFAULT_PAGE_SWITCH_RATIO : pageSwitchMinRatio.get();
    }

    public final void setPageSwitchMinRatio(double value) {
        pageSwitchMinRatioProperty().set(value);
    }

    // switch page duration (support css)

    private ObjectProperty<Duration> switchPageDuration;

    /**
     * Returns the property that represents the duration of the page switch animation.
     *
     * @return the switch page duration property
     */
    public final ObjectProperty<Duration> switchPageDurationProperty() {
        if (switchPageDuration == null) {
            switchPageDuration = new StyleableObjectProperty<>(DEFAULT_SWITCH_PAGE_DURATION) {
                @Override
                public Object getBean() {
                    return SwipeView.this;
                }

                @Override
                public String getName() {
                    return "switchPageDuration";
                }

                @Override
                public CssMetaData<? extends Styleable, Duration> getCssMetaData() {
                    return StyleableProperties.SWITCH_PAGE_DURATION;
                }
            };
        }
        return switchPageDuration;
    }

    public final Duration getSwitchPageDuration() {
        return switchPageDuration == null ? DEFAULT_SWITCH_PAGE_DURATION : switchPageDuration.get();
    }

    public final void setSwitchPageDuration(Duration value) {
        switchPageDurationProperty().set(value);
    }

    // switch page interpolator.

    private ObjectProperty<Interpolator> switchPageInterpolator;

    /**
     * Returns the property that represents the interpolator used for the page switch animation.
     *
     * @return the switch page interpolator property
     */
    public final ObjectProperty<Interpolator> switchPageInterpolatorProperty() {
        if (switchPageInterpolator == null) {
            switchPageInterpolator = new SimpleObjectProperty<>(this, "switchPageInterpolator", DEFAULT_INTERPOLATOR);
        }
        return switchPageInterpolator;
    }

    public final Interpolator getSwitchPageInterpolator() {
        return switchPageInterpolator == null ? DEFAULT_INTERPOLATOR : switchPageInterpolator.get();
    }

    public final void setSwitchPageInterpolator(Interpolator value) {
        switchPageInterpolatorProperty().set(value);
    }

    // revert current page duration (support css)

    private ObjectProperty<Duration> revertPageDuration;

    /**
     * Returns the property that represents the duration of the animation to revert back to the current page when a swipe is not enough to switch to the next or previous page.
     *
     * @return the revert page duration property
     */
    public final ObjectProperty<Duration> revertPageDurationProperty() {
        if (revertPageDuration == null) {
            revertPageDuration = new StyleableObjectProperty<>(DEFAULT_REVERT_PAGE_DURATION) {
                @Override
                public Object getBean() {
                    return SwipeView.this;
                }

                @Override
                public String getName() {
                    return "revertPageDurationProperty";
                }

                @Override
                public CssMetaData<? extends Styleable, Duration> getCssMetaData() {
                    return StyleableProperties.REVERT_PAGE_DURATION;
                }
            };
        }
        return revertPageDuration;
    }

    public final Duration getRevertPageDuration() {
        return revertPageDuration == null ? DEFAULT_REVERT_PAGE_DURATION : revertPageDuration.get();
    }

    public final void setRevertPageDuration(Duration value) {
        revertPageDurationProperty().set(value);
    }

    // revert page interpolator.

    private ObjectProperty<Interpolator> revertPageInterpolator;

    /**
     * Returns the property that represents the interpolator used for the animation to revert back to the current page when a swipe is not enough to switch to the next or previous page.
     *
     * @return the revert page interpolator property
     */
    public final ObjectProperty<Interpolator> revertPageInterpolatorProperty() {
        if (revertPageInterpolator == null) {
            revertPageInterpolator = new SimpleObjectProperty<>(this, "revertPageInterpolator", DEFAULT_INTERPOLATOR);
        }
        return revertPageInterpolator;
    }

    public final Interpolator getRevertPageInterpolator() {
        return revertPageInterpolator == null ? DEFAULT_INTERPOLATOR : revertPageInterpolator.get();
    }

    public final void setRevertPageInterpolator(Interpolator value) {
        revertPageInterpolatorProperty().set(value);
    }

    private static class StyleableProperties {
        private static final CssMetaData<SwipeView, Duration> SWITCH_PAGE_DURATION = new CssMetaData<>("-fx-switch-page-duration", StyleConverter.getDurationConverter(), DEFAULT_SWITCH_PAGE_DURATION) {
            @Override
            public boolean isSettable(SwipeView node) {
                return node.switchPageDuration == null || !node.switchPageDuration.isBound();
            }

            @Override
            public StyleableProperty<Duration> getStyleableProperty(SwipeView node) {
                return (StyleableProperty<Duration>) node.switchPageDurationProperty();
            }
        };

        private static final CssMetaData<SwipeView, Duration> REVERT_PAGE_DURATION = new CssMetaData<>("-fx-revert-page-duration", StyleConverter.getDurationConverter(), DEFAULT_REVERT_PAGE_DURATION) {
            @Override
            public boolean isSettable(SwipeView node) {
                return node.revertPageDuration == null || !node.revertPageDuration.isBound();
            }

            @Override
            public StyleableProperty<Duration> getStyleableProperty(SwipeView node) {
                return (StyleableProperty<Duration>) node.revertPageDurationProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables, SWITCH_PAGE_DURATION, REVERT_PAGE_DURATION);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return SwipeView.StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

}
