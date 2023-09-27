package com.dlsc.jfxcentral2.devtools.pathextractor;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.devtools.FileHandlerView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ReadTextFile;
import com.dlsc.jfxcentral2.utils.SVGPathExtractor;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import one.jpro.platform.routing.CopyUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;
import org.kordamp.ikonli.whhg.WhhgAL;

import java.io.File;

public class SVGPathExtractorView extends PaneBase {
    /**
     * The preferred width of the preview image.
     */
    private static final double PREF_WIDTH = 120;
    /**
     * The preferred height of the preview image.
     */
    private static final double PREF_HEIGHT = 120;
    /**
     * The maximum number of recent SVG files to show.
     */
    private static final int MAX_RECENT_SVG = 10;
    /**
     * The SVG file to be parsed.
     */
    private final ObjectProperty<File> svgFileProperty = new SimpleObjectProperty<>(this, "svgFile");
    /**
     * The extracted path from the SVG file.
     */
    private final StringProperty pathProperty = new SimpleStringProperty(this, "path");
    private final ObjectProperty<SvgToImageUtil.ImageResult> imageResultProperty = new SimpleObjectProperty<>(this, "svgImage");
    private final ObservableList<SvgToImageUtil.ImageResult> recentSvgList = FXCollections.observableArrayList();
    private Region fxSvgRegion;
    private StackPane jsvgPane;

    public SVGPathExtractorView() {
        getStyleClass().addAll("online-tools-view", "svg-path-extractor-view");

        imageResultProperty.bind(svgFileProperty.map(file -> SvgToImageUtil.parserSVG(file, PREF_WIDTH, PREF_HEIGHT)));
        pathProperty.bind(svgFileProperty.map(SVGPathExtractor::extractPaths));

        // Header
        Header header = new Header();
        header.setTitle("SVG Path Extractor");
        header.setIcon(MaterialDesignS.SHAPE_OUTLINE);

        // Top
        HBox jsvgBox = createJSVGBox();

        // Center
        HBox fxSvgBox = createFXSVGBox();

        // Bottom
        String mdTips = ReadTextFile.readText(this.getClass(), "/com/dlsc/jfxcentral2/devtools/path-extractor-tips.md");
        CustomMarkdownView tipsView = new CustomMarkdownView(mdTips);
        tipsView.getStyleClass().add("tips-view");

        // Container
        VBox container = new VBox(header, jsvgBox, fxSvgBox, tipsView);
        container.getStyleClass().add("container");

        getChildren().setAll(container);
    }

    private HBox createFXSVGBox() {
        SVGPath fxSvgPath = new SVGPath();
        fxSvgPath.getStyleClass().add("fx-svg-path");

        fxSvgPath.contentProperty().bind(Bindings.when(pathProperty.isNotEmpty())
                .then(pathProperty)
                .otherwise(""));

        fxSvgRegion = new Region();
        fxSvgRegion.getStyleClass().add("fxsvg-region");
        fxSvgRegion.managedProperty().bind(fxSvgRegion.visibleProperty());
        fxSvgRegion.visibleProperty().bind(fxSvgRegion.styleProperty().isNotEmpty());
        fxSvgRegion.setShape(fxSvgPath);
        fxSvgRegion.styleProperty().bind(Bindings.createStringBinding(() -> generateStyleString(fxSvgPath), pathProperty, imageResultProperty));

        StackPane fxPreviewPane = new StackPane(fxSvgRegion);
        fxPreviewPane.getStyleClass().addAll("fx-preview-pane", "preview-pane");
        fxPreviewPane.minWidthProperty().bind(jsvgPane.widthProperty());
        fxPreviewPane.prefWidthProperty().bind(jsvgPane.widthProperty());
        fxPreviewPane.maxWidthProperty().bind(jsvgPane.widthProperty());

        fxPreviewPane.minHeightProperty().bind(jsvgPane.heightProperty());
        fxPreviewPane.prefHeightProperty().bind(jsvgPane.heightProperty());
        fxPreviewPane.maxHeightProperty().bind(jsvgPane.heightProperty());

        ToggleGroup codeGroup = new ToggleGroup();

        RadioButton pathRadioButton = new RadioButton("Path");
        pathRadioButton.setFocusTraversable(false);
        pathRadioButton.setToggleGroup(codeGroup);
        pathRadioButton.setSelected(true);

        RadioButton cssRadioButton = new RadioButton("Css");
        cssRadioButton.setFocusTraversable(false);
        cssRadioButton.setToggleGroup(codeGroup);

        Button copyButton = new Button();
        copyButton.getStyleClass().addAll("blue-button", "copy-button");
        copyButton.setGraphic(new FontIcon(IkonUtil.copy));
        copyButton.setFocusTraversable(false);

        HBox buttonBox = new HBox(pathRadioButton, cssRadioButton, new Spacer(), copyButton);
        buttonBox.getStyleClass().add("button-box");

        TextArea codeTextArea = new TextArea();
        codeTextArea.getStyleClass().add("code-text-area");
        codeTextArea.setWrapText(true);
        codeTextArea.setEditable(false);
        codeTextArea.setFocusTraversable(false);
        codeTextArea.textProperty().bind(Bindings.createStringBinding(() -> {
            String path = pathProperty.get();
            if (path == null || path.trim().isEmpty()) {
                return "No path extracted.";
            }
            if (pathRadioButton.isSelected()) {
                return path;
            } else {
                return buildStyleString(path, (int) fxSvgRegion.getWidth(), (int) fxSvgRegion.getHeight());
            }
        }, codeGroup.selectedToggleProperty(), pathProperty, fxSvgRegion.widthProperty(), fxSvgRegion.heightProperty()));

        CopyUtil.setCopyOnClick(copyButton, codeTextArea.getText());
        codeTextArea.textProperty().addListener(it -> CopyUtil.setCopyOnClick(copyButton, codeTextArea.getText()));

        VBox codeBox = new VBox(buttonBox, codeTextArea);
        codeBox.getStyleClass().add("code-box");
        HBox.setHgrow(codeBox, Priority.ALWAYS);

        HBox fxSvgBox = new HBox(fxPreviewPane, codeBox);
        fxSvgBox.getStyleClass().add("fxsvg-box");
        return fxSvgBox;
    }

