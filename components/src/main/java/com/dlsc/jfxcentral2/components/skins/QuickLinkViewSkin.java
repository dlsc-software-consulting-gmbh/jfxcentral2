package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.QuickLinkView;
import com.dlsc.jfxcentral2.components.Spacer;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class QuickLinkViewSkin extends ControlBaseSkin<QuickLinkView> {

    public QuickLinkViewSkin(QuickLinkView control) {
        super(control);
        control.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isPrimaryButtonDown()) {
                if (control.getQuickLink() != null && control.getQuickLink().url() != null) {
                    System.out.println(control.getQuickLink().url());
                }
            }
        });
        layoutBySize();
    }

    public void layoutBySize() {
        QuickLinkView.QuickLink quickLink = getSkinnable().getQuickLink();

        FontIcon icon = new FontIcon(quickLink.ikon());
        ;
        icon.getStyleClass().add("icon");

        FontIcon linkIcon = new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT);
        linkIcon.getStyleClass().add("link-icon");

        HBox topBox = new HBox(icon, new Spacer(), linkIcon);
        topBox.getStyleClass().add("top-box");

        Label titleLabel = new Label(quickLink.title());
        titleLabel.getStyleClass().add("title-label");

        Label descriptionLabel = new Label(quickLink.description());
        descriptionLabel.getStyleClass().add("description-label");
        descriptionLabel.setWrapText(true);

        VBox contentBox = new VBox(topBox, new Spacer(Orientation.VERTICAL), titleLabel, descriptionLabel);

        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }
}
