package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.components.TextCopyView;
import com.dlsc.jfxcentral2.model.IconInfo;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import one.jpro.platform.utils.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2RoundAL;

public class IkonDetailExtraView extends IkonDetailView {

    public IkonDetailExtraView(IconInfo iconInfo) {
        super(iconInfo);
        getStyleClass().add("ikon-detail-extra-view");
    }

    @Override
    protected Node createInfoNode() {
        IconInfo iconInfo = getIconInfo();

        Node literalBox = createInfoBoxWithTextField("Literal", iconInfo.getIconLiteral());
        Node cssBox = createInfoBoxWithTextField("CSS", iconInfo.getCssCode());
        Node javaBox = createInfoBoxWithTextField("Java", iconInfo.getJavaCode());
        Node unicodeBox = createInfoBoxWithTextField("Unicode", iconInfo.getUnicode());

        TextCopyView textCopyView = new TextCopyView();
        textCopyView.getOptions().addAll("Maven", "Gradle");
        textCopyView.setSelectedIndex(0);
        textCopyView.textProperty().bind(Bindings.createStringBinding(() -> {
            String selectedItem = textCopyView.getSelectedItem();
            if (StringUtils.equalsIgnoreCase(selectedItem, "Maven")) {
                return iconInfo.getMavenInfo();
            } else {
                return iconInfo.getGradleInfo();
            }
        }, textCopyView.selectedItemProperty()));

        Node path = createInfoBoxWithTextArea("Path", iconInfo.getPath());

        Node downloadFillBox = createDownloadBox("Download SVG(Fill)", SVGType.FILL);
        Node downloadOutlineBox = createDownloadBox("Download SVG(Outline)", SVGType.OUTLINE);

        VBox infoBoxWrapper = new VBox(literalBox, cssBox, javaBox, unicodeBox, textCopyView);

        if (OSUtil.isAWTSupported()) {
            infoBoxWrapper.getChildren().addAll(path, downloadFillBox, downloadOutlineBox);
        }

        infoBoxWrapper.getStyleClass().add("info-box-wrapper");
        return infoBoxWrapper;
    }

    private Node createDownloadBox(String title, SVGType svgType) {
        Button button = new Button();
        button.setFocusTraversable(false);
        button.setGraphic(new FontIcon(Material2RoundAL.CLOUD_DOWNLOAD));
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.setOnAction(e -> downloadSVGHandler(svgType));

        HBox downloadBox = new HBox(new Label(title), new Spacer(), button);
        downloadBox.getStyleClass().add("download-box");

        return downloadBox;
    }

    private Node createInfoBox(String title, String contentText, Node contentNode) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("info-label");

        Button copyButton = new Button();
        copyButton.setFocusTraversable(false);
        copyButton.setGraphic(new FontIcon(IkonUtil.copy));
        copyButton.getStyleClass().addAll("fill-button", "copy-button");
        copyButton.managedProperty().bind(copyButton.visibleProperty());
        CopyUtil.setCopyOnClick(copyButton, contentText);

        HBox titleBox = new HBox(titleLabel, new Spacer(), copyButton);
        titleBox.getStyleClass().add("title-box");

        VBox infoBox = new VBox(titleBox, contentNode);
        infoBox.getStyleClass().add("info-box");
        return infoBox;
    }

    private Node createInfoBoxWithTextField(String title, String contentText) {
        TextField textField = new TextField();
        textField.setText(contentText);
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setContextMenu(null);
        textField.managedProperty().bind(textField.visibleProperty());
        return createInfoBox(title, contentText, textField);
    }

    private Node createInfoBoxWithTextArea(String title, String contentText) {
        TextArea textArea = new TextArea();
        textArea.setText(contentText);
        textArea.setFocusTraversable(false);
        textArea.setEditable(false);
        textArea.setContextMenu(null);
        textArea.managedProperty().bind(textArea.visibleProperty());
        textArea.setWrapText(true);
        textArea.getStyleClass().add("code-text-area");
        return createInfoBox(title, contentText, textArea);
    }

    @Override
    protected Node createIconBottomNode() {
        IconInfo iconInfo = getIconInfo();
        String iconUrl = "/icons/" + iconInfo.getIkonliPackId() + "/" + iconInfo.getIconLiteral();

        //Web URL Button
        Button webUrlButton = new Button("Web URL");
        webUrlButton.getStyleClass().addAll("fill-button");
        webUrlButton.setMaxWidth(Double.MAX_VALUE);
        webUrlButton.setFocusTraversable(false);
        CopyUtil.setCopyOnClick(webUrlButton, "https://www.jfx-central.com" + iconUrl);
        webUrlButton.setGraphic(new FontIcon(IkonUtil.copy));

        //Special Protocol Link
        Button linkButton = new Button("App Link");
        linkButton.setFocusTraversable(false);
        linkButton.setGraphic(new FontIcon(IkonUtil.copy));
        linkButton.getStyleClass().addAll("fill-button");
        // read the url schemes from the system properties
        String urlSchemes = System.getProperty("url.schemes", "jfxcentral");
        String specialProtocolLink = urlSchemes + ":/" + iconUrl;
        CopyUtil.setCopyOnClick(linkButton, specialProtocolLink);

        VBox bottomBox = new VBox(webUrlButton, linkButton);
        bottomBox.getStyleClass().add("bottom-box");
        return bottomBox;
    }

}
