package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.model.IconInfo;
import com.dlsc.jfxcentral2.model.IconInfoBuilder;
import com.dlsc.jfxcentral2.utils.FileUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.LOGGER;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.SVGPathExtractor;
import com.dlsc.jfxcentral2.utils.WebAPIUtil;
import com.jpro.webapi.WebAPI;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import one.jpro.platform.utils.CopyUtil;
import one.jpro.platform.routing.LinkUtil;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
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
import java.io.InputStream;
import java.net.MalformedURLException;

public class IkonDetailView extends DetailView<Ikon> {
    protected static File initDirectory;
    private static final String CACHE_PATH = ".jfxcentral/svgcache";
    private static final String TEMP_DIR_PREFIX = "tempDir_";
    private static final String TEMP_DIR_SUFFIX = "_dir";
    private static final String SVG_EXTENSION = ".svg";
    private static final float SVG_FONT_SIZE = 24;

    public enum SVGType {
        FILL,
        OUTLINE
    }

    private final IconInfo iconInfo;

    public IkonDetailView(Ikon ikon) {
        this(new IconInfoBuilder(ikon).build());
    }

    public IkonDetailView(IconInfo iconInfo) {
        super(iconInfo.getIkon());
        this.iconInfo = iconInfo;

        getStyleClass().add("ikon-detail-view");

        if (iconInfo.getClass().getSimpleName().equals("PaymentFont")) {
            getStyleClass().add("payment-font-detail-view");
        }

        FontIcon fontIcon = new FontIcon(iconInfo.getIkon());
        iconInfo.setPath(extractorPathFromIcon(iconInfo.getIconLiteral()));

        VBox ikonPreview = new VBox(fontIcon, createIconBottomNode());
        ikonPreview.getStyleClass().add("ikon-preview");
        ikonPreview.setMaxWidth(Region.USE_PREF_SIZE);

        VBox previewPaneWrapper = new VBox(ikonPreview);
        previewPaneWrapper.getStyleClass().add("ikon-preview-wrapper");
        HBox.setHgrow(previewPaneWrapper, Priority.ALWAYS);

        Node infoNode = createInfoNode();
        HBox.setHgrow(infoNode, Priority.ALWAYS);
        HBox detailContent = new HBox();
        if (OSUtil.isAndroidOrIOS()) {
            detailContent.getChildren().setAll(infoNode);
        } else {
            detailContent.getChildren().setAll(previewPaneWrapper, infoNode);
        }
        detailContent.getStyleClass().add("detail-content");
        getChildren().setAll(detailContent);
    }

    protected Node createIconBottomNode() {
        String iconUrl = "/icons/" + iconInfo.getIkonliPackId() + "/" + iconInfo.getIconLiteral();

        Button button = new Button("Details");
        button.getStyleClass().addAll("fill-button");
        button.setFocusTraversable(false);
        LinkUtil.setLink(button, iconUrl);
        return button;
    }

    protected Node createInfoNode() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().add("ikon-info-grid-pane");
        HBox.setHgrow(flowPane, Priority.ALWAYS);

        addRow(flowPane, "Icon Literal:", iconInfo.getIconLiteral());
        addRow(flowPane, "CSS Code:", iconInfo.getCssCode());
        addRow(flowPane, "Java Code:", iconInfo.getJavaCode());
        addRow(flowPane, "Unicode:", iconInfo.getUnicode());
        addRow(flowPane, "Maven:", iconInfo.getMavenInfo());
        addRow(flowPane, "Gradle :", iconInfo.getGradleInfo());

        if (OSUtil.isAWTSupported()) {
            addRow(flowPane, "Path:", iconInfo.getPath());
            addDownloadSvgRow(flowPane);
        }

