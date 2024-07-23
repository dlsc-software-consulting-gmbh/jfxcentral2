package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class LearnCategoryBox extends VBox {

    public LearnCategoryBox() {
        getStyleClass().add("learn-category-box");

        LearnCategoryCell learnJavaFX = new LearnCategoryCell();
        learnJavaFX.getStyleClass().add("learn-javafx-cell");
        learnJavaFX.setTitle("JavaFX");
        learnJavaFX.setIcon(IkonUtil.learnJavaFX);
        learnJavaFX.setDescription("JavaFX tutorials for beginners and advanced developers.");
        MobileLinkUtil.setLink(learnJavaFX, PagePath.LEARN_JAVAFX);

        LearnCategoryCell learnMobile = new LearnCategoryCell();
        learnMobile.getStyleClass().add("learn-mobile-cell");
        learnMobile.setTitle("Mobile");
        learnMobile.setIcon(IkonUtil.learnMobile);
        learnMobile.setDescription("Master the skills to build JavaFX apps for mobile platforms.");
        MobileLinkUtil.setLink(learnMobile, PagePath.LEARN_MOBILE);

        LearnCategoryCell learnRaspberryPi = new LearnCategoryCell();
        learnRaspberryPi.getStyleClass().add("learn-raspberry-pi-cell");
        learnRaspberryPi.setTitle("RaspberryPi");
        learnRaspberryPi.setIcon(IkonUtil.learnRaspberryPi);
        learnRaspberryPi.setDescription("Learn JavaFX development tailored to Raspberry Pi applications.");
        MobileLinkUtil.setLink(learnRaspberryPi, PagePath.LEARN_RASPBERRYPI);

        HBox content = new HBox(learnJavaFX, learnMobile, learnRaspberryPi);
        content.getStyleClass().add("content");

        Label title = new Label("Learn");
        title.getStyleClass().add("title");

        getChildren().addAll(title, content);
    }

    private static class LearnCategoryCell extends VBox {

        public LearnCategoryCell() {
            getStyleClass().add("learn-category-cell");

            Label title = new Label();
            title.getStyleClass().add("title");
            FontIcon fontIcon = new FontIcon();
            title.setGraphic(fontIcon);
            fontIcon.iconCodeProperty().bind(iconProperty());
            title.textProperty().bind(titleProperty());

            Label description = new Label();
            description.getStyleClass().add("description");
            description.textProperty().bind(descriptionProperty());
            description.setWrapText(true);

            getChildren().addAll(title, description);
            setPrefWidth(0);
            HBox.setHgrow(this, Priority.ALWAYS);
        }

        // title

        private final StringProperty title = new SimpleStringProperty(this, "title");

        public final String getTitle() {
            return title.get();
        }

        public final StringProperty titleProperty() {
            return title;
        }

        public final void setTitle(String title) {
            titleProperty().set(title);
        }

        // icon
        private final ObjectProperty<Ikon> icon = new SimpleObjectProperty(this, "icon");

        public final Ikon getIcon() {
            return icon.get();
        }

        public final ObjectProperty<Ikon> iconProperty() {
            return icon;
        }

        public final void setIcon(Ikon icon) {
            iconProperty().set(icon);
        }

        // description

        private final StringProperty description = new SimpleStringProperty(this, "description");

        public final String getDescription() {
            return description.get();
        }

        public final StringProperty descriptionProperty() {
            return description;
        }

        public final void setDescription(String description) {
            descriptionProperty().set(description);
        }

    }

}
