package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.runestroicons.Runestroicons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DownloadsBox extends PaneBase {

    private enum OS {
        WINDOWS("windows"),
        MACOS("mac"),
        LINUX("linux"),
        GENERIC("generic");

        private final String name;

        OS(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public DownloadsBox(Download download) {
        getStyleClass().addAll("downloads-box");

        List<Download.DownloadFile> downloadFiles = download.getFiles();
        List<Button> downloadButtons = new ArrayList<>();
        if (downloadFiles != null && !downloadFiles.isEmpty()) {
            for (Download.DownloadFile downloadFile : downloadFiles) {
                Button downloadButton = new Button();
                downloadButton.getStyleClass().add("download-button");
                String downloadName = downloadFile.getName();
                Ikon downloadIcon;
                if (StringUtils.containsIgnoreCase(downloadName, OS.WINDOWS.getName())) {
                    downloadButton.getStyleClass().add("download-button-windows");
                    downloadIcon = AntDesignIconsFilled.WINDOWS;
                    downloadButton.setUserData(OS.WINDOWS);
                } else if (StringUtils.containsIgnoreCase(downloadName, OS.MACOS.getName())) {
                    downloadButton.getStyleClass().add("download-button-mac");
                    downloadIcon = AntDesignIconsFilled.APPLE;
                    downloadButton.setUserData(OS.MACOS);
                } else if (StringUtils.containsIgnoreCase(downloadName, OS.LINUX.getName())) {
                    downloadButton.getStyleClass().add("download-button-linux");
                    downloadIcon = Runestroicons.LINUX;
                    downloadButton.setUserData(OS.LINUX);
                } else {
                    downloadButton.getStyleClass().add("download-button-generic");
                    downloadIcon = IkonUtil.download;
                    downloadButton.setUserData(OS.GENERIC);
                }
                downloadButton.setText(downloadName);
                downloadButton.setGraphic(new FontIcon(downloadIcon));
                downloadButton.setMaxWidth(Double.MAX_VALUE);
                downloadButton.setWrapText(true);
                LinkUtil.setExternalLink(downloadButton, downloadFile.getUrl());
                downloadButtons.add(downloadButton);
            }
        }

        Map<OS, List<Button>> groupedButtons = downloadButtons.stream()
                .collect(Collectors.groupingBy(button -> (OS) button.getUserData()));

        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");

        if (groupedButtons.containsKey(OS.WINDOWS)) {
            createDownloadPane(groupedButtons.get(OS.WINDOWS), mainContainer, OS.WINDOWS);
        }

        if (groupedButtons.containsKey(OS.MACOS)) {
            createDownloadPane(groupedButtons.get(OS.MACOS), mainContainer, OS.MACOS);
        }

        if (groupedButtons.containsKey(OS.LINUX)) {
            createDownloadPane(groupedButtons.get(OS.LINUX), mainContainer, OS.LINUX);
        }

        if (groupedButtons.containsKey(OS.GENERIC)) {
            createDownloadPane(groupedButtons.get(OS.GENERIC), mainContainer, OS.GENERIC);
        }

        Header header = new Header();
        header.setTitle("DOWNLOADS");
        header.setIcon(IkonUtil.download);

        Label nameLabel = new Label(download.getName());
        nameLabel.getStyleClass().add("download-name");

        HBox tagsBox = new HBox();
        tagsBox.getStyleClass().add("tags-box");
        String tags = download.getTags();
        if (StringUtils.isNotBlank(tags)) {
            Arrays.stream(tags.trim().split(",")).limit(6).forEach(tag -> {
                Label tagLabel = new Label(tag.trim());
                tagLabel.getStyleClass().addAll("download-tag", "badge");
                tagsBox.getChildren().add(tagLabel);
            });
        }
        tagsBox.managedProperty().bind(tagsBox.visibleProperty());
        tagsBox.visibleProperty().bind(sizeProperty().map(size-> !isSmall()));

        Region dividingLine = new Region();
        dividingLine.getStyleClass().add("dividing-line");

        HBox topBox = new HBox(nameLabel, new Spacer(), tagsBox);
        topBox.getStyleClass().add("top-box");

        VBox centerBox = new VBox(topBox, mainContainer);
        centerBox.getStyleClass().add("center-box");

        VBox contentBox = new VBox(header, topBox,mainContainer);
        contentBox.getStyleClass().add("content-box");

        getChildren().setAll(contentBox);
    }

    private void createDownloadPane(List<Button> buttons, VBox mainContainer, OS os) {
        Label titleLabel = new Label(os.getName(), new FontIcon());
        titleLabel.getStyleClass().addAll("os-label", os.getName() + "-os-label");

        Region dividingLine = new Region();
        dividingLine.getStyleClass().add("dividing-line");
        HBox.setHgrow(dividingLine, Priority.ALWAYS);
        HBox titleWrapper = new HBox(titleLabel, dividingLine);
        titleWrapper.getStyleClass().add("title-wrapper");

        TilePane tilePane = new TilePane();
        tilePane.getStyleClass().add("tile-pane");
        tilePane.getChildren().addAll(buttons);

        mainContainer.getChildren().addAll(titleWrapper, tilePane);
    }
}