        return flowPane;
    }

    protected void downloadSVGHandler(SVGType type) {
        SVGGraphics2D g2d = getSvgGraphics2D(type);
        if (WebAPI.isBrowser()) {
            webDownloadFile(g2d, type);
        } else {
            desktopDownloadFile(g2d, type);
        }
    }

    private String extractorPathFromIcon(String iconLiteral) {
        // no AWT support on ios / android
        if (OSUtil.isAWTSupported()) {
            SVGType type = SVGType.FILL;
            SVGGraphics2D g2d = getSvgGraphics2D(type);

            File cacheDir = getCacheDirectory();
            File tempFile = createSvgTempFile(g2d, cacheDir, type, iconLiteral);

            String paths = SVGPathExtractor.extractPaths(tempFile);

            g2d.dispose();
            FileUtil.clearDirectory(cacheDir);

            return paths;
        }
        return "";
    }

    private SVGGraphics2D getSvgGraphics2D(SVGType svgType) {
        IkonHandler handler = IkonResolver.getInstance().resolve(getData().getDescription());
        Font font;
        /*
         * The createFont method does not close the provided InputStream.
         * Therefore, we use a try-with-resources block
         * to ensure the InputStream gets closed properly.
         */
        try (InputStream inputStream = handler.getFontResourceAsStream()) {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(SVG_FONT_SIZE);
        } catch (FontFormatException | IOException e) {
            LOGGER.error("Failed to load font: {}", handler.getFontFamily(), e);
            throw new RuntimeException(e);
        }

        char iconChar = (char) getData().getCode();
        GlyphVector glyphVector = font.createGlyphVector(new FontRenderContext(new AffineTransform(), true, true), "" + iconChar);
        Shape glyphShape = glyphVector.getGlyphOutline(0);

        // Center the glyph vertically
        java.awt.geom.Rectangle2D bounds = glyphShape.getBounds2D();
        double yOffset = (SVG_FONT_SIZE + bounds.getHeight()) / 2 - bounds.getMaxY();
        AffineTransform transform = AffineTransform.getTranslateInstance(0, yOffset);
        Shape centeredGlyph = transform.createTransformedShape(glyphShape);

        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
        SVGGraphics2D g2d = new SVGGraphics2D(document);
        if (svgType == SVGType.FILL) {
            g2d.fill(centeredGlyph);
        } else {
            g2d.draw(centeredGlyph);
        }
        return g2d;
    }

    private void webDownloadFile(SVGGraphics2D g2d, SVGType type) {
        File cacheDir = getCacheDirectory();
        File tempFile = createSvgTempFile(g2d, cacheDir, type, iconInfo.getIconLiteral());
        g2d.dispose();
        try {
            /*
             * On Windows, the temporary files used for the download function might be occupied by the browser, causing delayed deletions to fail.
             * Hence, we use the `clearDirectory` method which skips failed file deletions and proceeds with deleting the others.
             * Icons that fail to be deleted on this attempt will typically be cleaned up during the next icon download.
             * This ensures that the cache directory always contains the minimum number of files.
             */
            WebAPIUtil.getWebAPI(this).downloadURL(tempFile.toURI().toURL(), () -> FileUtil.clearDirectory(cacheDir));
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to convert svg file to URL: {}", tempFile, e);
            throw new RuntimeException(e);
        }
    }

    private File getCacheDirectory() {
        File cacheDir = new File(System.getProperty("user.home"), CACHE_PATH);
        if (!cacheDir.exists() && !cacheDir.mkdir()) {
            LOGGER.error("Failed to create cache directory: {}", cacheDir);
            throw new RuntimeException("Failed to create svg cache directory: " + cacheDir);
        }
        return cacheDir;
    }

    private File createSvgTempFile(SVGGraphics2D g2d, File cacheDir, SVGType type, String iconLiteral) {
        File tempDir = createTempDirectory(cacheDir);
        File tempFile = new File(tempDir, iconLiteral + "-" + type.toString().toLowerCase() + SVG_EXTENSION);

        try (FileWriter writer = new FileWriter(tempFile)) {
            g2d.stream(writer, true);
        } catch (IOException e) {
            LOGGER.error("Failed to write svg to file: {}", tempFile, e);
            throw new RuntimeException(e);
        }
        return tempFile;
    }

    private File createTempDirectory(File parentDir) {
        File tempDir = null;
        try {
            tempDir = File.createTempFile(TEMP_DIR_PREFIX, TEMP_DIR_SUFFIX, parentDir);
            if (!tempDir.delete() || !tempDir.mkdir()) {
                LOGGER.error("Failed to create temp directory: {}", tempDir);
                throw new IOException("Failed to create a temporary directory for SVG.");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create svg temp directory: {}", tempDir, e);
            throw new RuntimeException(e);
        }
        return tempDir;
    }

    private void desktopDownloadFile(SVGGraphics2D g2d, SVGType type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(iconInfo.getIconLiteral() + "-" + type.toString().toLowerCase() + SVG_EXTENSION);
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
                g2d.stream(fileWriter, true);
            } catch (IOException e) {
                LOGGER.error("Failed to write the file: {}", file, e);
            } finally {
                g2d.dispose();
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
        HBox.setHgrow(textField, Priority.ALWAYS);

        HBox box = new HBox(titleLabel, textField, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }

    private void addDownloadSvgRow(FlowPane flowPane) {
        Label titleLabel = new Label("SVG:");
        titleLabel.getStyleClass().addAll("title");

        ComboBox<SVGType> svgTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(SVGType.FILL, SVGType.OUTLINE));
        svgTypeComboBox.getSelectionModel().select(0);
        svgTypeComboBox.setFocusTraversable(false);

        Button button = new Button();
        button.setFocusTraversable(false);
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.setGraphic(new FontIcon(IkonUtil.download));
        SVGType svgType = svgTypeComboBox.getSelectionModel().getSelectedItem();
        button.setOnAction(event -> downloadSVGHandler(svgType));

        HBox box = new HBox(titleLabel, svgTypeComboBox, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }

    protected IconInfo getIconInfo() {
        return iconInfo;
    }

}
