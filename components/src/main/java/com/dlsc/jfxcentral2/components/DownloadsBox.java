package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class DownloadsBox extends PaneBase {

    private final CustomImageView imageView;
    private final Label nameLabel;
    private final VBox contentBox;

    public DownloadsBox() {
        getStyleClass().addAll("downloads-box");

        imageView = new CustomImageView();
        imageView.managedProperty().bind(imageView.visibleProperty());
        imageView.visibleProperty().bind(imageView.imageProperty().isNotNull());

        nameLabel = new Label();
        nameLabel.setWrapText(true);
        nameLabel.getStyleClass().add("name");

        contentBox = new VBox(imageView, nameLabel);
        contentBox.getStyleClass().add("content-box");

        setMaxHeight(Region.USE_PREF_SIZE);
        getChildren().setAll(contentBox);

        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        refreshInfo();
        downloadProperty().addListener(it -> refreshInfo());
    }

    private void refreshInfo() {
        Download newDownload = getDownload();
        contentBox.getChildren().removeIf(node -> node != imageView && node != nameLabel);

        if (imageView.imageProperty().isBound()) {
            imageView.imageProperty().unbind();
        }

        if (newDownload == null) {
            imageView.setImage(null);
            nameLabel.setText("");
        } else {
            //imageView.imageProperty().bind(ImageManager.getInstance().downloadBannerImageProperty(download));

            //add test image
            imageView.setImage(new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/download-thumbnail-01.png").toExternalForm()));

            nameLabel.setText(newDownload.getName());
            List<Download.DownloadFile> downloadFiles = newDownload.getFiles();
            if (downloadFiles != null && !downloadFiles.isEmpty()) {
                downloadFiles.forEach(downloadFile -> {
                    Download.FileType fileType = downloadFile.getFileType();
                    String downloadInfo = switch (fileType) {
                        case DMG -> "DMG for Mac";
                        case PKG -> "PKG for Mac";
                        case MSI -> "MSI for Windows";
                        case EXE -> "EXE for Windows";
                        case RPM -> "RPM for Linux";
                        case DEB -> "DEB for Linux";
                        default -> "Download " + fileType.name().toUpperCase() + " File";
                    };

                    Button downloadButton = new Button(downloadInfo, new FontIcon(IkonUtil.download));
                    downloadButton.getStyleClass().add("download-button");
                    downloadButton.setOnAction(event -> {
                        System.out.println("Download: " + downloadFile.getUrl());
                    });

                    contentBox.getChildren().add(downloadButton);
                });
            }
        }
    }

    private final ObjectProperty<Download> download = new SimpleObjectProperty<>(this, "download");

    public Download getDownload() {
        return download.get();
    }

    public ObjectProperty<Download> downloadProperty() {
        return download;
    }

    public void setDownload(Download download) {
        this.download.set(download);
    }
}
