package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Coordinates;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.NameProvider;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import simplefx.experimental.parts.FXFuture;

public class LibraryCoordinatesBox extends PaneBase implements NameProvider {

    private final Label repositoryCoordinatesLabel;

    public LibraryCoordinatesBox(Coordinates coordinates) {
        Header headerBox = new Header();
        headerBox.setTitle("COORDINATES");
        headerBox.setIcon(MaterialDesign.MDI_COMPASS);

        getStyleClass().addAll("overview-box", "library-coordinates-box");

        repositoryCoordinatesLabel = new Label();
        repositoryCoordinatesLabel.getStyleClass().add("coordinates-label");
        repositoryCoordinatesLabel.setWrapText(true);
        repositoryCoordinatesLabel.setMinHeight(Region.USE_PREF_SIZE);
        repositoryCoordinatesLabel.setMaxWidth(Double.MAX_VALUE);

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
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(repositoryCoordinatesLabel.getText());
            clipboard.setContent(content);
        });

        HBox buttonsBox = new HBox(mavenButton, gradleButton, new Spacer());
        buttonsBox.getStyleClass().add("buttons-box");
        if (!WebAPI.isBrowser()) {
            buttonsBox.getChildren().add(copyButton);
        }

        CustomMarkdownView descriptionLabel = new CustomMarkdownView();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.mdStringProperty().bind(descriptionProperty());

        VBox bodyBox = new VBox(descriptionLabel, buttonsBox, repositoryCoordinatesLabel);
        bodyBox.getStyleClass().add("body-box");

        VBox contentBox = new VBox(headerBox, bodyBox);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

        setVisible(StringUtils.isNotBlank(coordinates.getGroupId()) && StringUtils.isNotBlank(coordinates.getArtifactId()));
        setManaged(StringUtils.isNotBlank(coordinates.getGroupId()) && StringUtils.isNotBlank(coordinates.getArtifactId()));

        String groupId = coordinates.getGroupId();
        String artifactId = coordinates.getArtifactId();

        if (StringUtils.isNotBlank(groupId) && StringUtils.isNotBlank(artifactId)) {
            FXFuture.runBackground(() -> DataRepository2.getInstance().getArtifactVersion(coordinates)).map(property -> {
                repositoryCoordinatesLabel.textProperty().bind(Bindings.createStringBinding(() -> {
                    if (getBuildTool().equals(BuildTool.MAVEN)) {
                        return "<dependency>\n    <groupId>" + groupId + "</groupId>\n    <artifactId>" + artifactId + "</artifactId>\n    <version>" + property.get() + "</version>\n</dependency>";
                    }
                    return "dependencies {\n    implementation '" + groupId + ":" + artifactId + ":" + property.get() + "'\n}";
                }, property, buildTool));
                return null;
            });
        } else {
            repositoryCoordinatesLabel.textProperty().unbind();
        }
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