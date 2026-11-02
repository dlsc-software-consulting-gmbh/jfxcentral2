package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.jfxcentral2.components.GlassPane;
import com.dlsc.jfxcentral2.components.MobilePageBase;
import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import com.dlsc.jfxcentral2.events.RepositoryUpdatedEvent;
import com.dlsc.jfxcentral2.mobile.components.CategorySelectionView;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.mobile.utils.PreferredFocusedNodeProvider;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import com.dlsc.jfxcentral2.utils.skin.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Optional;
import java.util.function.Function;

public class MainPageSkin extends SkinBase<MainPage> {

    public static final double CLOSED_DRAWER_HEIGHT = .26;
    public static final int DRAWER_ANIMATION_DURATION = 150;

    private final StackPane contentPane = new StackPane();
    private final GlassPane glassPane = new GlassPane();
    private final Node drawer;
    private StackPane drawerHandle;

    // value between 0 and 1
    private final DoubleProperty drawerHeight = new SimpleDoubleProperty(CLOSED_DRAWER_HEIGHT);

    private CategorySelectionView content;
    private Timeline timeline;
    private double startY = -1;

    public MainPageSkin(MainPage control) {
        super(control);

        EventBusUtil.subscribe(MobileResponseEvent.class, this, this::onMobileResponseEvent);
        EventBusUtil.subscribe(RepositoryUpdatedEvent.class, this, this::onHideMenuBarEvent);

        drawer = createDrawerContent();

        contentPane.getStyleClass().add("content-pane");

        contentPane.setManaged(false);
        glassPane.setManaged(false);
        drawer.setManaged(false);
        drawer.setVisible(false);

        glassPane.setOnMouseClicked(evt -> hideDrawer());
        glassPane.setBlockingOpacity(0);
        glassPane.hideProperty().bind(drawerHeight.lessThanOrEqualTo(CLOSED_DRAWER_HEIGHT));

        getChildren().setAll(contentPane, glassPane, drawer);

        drawerHeight.addListener(it -> getSkinnable().requestLayout());
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        double drawerHeight = 0;
        if (drawer.isVisible()) {
            drawerHeight = this.drawerHeight.get() * content.prefHeight(-1) + drawerHandle.prefHeight(-1);
            drawer.resizeRelocate(contentX, contentY + contentHeight - drawerHeight, contentWidth, drawerHeight);
        }
        contentPane.resizeRelocate(contentX, contentY, contentWidth, contentHeight - drawerHeight);
        glassPane.resizeRelocate(contentX, contentY, contentWidth, contentHeight - drawerHeight);
    }

    private Node createDrawerContent() {
        Region region = new Region();
        region.getStyleClass().add("region");
        region.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        StackPane.setAlignment(region, Pos.CENTER);

        drawerHandle = new StackPane(region);
        drawerHandle.setMaxWidth(Double.MAX_VALUE);
        drawerHandle.setMinHeight(Region.USE_PREF_SIZE);
        drawerHandle.getStyleClass().add("handle");

        content = new CategorySelectionView(this::hideDrawer);
        content.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        content.setAlignment(Pos.CENTER);
        content.setMouseTransparent(false);
        VBox.setVgrow(content, Priority.ALWAYS);

        installCloseDrawerHandler(content);

        VBox drawer = new VBox(drawerHandle, content);
        drawer.getStyleClass().add("drawer");
        drawer.setMaxHeight(Region.USE_PREF_SIZE);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(drawer.widthProperty());
        clip.heightProperty().bind(drawer.heightProperty().add(50));
        clip.setLayoutY(-50);
        drawer.setClip(clip);

        drawer.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> startY = evt.getScreenY());

        drawer.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
            startY = 0;

            if (drawerHeight.get() > CLOSED_DRAWER_HEIGHT) {
                if (drawerHeight.get() < .75) {
                    hideDrawer();
                } else {
                    showDrawer();
                }
            }
        });

        drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, evt -> {
            if (startY != -1) {
                double deltaY = startY - evt.getScreenY();
                double height = drawerHeight.get();
                height += (deltaY / drawer.prefHeight(-1));
                if (height > CLOSED_DRAWER_HEIGHT && height <= 1) {
                    height = Math.max(CLOSED_DRAWER_HEIGHT, Math.min(1, height));
                    drawerHeight.set(height);
                    startY = evt.getScreenY();
                }
            }
        });

