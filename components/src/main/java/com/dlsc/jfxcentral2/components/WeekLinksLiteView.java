package com.dlsc.jfxcentral2.components;

import com.sandec.mdfx.MarkdownView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WeekLinksLiteView extends PaneBase {

    private final VBox contentBox;
    private final MarkdownView markdownView;
    private final Label title;
    private final Label subtitle;
    private final Button viewAllButton;
    private Pane topBox;

    public WeekLinksLiteView() {
        getStyleClass().add("week-links-lite-view");
        title = new Label("Links of the week");
        title.getStyleClass().add("title");

        subtitle = new Label("Miscellaneous JavaFX stuff found on the web");
        subtitle.setWrapText(true);
        subtitle.getStyleClass().add("subtitle");

        viewAllButton = new Button("VIEW ALL LINKS OF THE WEEK");
        viewAllButton.getStyleClass().add("view-all-button");

        markdownView = new MarkdownView();
        markdownView.getStyleClass().add("md-view");
        markdownView.mdStringProperty().bind(mdStringProperty());

        contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
    }

    @Override
    protected void layoutBySize() {
        contentBox.getChildren().clear();
        if (topBox != null) {
            contentBox.getChildren().clear();
        }
        if (isSmall()) {
            topBox = new VBox(title, subtitle, viewAllButton);
            ((VBox)topBox).setAlignment(Pos.CENTER);
        } else {
            VBox titleBox = new VBox(title, subtitle);
            titleBox.getStyleClass().add("title-box");
            titleBox.setAlignment(Pos.CENTER_LEFT);
            topBox = new HBox(titleBox,new Spacer(), viewAllButton);
            ((HBox)topBox).setAlignment(Pos.CENTER);
        }
        topBox.getStyleClass().add("top-box");

        contentBox.getChildren().setAll(topBox, markdownView);
        getChildren().setAll(contentBox);
    }

    private final StringProperty mdString = new SimpleStringProperty(this,"mdString");

    public String getMdString() {
        return mdString.get();
    }

    public StringProperty mdStringProperty() {
        return mdString;
    }

    public void setMdString(String mdString) {
        this.mdString.set(mdString);
    }
}
