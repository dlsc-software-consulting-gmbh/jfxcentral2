package com.dlsc.jfxcentral2.utils;


import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.events.MobileLinkEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;

public class MobileLinkUtil {

    private static final EventBus EVENT_BUS = EventBusUtil.getEventBus();

    private MobileLinkUtil() {
    }

    public static void getToPage(String link) {
        EVENT_BUS.post(new MobileLinkEvent(link));
    }

    public static void setLink(Node node, String link) {
        setNodeAction(node, () -> getToPage(link));
    }

    public static void setGoToBackLink(Node node) {
        setNodeAction(node, () -> MobileRouter.getInstance().goToBack());
    }

    public static void setGoToForwardLink(Node node) {
        setNodeAction(node, () -> MobileRouter.getInstance().goToForward());
    }

    private static void setNodeAction(Node node, Runnable action) {
        if (node instanceof Button button) {
            button.setOnAction(e -> action.run());
        } else if (node instanceof CustomToggleButton button) {
            button.setOnAction(e -> action.run());
        } else if (node instanceof ToggleButton button) {
            button.setOnAction(e -> action.run());
        } else if (node instanceof Hyperlink hyperlink) {
            hyperlink.setOnAction(e -> action.run());
        } else {
            node.setOnMouseClicked(e -> {
                if (e.isStillSincePress()) {
                    action.run();
                }
            });
        }
    }

}
