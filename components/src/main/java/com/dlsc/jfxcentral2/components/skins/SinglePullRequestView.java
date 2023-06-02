package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class SinglePullRequestView extends HBox {

    private final Label titleLabel = new Label();
    private final Label summaryLabel = new Label();
    private final Label statusLabel = new Label();
    private final HBox labelBox = new HBox();
    private final AvatarView photoView = new AvatarView();
    private final DateTimeFormatter dateTimeFormatter;
    private final VBox vBox = new VBox();

    public SinglePullRequestView(PullRequest pr) {
        getStyleClass().add("single-pull-request-view");

        dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault());

        setPrefWidth(0);

        photoView.visibleProperty().bind(photoView.imageProperty().isNotNull());
        photoView.managedProperty().bind(photoView.imageProperty().isNotNull());

        titleLabel.getStyleClass().add("title-label");
        titleLabel.setContentDisplay(ContentDisplay.RIGHT);

        summaryLabel.getStyleClass().add("summary-label");
        statusLabel.getStyleClass().add("status-label");
        labelBox.getStyleClass().add("label-box");

        vBox.getStyleClass().add("vbox");
        HBox.setHgrow(vBox, Priority.ALWAYS);

        HBox hBox = new HBox();
        hBox.getStyleClass().add("hbox");

        labelBox.getChildren().clear();

        titleLabel.setText(pr.getTitle());
        statusLabel.getStyleClass().removeAll("open", "closed");
        photoView.imageProperty().bind(ImageManager.getInstance().githubAvatarImageProperty(pr.getUser().getLogin()));

        switch (pr.getState()) {
            case "open" -> {
                statusLabel.setText("Open");
                statusLabel.setVisible(false);
                statusLabel.setManaged(false);
                summaryLabel.setText("#" + pr.getNumber() + " opened by " + pr.getUser().getLogin() + " " + createTimeString(pr.getCreatedAt()));
                statusLabel.getStyleClass().add("open");
            }
            case "closed" -> {
                statusLabel.setText("Closed");
                statusLabel.setVisible(true);
                statusLabel.setManaged(true);
                summaryLabel.setText("#" + pr.getNumber() + " closed by " + pr.getUser().getLogin() + " " + createTimeString(pr.getUpdatedAt()));
                statusLabel.getStyleClass().add("closed");
            }
        }

        pr.getLabels().forEach(githubLabel -> {
            Label label = new Label(githubLabel.getName());
            label.getStyleClass().add("pull-request-label");
            labelBox.getChildren().add(label);
        });

        sizeProperty().addListener(it -> updateView());
        updateView();

        LinkUtil.setExternalLink(this, pr.getHtmlUrl());
    }

    private void updateView() {
        if (getSize().equals(Size.SMALL)) {
            titleLabel.setWrapText(true);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);
            summaryLabel.setWrapText(true);
            summaryLabel.setMinHeight(Region.USE_PREF_SIZE);
            vBox.getChildren().setAll(statusLabel, titleLabel, summaryLabel, labelBox);
            getChildren().setAll(photoView, vBox);
            setAlignment(Pos.TOP_LEFT);
        } else {
            titleLabel.setGraphic(labelBox);
            vBox.getChildren().setAll(titleLabel, summaryLabel);
            getChildren().setAll(photoView, vBox, statusLabel);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(this, "size", Size.LARGE);

    public Size getSize() {
        return size.get();
    }

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public void setSize(Size size) {
        this.size.set(size);
    }

    private String createTimeString(String timeString) {
        ZonedDateTime time = DateTimeFormatter.ISO_DATE_TIME.parse(timeString, ZonedDateTime::from);

        Duration between = Duration.between(time, ZonedDateTime.now());

        if (between.toDays() < 1) {
            return between.toHours() + " hours ago.";
        } else if (between.toDays() == 1) {
            return "one day ago.";
        } else if (between.toDays() < 14) {
            return between.toDays() + " days ago.";
        }

        return "on " + dateTimeFormatter.format(time) + ".";
    }
}