//        drawer.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
//            if (evt.isConsumed()) {
//                return;
//            }
//            if (drawerHeight.get() > CLOSED_DRAWER_HEIGHT) {
//                hideDrawer();
//            } else if (evt.getClickCount() == 2) {
//                showDrawer();
//            }
//        });

        drawer.setOnSwipeDown(evt -> hideDrawer());
        drawer.setOnSwipeUp(evt -> showDrawer());

        return drawer;
    }

    private void installCloseDrawerHandler(Node node) {
//        node.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
//            if (evt.getButton().equals(MouseButton.PRIMARY) && evt.isStillSincePress()) {
//                hideDrawer();
//            }
//        });
    }

    private void hideDrawer() {
        animateDrawer(CLOSED_DRAWER_HEIGHT);
    }

    private void showDrawer() {
        animateDrawer(1);
    }

    private void animateDrawer(double value) {
        if (timeline != null && timeline.getStatus().equals(Animation.Status.RUNNING)) {
            timeline.stop();
        }

        KeyValue keyValue = new KeyValue(drawerHeight, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(DRAWER_ANIMATION_DURATION), keyValue);
        timeline = new Timeline(keyFrame);
        timeline.play();
    }

    /// How long a page takes to slide in or out, and to cross fade.
    private static final double PUSH_MILLIS = 320;
    private static final double FADE_MILLIS = 180;
    /// iOS's navigation curve: quick start, long settle.
    private static final Interpolator PUSH_CURVE = Interpolator.SPLINE(0.2, 0.9, 0.3, 1);

    private Timeline pageTransition;
    private Runnable finishTransition;

    /// 1 for a page about one item, 0 for home, the categories and the other
    /// pages the tab bar and the home page lead to. Going one deeper slides
    /// the new page in from the right, coming back slides the old one out,
    /// and a change at the same depth cross fades. The original app swapped
    /// pages without any motion.
    private static int depth(Node view) {
        return view != null && view.getClass().getName().contains(".pages.details.") ? 1 : 0;
    }

    public void onMobileResponseEvent(MobileResponseEvent event) {
        // A new page during a transition: land the running one first.
        if (finishTransition != null) {
            pageTransition.stop();
            finishTransition.run();
        }

        Node oldView = contentPane.getChildren().isEmpty() ? null
                : contentPane.getChildren().get(contentPane.getChildren().size() - 1);
        Node newView = event.mobileResponse().getView();
        if (oldView == newView) {
            // The page already shown: it neither leaves nor arrives.
            return;
        }

        // Old view will disappear
        invokeLifecycleMethod(oldView, MobilePageBase::getViewWillDisappear);

        // New view will appear
        invokeLifecycleMethod(newView, MobilePageBase::getViewWillAppear);

        if (oldView == null) {
            contentPane.getChildren().setAll(newView);
            pageShown(null, newView);
            return;
        }

        int from = depth(oldView);
        int to = depth(newView);
        double width = Math.max(1, contentPane.getWidth());
        Node leaving = oldView;
        newView.setTranslateX(0);
        newView.setOpacity(1);
        Timeline t;
        if (to > from) {
            // Push: the new page over the old one, which drifts left.
            contentPane.getChildren().setAll(oldView, newView);
            newView.setTranslateX(width);
            t = new Timeline(new KeyFrame(Duration.millis(PUSH_MILLIS),
                    new KeyValue(newView.translateXProperty(), 0, PUSH_CURVE),
                    new KeyValue(oldView.translateXProperty(), -width * 0.3, PUSH_CURVE)));
        } else if (to < from) {
            // Pop: the old page slides away to the right, uncovering the new.
            contentPane.getChildren().setAll(newView, oldView);
            newView.setTranslateX(-width * 0.3);
            t = new Timeline(new KeyFrame(Duration.millis(PUSH_MILLIS),
                    new KeyValue(newView.translateXProperty(), 0, PUSH_CURVE),
                    new KeyValue(oldView.translateXProperty(), width, PUSH_CURVE)));
        } else {
            contentPane.getChildren().setAll(oldView, newView);
            newView.setOpacity(0);
            t = new Timeline(new KeyFrame(Duration.millis(FADE_MILLIS),
                    new KeyValue(newView.opacityProperty(), 1, Interpolator.EASE_OUT)));
        }
        Node shown = newView;
        finishTransition = () -> {
            finishTransition = null;
            leaving.setTranslateX(0);
            leaving.setOpacity(1);
            shown.setTranslateX(0);
            shown.setOpacity(1);
            contentPane.getChildren().setAll(shown);
            pageShown(leaving, shown);
        };
        t.setOnFinished(e -> {
            if (finishTransition != null) {
                finishTransition.run();
            }
        });
        pageTransition = t;
        t.play();
    }

    private void pageShown(Node oldView, Node newView) {
        // Old view did disappear
        invokeLifecycleMethod(oldView, MobilePageBase::getViewDidDisappear);

        // New view did appear
        invokeLifecycleMethod(newView, MobilePageBase::getViewDidAppear);

        // focus the preferred node
        if (newView instanceof PreferredFocusedNodeProvider preferredFocusedNodeProvider) {
            Node preferredFocusedNode = preferredFocusedNodeProvider.getPreferredFocusedNode();
            if (preferredFocusedNode != null) {
                preferredFocusedNode.requestFocus();
            }
        }
    }

    private void invokeLifecycleMethod(Node view, Function<MobilePageBase, Runnable> eventFunction) {
        if (view instanceof MobilePageBase mobilePage) {
            Optional.ofNullable(eventFunction.apply(mobilePage)).ifPresent(Runnable::run);
        }
    }

    public void onHideMenuBarEvent(RepositoryUpdatedEvent event) {
        drawer.setVisible(event.isUpdated());
    }
}
