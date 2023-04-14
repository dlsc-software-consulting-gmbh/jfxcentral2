package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.FeatureView;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.Feature;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class FeatureViewSkin extends ControlBaseSkin<FeatureView> {
    public FeatureViewSkin(FeatureView control) {
        super(control);
        layoutBySize();
        control.featureProperty().addListener((observable, oldValue, newValue) -> layoutBySize());
        control.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isPrimaryButtonDown() && control.getFeature() != null && control.getFeature().url() != null) {
                System.out.println("Opening " + control.getFeature().url());
            }
            event.consume();
        });
    }

    protected void layoutBySize() {
        Feature feature = getSkinnable().getFeature();

        Label tagLabel = NodeUtil.createLabel("tag");
        tagLabel.setText(feature.tagName());

        FontIcon typeIcon = new FontIcon();
        typeIcon.getStyleClass().add("type-icon");
        typeIcon.setIconCode(feature.type() == Feature.Type.VIDEO ? MaterialDesign.MDI_PLAY : MaterialDesign.MDI_ARROW_TOP_RIGHT);

        Label titleLabel = null;
        if (!isSmall()) {
            titleLabel = NodeUtil.createLabel("title");
            titleLabel.setText(feature.title());
        }

        HBox topBox = new HBox(NodeUtil.createVBox(tagLabel, titleLabel), new Spacer(), typeIcon);
        topBox.getStyleClass().add("top-box");

        CustomImageView imageView = null;
        if (feature.image() != null) {
            imageView = new CustomImageView();
            imageView.setImage(feature.image());
        }

        Label descriptionLabel = NodeUtil.createLabel("description");
        descriptionLabel.setText(feature.description());
        descriptionLabel.setWrapText(true);

        Label remarkLabel = null;
        if (!isSmall() && (feature.remark() != null || feature.remarkIcon() != null)) {
            remarkLabel = NodeUtil.createLabel("remark");
            remarkLabel.setText(feature.remark());
            remarkLabel.setGraphic(new FontIcon(feature.remarkIcon()));
        }

        VBox contentBox = NodeUtil.createVBox(topBox, imageView, descriptionLabel, remarkLabel);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
        if (isLarge()) {
            VBox.setMargin(topBox, new Insets(0, 0, 0, 0));
            NodeUtil.setVBoxMargin(imageView, new Insets(25, 0, 10, 0));
        } else if (isMedium()) {
            VBox.setMargin(topBox, new Insets(0, 0, 10, 0));
            NodeUtil.setVBoxMargin(imageView, new Insets(25, 0, 20, 0));
        } else if (isSmall()) {
            VBox.setMargin(topBox, new Insets(0, 0, 0, 0));
            NodeUtil.setVBoxMargin(imageView, new Insets(0, 0, 0, 0));
        }
        NodeUtil.setVBoxMargin(remarkLabel, new Insets(10, 0, 0, 0));
    }

}
