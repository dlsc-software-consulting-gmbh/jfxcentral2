package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.SVGPathExtractor;
import com.dlsc.jfxcentral2.utils.WebAPIUtil;
import com.jpro.webapi.WebAPI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import one.jpro.routing.CopyUtil;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonHandler;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.javafx.IkonResolver;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

public class IkonDetailView extends DetailView<Ikon> {
    protected static File initDirectory;
    private static final Logger LOGGER = LogManager.getLogger(IkonDetailView.class);
    private final HBox detailContent = new HBox();
    private final StackPane previewPane = new StackPane();

    private enum SVGType {
        FILL,
        OUTLINE
    }

    private record IkonInfo(String iconLiteral, String cssCode, String javaCode, String unicode, String mavenInfo,
                            String gradleInfo) {
    }

    private final IkonInfo ikonInfo;

    public IkonDetailView(Ikon item) {
        super(item);

        getStyleClass().add("ikon-detail-view");

        if (item.getClass().getSimpleName().equals("PaymentFont")) {
            getStyleClass().add("payment-font-detail-view");
        }

        FontIcon fontIcon = new FontIcon(item);
        ikonInfo = new IkonInfo(item.getDescription(),
                "-fx-icon-code: \"" + item.getDescription() + "\";",
                item.getClass().getSimpleName() + "." + fontIcon.getIconCode(),
                "\\u" + Integer.toHexString(item.getCode()),
                IkonliPackUtil.getInstance().getMavenDependency(item),
                IkonliPackUtil.getInstance().getGradleDependency(item));

        previewPane.getChildren().setAll(fontIcon);
        previewPane.getStyleClass().add("ikon-preview-wrapper");
        HBox.setHgrow(previewPane, Priority.ALWAYS);

        Node infoNode = createInfoNode();
        StackPane.setAlignment(infoNode, Pos.CENTER);
        detailContent.getChildren().setAll(previewPane, infoNode);
        detailContent.getStyleClass().add("detail-content");
        getChildren().setAll(detailContent);
    }

    private FlowPane createInfoNode() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().add("ikon-info-grid-pane");
        HBox.setHgrow(flowPane, Priority.ALWAYS);

        addRow(flowPane, "Icon Literal:", ikonInfo.iconLiteral());
        addRow(flowPane, "CSS Code:", ikonInfo.cssCode());
        addRow(flowPane, "Java Code:", ikonInfo.javaCode());
        addRow(flowPane, "Unicode:", ikonInfo.unicode());
        addRow(flowPane, "Maven:", ikonInfo.mavenInfo());
        addRow(flowPane, "Gradle :", ikonInfo.gradleInfo());

        ComboBox<SVGType> pathComboBox = createSvgTypeComboBox();
        addRow(flowPane, "Path:", IkonUtil.copy, pathComboBox, event -> copyPathEventHandler(event, pathComboBox));

        ComboBox<SVGType> svgComboBox = createSvgTypeComboBox();
        addRow(flowPane, "SVG:", IkonUtil.download, svgComboBox, event -> downloadSVGHandler(svgComboBox));

