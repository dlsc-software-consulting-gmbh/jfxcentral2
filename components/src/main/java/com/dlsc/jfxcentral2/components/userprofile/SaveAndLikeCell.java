package com.dlsc.jfxcentral2.components.userprofile;

import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.SaveAndLikeModel;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SaveAndLikeCell extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(SaveAndLikeCell.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public SaveAndLikeCell() {
        getStyleClass().add("save-and-like-cell");

        FontIcon fontIcon = new FontIcon();
        fontIcon.getStyleClass().add("model-icon");
        fontIcon.iconCodeProperty().bind(Bindings.createObjectBinding(() -> {
            SaveAndLikeModel item = getItem();
            if (item != null) {
                return IkonUtil.getModelIkon(item.getTypeClazz());
            }
            return null;
        }, itemProperty()));

        Label title = new Label();
        title.getStyleClass().add("title");
        title.setWrapText(true);
        VBox.setVgrow(title, Priority.ALWAYS);
        title.textProperty().bind(Bindings.createStringBinding(() -> {
            SaveAndLikeModel item = getItem();
            if (item != null) {
                return item.getTitle();
            }
            return null;
        }, itemProperty()));

        Label savedDateLabel = new Label();
        savedDateLabel.managedProperty().bind(savedDateLabel.visibleProperty());
        savedDateLabel.visibleProperty().bind(savedDateLabel.textProperty().isNotEmpty());

        Label likedDateLabel = new Label();
        likedDateLabel.managedProperty().bind(likedDateLabel.visibleProperty());
        likedDateLabel.visibleProperty().bind(likedDateLabel.textProperty().isNotEmpty());

        HBox dateBox = new HBox(savedDateLabel, likedDateLabel);
        dateBox.getStyleClass().add("date-box");


        SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, nv) -> {
            SaveAndLikeModel item = getItem();
            if (item != null) {
                boolean selected = saveAndLikeButton.getSaveButtonSelected();
                item.setSaved(selected);
                item.setSavedDate(selected ? LocalDate.now() : null);
                refreshDateLabel(item.isSaved(), item.getSavedDate(), savedDateLabel, "Saved on ");
                LOGGER.info("{}: {}", item.getTitle(), selected ? "Saved" : "Unsaved");
            }
        });

        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, nv) -> {
            SaveAndLikeModel item = getItem();
            if (item != null) {
                boolean selected = saveAndLikeButton.getLikeButtonSelected();
                item.setLiked(selected);
                item.setLikedDate(selected ? LocalDate.now() : null);
                refreshDateLabel(item.isLiked(), item.getLikedDate(), likedDateLabel, "Liked on ");
                LOGGER.info("{}: {}", item.getTitle(), selected ? "Liked" : "Unliked");
            }
        });

        HBox topBox = new HBox(title, new Spacer(), saveAndLikeButton);
        topBox.getStyleClass().add("top-box");

        VBox centerBox = new VBox(topBox, dateBox);
        centerBox.getStyleClass().add("center-box");

        HBox cellContent = new HBox();
        cellContent.getStyleClass().add("cell-content");
        cellContent.getChildren().addAll(fontIcon, centerBox);
        HBox.setHgrow(centerBox, Priority.ALWAYS);
        getChildren().setAll(cellContent);


        itemProperty().addListener(it -> {
            SaveAndLikeModel item = getItem();
            if (item != null) {
                saveAndLikeButton.setSaveButtonSelected(item.isSaved());
                saveAndLikeButton.setLikeButtonSelected(item.isLiked());
                refreshDateLabel(item.isLiked(), item.getLikedDate(), likedDateLabel, "Liked on ");
                refreshDateLabel(item.isSaved(), item.getSavedDate(), savedDateLabel, "Saved on ");
            } else {
                saveAndLikeButton.setSaveButtonSelected(false);
                saveAndLikeButton.setLikeButtonSelected(false);
                likedDateLabel.setText("");
                savedDateLabel.setText("");
            }
        });
    }

    private void refreshDateLabel(boolean selected, LocalDate date, Label dateLabel, String prefix) {
        if (selected) {
            date = date == null ? LocalDate.EPOCH : date;
            dateLabel.setText(prefix + date.format(DATE_FORMATTER));
        }else {
            dateLabel.setText("");
        }
    }

    private final ObjectProperty<SaveAndLikeModel> item = new SimpleObjectProperty<>(this, "item");

    public SaveAndLikeModel getItem() {
        return item.get();
    }

    public ObjectProperty<SaveAndLikeModel> itemProperty() {
        return item;
    }

    public void setItem(SaveAndLikeModel item) {
        this.item.set(item);
    }
}
