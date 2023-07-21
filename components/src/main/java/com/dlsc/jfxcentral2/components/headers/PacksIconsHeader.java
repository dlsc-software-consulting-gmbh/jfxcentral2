package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

public class PacksIconsHeader extends CategoryHeader  {

    public PacksIconsHeader() {
        super();

        getStyleClass().addAll("packs-icons-header", "dark-header");

        FontIcon fontIcon = new FontIcon();
        fontIcon.iconCodeProperty().bind(ikonProperty());

        Label label = new Label();
        label.textProperty().bind(titleProperty());
        label.setGraphic(fontIcon);
        label.getStyleClass().add("header-title");

        setContent(label);
        setTitle("Icons");
        setIkon(IkonUtil.icons);
    }
}
