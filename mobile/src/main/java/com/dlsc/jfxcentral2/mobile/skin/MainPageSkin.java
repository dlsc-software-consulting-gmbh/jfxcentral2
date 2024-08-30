package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.gemsfx.GlassPane;
import com.dlsc.jfxcentral2.components.MobilePageBase;
import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import com.dlsc.jfxcentral2.events.RepositoryUpdatedEvent;
import com.dlsc.jfxcentral2.mobile.pages.CategoriesPane;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.mobile.utils.PreferredFocusedNodeProvider;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import com.dlsc.jfxcentral2.utils.Subscribe;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseButton;
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

    public static final double CLOSED_DRAWER_HEIGHT = .28;
    public static final int DRAWER_ANIMATION_DURATION = 150;

    private final StackPane contentPane = new StackPane();
    private final GlassPane glassPane = new GlassPane();
    private final Node drawer;
    private StackPane drawerHandle;

    // value between 0 and 1
    private final DoubleProperty drawerHeight = new SimpleDoubleProperty(CLOSED_DRAWER_HEIGHT);

    private CategoriesPane content;
    private Timeline timeline;
    private double startY = -1;

    public MainPageSkin(MainPage control) {
        super(control);

        EventBusUtil.register(this);

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

        content = new CategoriesPane(this::hideDrawer);
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
//                System.out.println("closing bc of handler");
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

    @Subscribe
    public void onMobileResponseEvent(MobileResponseEvent event) {
        Node oldView = null;

        // Old view will disappear
        if (!contentPane.getChildren().isEmpty()) {
            oldView = contentPane.getChildren().get(0);
            invokeLifecycleMethod(oldView, MobilePageBase::getViewWillDisappear);
        }

        // New view will appear
        Node newView = event.mobileResponse().getView();
        invokeLifecycleMethod(newView, MobilePageBase::getViewWillAppear);

        contentPane.getChildren().setAll(newView);

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

    @Subscribe
    public void onHideMenuBarEvent(RepositoryUpdatedEvent event) {
        drawer.setVisible(event.isUpdated());
    }
}
