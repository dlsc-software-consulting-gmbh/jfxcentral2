package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Coordinates;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.NameProvider;
import com.dlsc.jfxcentral2.utils.FXUtil;
import com.dlsc.jfxcentral2.utils.FilesUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
import com.dlsc.jfxcentral2.utils.WebAPIUtil;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import simplefx.experimental.parts.FXFuture;

public class LibraryCoordinatesBox extends PaneBase implements NameProvider {
    private static final String LINED_SEPARATOR = System.lineSeparator();
    private final VBox contentBox;
    private final String groupId;
    private final String artifactId;
    private final boolean isAvailable;

    public LibraryCoordinatesBox(Coordinates coordinates) {
        getStyleClass().addAll("overview-box", "library-coordinates-box");
        contentBox = new VBox();
        groupId = coordinates.getGroupId();
        artifactId = coordinates.getArtifactId();
        isAvailable = StringUtils.isNotBlank(groupId) && StringUtils.isNotBlank(artifactId);
        setVisible(isAvailable);
        setManaged(isAvailable);
        StringProperty versionProperty = new SimpleStringProperty(this, "version");

        if (isAvailable) {
            FXFuture.runBackground(() -> DataRepository2.getInstance().getArtifactVersion(coordinates)).map(property -> {
                versionProperty.bind(property);
                return null;
            });
        } else {
            versionProperty.unbind();
        }

        Header headerBox = new Header();
        headerBox.setTitle("COORDINATES");
        headerBox.setIcon(MaterialDesign.MDI_COMPASS);

        Node bodyNode = WebAPI.isBrowser() ? createInfoNodeForWeb(versionProperty) : createInfoNodeForFX(versionProperty);
        contentBox.getChildren().setAll(headerBox, bodyNode);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

    }

    private VBox createInfoNodeForFX(StringProperty versionProperty) {
        Label repositoryCoordinatesLabel = new Label(StringUtil.LOADING_TIPS);
        repositoryCoordinatesLabel.getStyleClass().add("coordinates-label");
        repositoryCoordinatesLabel.setWrapText(true);
        repositoryCoordinatesLabel.setMinHeight(Region.USE_PREF_SIZE);
        repositoryCoordinatesLabel.setMaxWidth(Double.MAX_VALUE);

        if (isAvailable) {
            repositoryCoordinatesLabel.textProperty().bind(Bindings.createStringBinding(() -> {
                String version = versionProperty.get();
                if (StringUtils.equalsIgnoreCase("unknown", version)) {
                    return "WAIT TIMEOUT...";
                }
                if (getBuildTool().equals(BuildTool.MAVEN)) {
                    return getMavenInfo(version);
                }
                return getGradleInfo(version);
            }, versionProperty, buildTool));
        }

        RadioButton mavenButton = new RadioButton("Maven");
        RadioButton gradleButton = new RadioButton("Gradle");
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(mavenButton, gradleButton);
        mavenButton.setOnAction(evt -> setBuildTool(BuildTool.MAVEN));
        gradleButton.setOnAction(evt -> setBuildTool(BuildTool.GRADLE));
        mavenButton.setSelected(true);

        Button copyButton = new Button();
        copyButton.getStyleClass().addAll("blue-button", "copy-button");
        copyButton.setGraphic(new FontIcon(IkonUtil.copy));
        copyButton.setOnAction(evt -> {
            evt.consume();
            String content = repositoryCoordinatesLabel.getText();
            if (WebAPI.isBrowser()) {
                WebAPIUtil.copyToClipboard(copyButton, content);
            } else {
                FXUtil.copyToClipboard(content);
            }
        });

        HBox buttonsBox = new HBox(mavenButton, gradleButton, new Spacer());
        buttonsBox.getStyleClass().add("buttons-box");
        buttonsBox.getChildren().add(copyButton);

        CustomMarkdownView descriptionLabel = new CustomMarkdownView();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.mdStringProperty().bind(descriptionProperty());

        VBox bodyBox = new VBox(descriptionLabel, buttonsBox, repositoryCoordinatesLabel);
        bodyBox.getStyleClass().add("body-box");
        return bodyBox;
    }

    private String getGradleInfo(String version) {
        return "dependencies {" + LINED_SEPARATOR +
                "    implementation '" + groupId + ":" + artifactId + ":" + version + "'" + LINED_SEPARATOR +
                "}";
    }

    private String getMavenInfo(String version) {
        return "<dependency>" + LINED_SEPARATOR +
                "    <groupId>" + groupId + "</groupId>" + LINED_SEPARATOR +
                "    <artifactId>" + artifactId + "</artifactId>" + LINED_SEPARATOR +
                "    <version>" + version + "</version>" + LINED_SEPARATOR +
                "</dependency>";
    }

    private Node createInfoNodeForWeb(StringProperty versionProperty) {
        HTMLView view = new HTMLView();

        String str = FilesUtil.readText("/com/dlsc/jfxcentral2/htmlviews/LibraryCoordinatesView.html");
        assert str != null;

        view.setContent(str.replace("${mavenInfo}", StringUtil.LOADING_TIPS)
                .replace("${gradleInfo}", StringUtil.LOADING_TIPS));

        if (isAvailable) {
            view.contentProperty().bind(Bindings.createStringBinding(() -> {
                String version = versionProperty.get();
                if (StringUtils.isEmpty(version)) {
                    return str.replace("${mavenInfo}", StringUtil.LOADING_TIPS)
                            .replace("${gradleInfo}", StringUtil.LOADING_TIPS);
                } else if (StringUtils.equalsIgnoreCase("unknown", version)) {
                    return str.replace("${mavenInfo}", "WAIT TIMEOUT...")
                            .replace("${gradleInfo}", "WAIT TIMEOUT...");
                }
                return str.replace("${mavenInfo}", getMavenInfo(version))
                        .replace("${gradleInfo}", getGradleInfo(version));
            }, versionProperty));
        }

        view.setPrefHeight(180);
        return view;
    }

    private final StringProperty description = new SimpleStringProperty(this, "description");

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String getName() {
        return "Coordinates";
    }

    public enum BuildTool {
        MAVEN,
        GRADLE
    }

    private final ObjectProperty<BuildTool> buildTool = new SimpleObjectProperty<>(this, "buildTool", BuildTool.MAVEN);

    public BuildTool getBuildTool() {
        return buildTool.get();
    }

    public ObjectProperty<BuildTool> buildToolProperty() {
        return buildTool;
    }

    public void setBuildTool(BuildTool buildTool) {
        this.buildTool.set(buildTool);
    }
}