        return flowPane;
    }

    private void downloadSVGHandler(ComboBox<SVGType> svgComboBox) {
        SVGType type = svgComboBox.getSelectionModel().getSelectedItem();
        SVGGraphics2D svgGenerator = getSvgGraphics2D(type);
        if (WebAPI.isBrowser()) {
            webDownloadFile(svgGenerator);
        } else {
            desktopDownloadFile(svgGenerator);
        }
    }

    private void copyPathEventHandler(ActionEvent event, ComboBox<SVGType> pathComboBox) {
        File tempFile;
        try {
            tempFile = File.createTempFile("tempFile_", ".svg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(tempFile)) {
            SVGType type = pathComboBox.getSelectionModel().getSelectedItem();
            getSvgGraphics2D(type).stream(writer, true);
            String paths = SVGPathExtractor.extractPaths(tempFile);
            CopyUtil.setCopyOnClick((Button) event.getSource(), paths);
    } catch (IOException e) {
            LOGGER.error("Failed to write svg file: {}", tempFile, e);
            throw new RuntimeException(e);
        }
    }

    private ComboBox<SVGType> createSvgTypeComboBox() {
        ComboBox<SVGType> comboBox = new ComboBox<>(FXCollections.observableArrayList(SVGType.FILL, SVGType.OUTLINE));
        comboBox.getSelectionModel().select(0);
        comboBox.setFocusTraversable(false);
        return comboBox;
    }

    private SVGGraphics2D getSvgGraphics2D(SVGType svgType) {
        IkonHandler handler = IkonResolver.getInstance().resolve(getData().getDescription());
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, handler.getFontResourceAsStream()).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            LOGGER.error("Failed to load font: {}", handler.getFontFamily(), e);
            throw new RuntimeException(e);
        }

        char iconChar = (char) getData().getCode();
        GlyphVector glyphVector = font.createGlyphVector(new FontRenderContext(new AffineTransform(), true, true), "" + iconChar);
        Shape glyphShape = glyphVector.getGlyphOutline(0);

        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        if (svgType == SVGType.FILL) {
            svgGenerator.fill(glyphShape);
        } else {
            svgGenerator.draw(glyphShape);
        }
        return svgGenerator;
    }

    private void webDownloadFile(SVGGraphics2D svgGenerator) {
        File cacheDir = new File(new File(System.getProperty("user.home")), ".jfxcentral/svgcache");
        if (!cacheDir.exists()) {
            boolean cacheDirCreated = cacheDir.mkdir();
            if (!cacheDirCreated) {
                LOGGER.error("Failed to create cache directory: {}", cacheDir);
                throw new RuntimeException("Failed to create svg cache directory: " + cacheDir);
            }
        }

        File tempDir = null;
        try {
            tempDir = File.createTempFile("tempDir_", "_dir", cacheDir);
            if (!tempDir.delete() || !tempDir.mkdir()) {
                LOGGER.error("Failed to create temp directory: {}", tempDir);
                throw new IOException("Failed to create a temporary directory for SVG");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create svg temp directory: {}", tempDir, e);
            throw new RuntimeException(e);
        }

        File tempFile = new File(tempDir, ikonInfo.iconLiteral + ".svg");
        try (FileWriter writer = new FileWriter(tempFile)) {
            svgGenerator.stream(writer, true);
        } catch (IOException e) {
            LOGGER.error("Failed to write svg file: {}", tempFile, e);
            throw new RuntimeException(e);
        }

        try {
            File finalTempDir = tempDir;
            WebAPIUtil.getWebAPI(this).downloadURL(tempFile.toURI().toURL(), () -> {
                try {
                    if (OSUtil.isWindows()) {
                        //TODO On Windows systems, temporary folders cannot be deleted; I need to try something else
                        //FileUtils.deleteDirectory(finalTempDir);
                    } else {
                        // TODO macOS test passed, but under Linux, it has not been tested yet
                        FileUtils.deleteDirectory(finalTempDir);
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to delete temp directory: {}", finalTempDir, e);
                    throw new RuntimeException(e);
                }
            });
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to convert svg file to URL: {}", tempFile, e);
            throw new RuntimeException(e);
        }
    }

    private void desktopDownloadFile(SVGGraphics2D svgGenerator) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(ikonInfo.iconLiteral + ".svg");
        if (initDirectory != null) {
            fileChooser.setInitialDirectory(initDirectory);
        }
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SVG Files", "*.svg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(getScene().getWindow());
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                initDirectory = file.getParentFile();
                svgGenerator.stream(fileWriter, true);
            } catch (IOException e) {
                LOGGER.error("Failed to write the file: {}", file, e);
            }
        }
    }

    private void addRow(FlowPane flowPane, String title, String contentText) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().addAll("title");
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());

        Button button = new Button();
        button.setFocusTraversable(false);
        button.setGraphic(new FontIcon(IkonUtil.copy));
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.managedProperty().bind(button.visibleProperty());

        TextField textField = new TextField(contentText);
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setContextMenu(null);
        textField.managedProperty().bind(textField.visibleProperty());
        CopyUtil.setCopyOnClick(button, contentText);

        HBox box = new HBox(titleLabel, textField, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }

    private void addRow(FlowPane flowPane, String title, Ikon ikon, Node node, EventHandler<ActionEvent> eventHandler) {
        Label titleLabel = new Label(title);
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());
        titleLabel.getStyleClass().addAll("title");

        Button button = new Button();
        button.setFocusTraversable(false);
        button.getStyleClass().addAll("fill-button", "copy-button", "runnable-button");
        button.setGraphic(new FontIcon(ikon));
        button.managedProperty().bind(button.visibleProperty());
        button.setOnAction(eventHandler);

        HBox box = new HBox(titleLabel, node, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }

}
