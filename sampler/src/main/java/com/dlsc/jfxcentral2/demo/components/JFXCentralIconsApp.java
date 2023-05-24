package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JFXCentralIconsApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TilePane container = new TilePane();
        container.setPrefColumns(4);
        container.setTileAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10));
        container.setHgap(20);
        container.setVgap(20);

        List<JFXCentralIcon> icons = new ArrayList<>();

        Collections.addAll(icons, JFXCentralIcon.values());

        icons.sort(Comparator.comparing(JFXCentralIcon::getCode));

        System.out.println("count: " + icons.size());

        icons.forEach(icon -> {
            Label iconLbl = new Label();
            iconLbl.setGraphic(FontIcon.of(icon, 24));
            iconLbl.setText(icon.getDescription());
            iconLbl.setAlignment(Pos.CENTER);
            iconLbl.setTextAlignment(TextAlignment.CENTER);
            container.getChildren().add(iconLbl);
        });

        primaryStage.setTitle("JFXCentral2 Icons");
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToHeight(true);
        primaryStage.setScene(new Scene(scrollPane));
        primaryStage.setWidth(500);
        primaryStage.setHeight(400);
        primaryStage.show();
    }

    public String byteToHex(byte b) {
        // Returns hex String representation of byte b
        char[] hexDigit = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    public String charToHex(char c) {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }
}
