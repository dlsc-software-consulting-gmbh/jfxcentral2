package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class SinglePullRequestView extends PaneBase {

    private final Label titleLabel = new Label();
    private final Label summaryLabel = new Label();
    private final Label statusLabel = new Label();
    private final HBox labelBox = new HBox();
    private final HBox contentBox = new HBox();
    private final AvatarView photoView = new AvatarView();
    private final DateTimeFormatter dateTimeFormatter;
    private final VBox vBox = new VBox();

    public SinglePullRequestView(PullRequest pr) {
        getStyleClass().add("single-pull-request-view");
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
        contentBox.setMinHeight(Region.USE_PREF_SIZE);

        dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault());

        photoView.visibleProperty().bind(photoView.imageProperty().isNotNull());
        photoView.managedProperty().bind(photoView.imageProperty().isNotNull());

        titleLabel.getStyleClass().add("title-label");
        titleLabel.setContentDisplay(ContentDisplay.RIGHT);

        summaryLabel.getStyleClass().add("summary-label");
        statusLabel.getStyleClass().add("status-label");
        labelBox.getStyleClass().add("label-box");

        vBox.getStyleClass().add("vbox");
        HBox.setHgrow(vBox, Priority.ALWAYS);

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
        statusLabel.setMinWidth(Region.USE_PREF_SIZE);

        pr.getLabels().forEach(githubLabel -> {
            Label label = new Label(githubLabel.getName());
            label.getStyleClass().add("pull-request-label");
            labelBox.getChildren().add(label);
        });

        sizeProperty().addListener(it -> layoutBySize());
        layoutBySize();

        ExternalLinkUtil.setExternalLink(this, pr.getHtmlUrl());
    }

    @Override
    protected void layoutBySize() {
        if (isSmall()) {
            titleLabel.setWrapText(true);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);
            titleLabel.setGraphic(null);
            summaryLabel.setWrapText(true);
            summaryLabel.setMinHeight(Region.USE_PREF_SIZE);
            HBox badgeBox = new HBox(statusLabel, labelBox);
            badgeBox.getStyleClass().add("badge-box");
            vBox.getChildren().setAll(titleLabel, badgeBox, summaryLabel);
            contentBox.getChildren().setAll(photoView, vBox);
        } else if (isMedium()) {
            titleLabel.setGraphic(null);
            titleLabel.setWrapText(true);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);
            vBox.getChildren().setAll(titleLabel, summaryLabel);
            HBox badgeBox = new HBox(labelBox, statusLabel);
            labelBox.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getStyleClass().add("badge-box");
            contentBox.getChildren().setAll(photoView, vBox, badgeBox);
        } else {
            titleLabel.setGraphic(labelBox);
            titleLabel.setWrapText(true);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);
            vBox.getChildren().setAll(titleLabel, summaryLabel);
            contentBox.getChildren().setAll(photoView, vBox, statusLabel);
        }
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
