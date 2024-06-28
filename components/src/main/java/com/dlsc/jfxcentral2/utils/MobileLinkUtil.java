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
        if (node instanceof Button button) {
            button.setOnAction(e -> getToPage(link));
        } else if (node instanceof CustomToggleButton button) {
            button.setOnAction(e -> getToPage(link));
        } else if (node instanceof ToggleButton button) {
            button.setOnAction(e -> getToPage(link));
        } else if (node instanceof Hyperlink hyperlink) {
            hyperlink.setOnAction(e -> getToPage(link));
        } else {
            node.setOnMousePressed(e -> MobileLinkUtil.getToPage(link));
        }
    }

}