    private String generateStyleString(SVGPath fxSvgPath) {
        SvgToImageUtil.ImageResult result = imageResultProperty.get();
        if (result == null) {
            return "";
        }

        double prefWidth = fxSvgPath.prefWidth(-1);
        double prefHeight = fxSvgPath.prefHeight(-1);

        double widthScale = PREF_WIDTH / prefWidth;
        double heightScale = PREF_HEIGHT / prefHeight;

        double scale = Math.min(widthScale, heightScale);

        int reasonableWidth = (int) (prefWidth * scale);
        int reasonableHeight = (int) (prefHeight * scale);

        return buildStyleString(pathProperty.get(), reasonableWidth, reasonableHeight);
    }

    private HBox createJSVGBox() {
        //left
        VBox leftBox = createJSVGLeftPane();

        //center
        GridPane gridPane = createJSVGCentralPane();

        HBox jsvgBox = new HBox(leftBox, gridPane);
        jsvgBox.getStyleClass().add("jsvg-box");

        return jsvgBox;
    }

    private GridPane createJSVGCentralPane() {
        Label fileNameLabel = createIconLabel("Name:", AntDesignIconsOutlined.FILE_IMAGE);

        Label fileNameValueLabel = createValueLabel();
        fileNameValueLabel.setMaxWidth(Double.MAX_VALUE);
        fileNameValueLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            File file = svgFileProperty.get();
            if (file == null) {
                return "";
            }
            return file.getName();
        }, svgFileProperty));

        Label widthLabel = createIconLabel("Width:", AntDesignIconsOutlined.COLUMN_WIDTH);
        Label widthValueLabel = createValueLabel();
        widthValueLabel.textProperty().bind(imageResultProperty.map(result -> result.originalWidth() + " PX"));

        Label heightLabel = createIconLabel("Height:", AntDesignIconsOutlined.COLUMN_HEIGHT);
        Label heightValueLabel = createValueLabel();
        heightValueLabel.textProperty().bind(imageResultProperty.map(result -> result.originalHeight() + " PX"));

        Label lengthLabel = createIconLabel("Length:", BoxiconsRegular.DATA);

        Label lengthValueLabel = createValueLabel();
        lengthValueLabel.textProperty().bind(svgFileProperty.map(file -> StringUtil.formatFileSize(file.length())));

        TilePane recentFilesPane = new TilePane();
        recentFilesPane.getStyleClass().add("recent-tile-pane");

        for (int i = 0; i < MAX_RECENT_SVG; i++) {
            RecentImageView imageView = new RecentImageView();
            imageView.setOnMouseClicked(event -> {
                if (!imageResultProperty.get().equals(imageView.getImageResult())) {
                    if (imageView.getImageResult().file().exists()) {
                        svgFileProperty.set(imageView.getImageResult().file());
                    }
                }
            });
            recentFilesPane.getChildren().add(imageView);
        }

        recentSvgList.addListener((InvalidationListener) observable -> {
            for (int i = recentSvgList.size() - 1; i >= 0; i--) {
                RecentImageView imageView = (RecentImageView) recentFilesPane.getChildren().get(i);
                imageView.setImageResult(recentSvgList.get(i));
            }
        });

        // init GridPane
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(75);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column0, column1);

        gridPane.add(fileNameLabel, 0, 0);
        gridPane.add(fileNameValueLabel, 1, 0);

        gridPane.add(lengthLabel, 0, 1);
        gridPane.add(lengthValueLabel, 1, 1);

        gridPane.add(widthLabel, 0, 2);
        gridPane.add(widthValueLabel, 1, 2);

        gridPane.add(heightLabel, 0, 3);
        gridPane.add(heightValueLabel, 1, 3);

        gridPane.add(recentFilesPane, 0, 4, 2, 1);

        HBox.setHgrow(gridPane, Priority.ALWAYS);
        return gridPane;
    }

    private Label createIconLabel(String text, Ikon iconCode) {
        Label label = new Label(text);
        label.getStyleClass().add("icon-label");
        label.setGraphic(new FontIcon(iconCode));
        return label;
    }

    private Label createValueLabel() {
        Label valueLabel = new Label();
        valueLabel.getStyleClass().add("value-label");
        return valueLabel;
    }

    private VBox createJSVGLeftPane() {
        jsvgPane = new StackPane();
        jsvgPane.getStyleClass().addAll("jsvg-preview-pane", "preview-pane");

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.imageProperty().bind(imageResultProperty.map(SvgToImageUtil.ImageResult::image));

        FileHandlerView fileHandlerView = new FileHandlerView();
        fileHandlerView.setText("Click or \n Drop SVG File");
        fileHandlerView.getStyleClass().add("drag-tip-label");
        fileHandlerView.showTipsProperty().bind(imageView.imageProperty().isNull());
        fileHandlerView.getSupportedExtensions().setAll(".svg");

        fileHandlerView.setOnUploadedFile(file -> {
            svgFileProperty.set(file);
            svgFileProperty.set(file);
            if (recentSvgList.size() == MAX_RECENT_SVG) {
                recentSvgList.remove(0);
            }
            recentSvgList.add(imageResultProperty.get());
        });

        jsvgPane.getChildren().setAll(imageView, fileHandlerView);

        VBox leftBox = new VBox(jsvgPane, createBgBox(jsvgPane));
        leftBox.getStyleClass().add("left-box");
        return leftBox;
    }

    private Node createBgBox(Pane node) {
        ToggleGroup bgGroup = new ToggleGroup();

        CustomToggleButton whiteBtn = createBackgroundButton("white-bg", bgGroup);

        CustomToggleButton transparentBtn = createBackgroundButton("transparent-bg", bgGroup);
        CustomToggleButton translucentBtn = createBackgroundButton("translucent-bg", bgGroup);
        CustomToggleButton greyBtn = createBackgroundButton("grey-bg", bgGroup);
        CustomToggleButton blackBtn = createBackgroundButton("black-bg", bgGroup);
        blackBtn.getStyleClass().add("last");

        HBox bgBox = new HBox();
        bgBox.getStyleClass().add("bg-box");
        bgBox.getChildren().setAll(whiteBtn, transparentBtn, translucentBtn, greyBtn, blackBtn);

        bgGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String userData = newValue.getUserData().toString();
                node.getStyleClass().removeAll("white-bg", "transparent-bg", "translucent-bg", "grey-bg", "black-bg");
                node.getStyleClass().add(userData);
            }
        });

        transparentBtn.setSelected(true);
        return bgBox;
    }

    private CustomToggleButton createBackgroundButton(String userData, ToggleGroup bgGroup) {
        CustomToggleButton btn = new CustomToggleButton();
        btn.getStyleClass().addAll(userData + "-btn", "bg-btn");
        btn.setToggleGroup(bgGroup);
        btn.setFocusTraversable(false);
        btn.setGraphic(new FontIcon(WhhgAL.CIRCLESELECT));
        btn.setUserData(userData);
        return btn;
    }

    private String buildStyleString(String path, int width, int height) {
        String styleFormat =
                "-fx-min-width: %dpx;" + StringUtil.NEW_LINE +
                        "-fx-min-height: %dpx;" + StringUtil.NEW_LINE +
                        "-fx-max-width: %dpx;" + StringUtil.NEW_LINE +
                        "-fx-max-height: %dpx;" + StringUtil.NEW_LINE +
                        "-fx-background-color: skyblue;" + StringUtil.NEW_LINE +
                        "-fx-shape: \"%s\";";
        return String.format(styleFormat, width, height, width, height, path);
    }
}
