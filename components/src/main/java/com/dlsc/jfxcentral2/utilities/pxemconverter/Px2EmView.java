package com.dlsc.jfxcentral2.utilities.pxemconverter;

import com.dlsc.jfxcentral2.components.DoubleTextField;
import com.dlsc.jfxcentral2.components.FileHandlerView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.utils.FileUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.NumberUtil;
import com.dlsc.jfxcentral2.utils.WebAPIUtil;
import com.google.gson.Gson;
import com.jpro.webapi.WebAPI;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.ListSelectionView;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.hawcons.HawconsStroke;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Objects;

public class Px2EmView extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(Px2EmView.class);
    private static final String CACHE_PATH = ".jfxcentral/csscache";
    private static final String TEMP_DIR_PREFIX = "tempDir_";
    private static final String TEMP_DIR_SUFFIX = "_css";
    private static final double MAX_FONT_SIZE = 200;
    private static final double MIN_FONT_SIZE = 5;
    private static final double DEFAULT_FONT_SIZE = 12;
    private File initDirectory;
    private File cssFile;

    public Px2EmView() {
        getStyleClass().add("px-2-em-view");

        Label label = new Label("Base Font Size", new FontIcon(IkonUtil.font));

        DoubleTextField fontSizeField = new DoubleTextField();
        fontSizeField.setText(String.valueOf(DEFAULT_FONT_SIZE));
        fontSizeField.setPromptText("Input Default Font Size (px)");
        fontSizeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                double value = NumberUtil.clamp(fontSizeField.getValue(), MIN_FONT_SIZE, MAX_FONT_SIZE);
                fontSizeField.setText(NumberUtil.trimTrailingZeros(value, 2));
            }
        });

        Label convertTipsLabel = new Label("px = 1em");
        convertTipsLabel.getStyleClass().add("convert-tips-label");

        Label fontSizeTips = new Label("Please Enter base px for em conversion.");
        fontSizeTips.getStyleClass().add("size-tips-label");
        fontSizeTips.setGraphic(new FontIcon(IkonUtil.tip));

        FlowPane fontSizePane = new FlowPane(label, fontSizeField, convertTipsLabel, fontSizeTips);
        fontSizePane.getStyleClass().add("font-size-pane");

        ListSelectionView<String> view = new ListSelectionView<>();
        view.getSourceItems().addAll(loadRules().convertibleProps());
        Label convertibleLabel = new Label("Convertible Props", new FontIcon(BootstrapIcons.CHECK_CIRCLE));
        view.setSourceHeader(convertibleLabel);

        Label ignoredProps = new Label("Ignored Props", new FontIcon(Material.DO_NOT_DISTURB));
        view.setTargetHeader(ignoredProps);
        view.getTargetItems().addAll(loadRules().ignoredProps());

        FileHandlerView cssFileHandlerView = new FileHandlerView();
        cssFileHandlerView.setText("Click or drop CSS file here.");
        cssFileHandlerView.getSupportedExtensions().add(".css");

        Label fileNameLabel = new Label("File Name:", new FontIcon(HawconsStroke.DOCUMENT_FILE_CSS));
        fileNameLabel.getStyleClass().add("name-label");

        Label fileNameValue = new Label();
        fileNameValue.getStyleClass().add("name-value-label");
        cssFileHandlerView.setOnUploadedFile(file -> {
            fileNameValue.setText(file.getName());
            cssFile = file;
        });

        HBox fileNameBox = new HBox(fileNameLabel, fileNameValue);
        fileNameBox.getStyleClass().add("file-name-box");
        fileNameBox.managedProperty().bind(fileNameBox.visibleProperty());
        fileNameBox.visibleProperty().bind(fileNameValue.textProperty().isNotEmpty());

        CheckBox checkBox = new CheckBox("Preserve original values as comments.");
        checkBox.setSelected(true);

        Label egLabel = new Label("e.g. -fx-font-size: 2em; /* 24px */");
        egLabel.getStyleClass().add("eg-label");

        FlowPane flowPane = new FlowPane(checkBox, egLabel);
        flowPane.getStyleClass().add("flow-pane");

        Button convertButton = new Button("Convert", new FontIcon(CarbonIcons.CD_CREATE_EXCHANGE));
        convertButton.getStyleClass().addAll("fill-button", "convert-button");
        convertButton.disableProperty().bind(fileNameBox.visibleProperty().not());
        convertButton.setMaxWidth(Double.MAX_VALUE);

        convertButton.setOnAction(event -> {
            double fontSize = NumberUtil.clamp(fontSizeField.getValue(), MIN_FONT_SIZE, MAX_FONT_SIZE);
            if (WebAPI.isBrowser()) {
                webDownloadFile(cssFile, fontSize);
            } else {
                desktopDownloadFile(cssFile, fontSize);
            }
        });

        VBox container = new VBox(fontSizePane, view, cssFileHandlerView, fileNameBox, flowPane, convertButton);
        container.getStyleClass().add("container");
        getChildren().setAll(container);
    }

    private void desktopDownloadFile(File cssFile, double baseFontSize) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(FileUtil.getFileNameWithoutExtension(cssFile) + "_converted.css");
        if (initDirectory != null) {
            fileChooser.setInitialDirectory(initDirectory);
        } else {
            fileChooser.setInitialDirectory(cssFile.getParentFile());
        }
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSS Files", "*.css"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File targetFile = fileChooser.showSaveDialog(getScene().getWindow());
        if (targetFile != null) {
            initDirectory = targetFile.getParentFile();
            try {
                Px2EmConverter.convert(cssFile, targetFile, loadRules().convertibleProps(), baseFontSize, true);
            } catch (IOException e) {
                LOGGER.error("Failed to convert px to em for file {}", cssFile.getAbsolutePath(), e);
            }
        }
    }

    private void webDownloadFile(File cssFile, double baseFontSize) {
        File cacheDir = getCacheDirectory();
        File tempFile = createCssTempFile(cacheDir, cssFile, baseFontSize);

        try {
            WebAPIUtil.getWebAPI(this).downloadURL(tempFile.toURI().toURL(), () -> FileUtil.clearDirectory(cacheDir));
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to convert css file to URL: {}", tempFile, e);
            throw new RuntimeException(e);
        }
    }

    private File getCacheDirectory() {
        File cacheDir = new File(System.getProperty("user.home"), CACHE_PATH);
        if (!cacheDir.exists() && !cacheDir.mkdir()) {
            LOGGER.error("Failed to create cache directory: {}", cacheDir);
            throw new RuntimeException("Failed to create css cache directory: " + cacheDir);
        }
        return cacheDir;
    }

    private File createCssTempFile(File cacheDir, File cssFile, double baseFontSize) {
        File tempDir = createTempDirectory(cacheDir);
        File tempFile = new File(tempDir, FileUtil.getFileNameWithoutExtension(cssFile) + "_converted.css");
        try {
            Px2EmConverter.convert(cssFile, tempFile, loadRules().convertibleProps(), baseFontSize, true);
        } catch (IOException e) {
            LOGGER.error("Failed to convert px to em for file {}", cssFile.getAbsolutePath(), e);
        }
        return tempFile;
    }

    private File createTempDirectory(File parentDir) {
        File tempDir = null;
        try {
            tempDir = File.createTempFile(TEMP_DIR_PREFIX, TEMP_DIR_SUFFIX, parentDir);
            if (!tempDir.delete() || !tempDir.mkdir()) {
                LOGGER.error("Failed to create temp directory: {}", tempDir);
                throw new IOException("Failed to create a temporary directory for css.");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create css temp directory: {}", tempDir, e);
            throw new RuntimeException(e);
        }
        return tempDir;
    }

    private Px2EmRules loadRules() {
        String filePath = "/com/dlsc/jfxcentral2/utilities/pxemconverter/css-px2em-rules.json";
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return new Gson().fromJson(bufferedReader, Px2EmRules.class);
        } catch (IOException e) {
            LOGGER.error("Failed to load px2em rules from {}", filePath, e);
            return new Px2EmRules(Collections.emptyList(), Collections.emptyList());
        }
    }

}
