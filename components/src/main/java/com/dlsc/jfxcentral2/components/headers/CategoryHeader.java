package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class CategoryHeader extends PaneBase {
    private static final PseudoClass LIGHT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("light");
    private static final PseudoClass DARK_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("dark");
    private static Image defaultLargeBgImage;
    private static Image defaultMediumBgImg;
    private static Image defaultSmallBgImg;
    private double xOffset;
    private double yOffset;

    public CategoryHeader() {
        getStyleClass().add("category-header");

        backgroundProperty().bind(
                Bindings.createObjectBinding(() ->
                        createImageBackground(getBackgroundImage()), backgroundImageProperty(), sizeProperty(), modeProperty())
        );

        FontIcon fontIcon = new FontIcon();
        fontIcon.iconCodeProperty().bind(ikonProperty());

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(titleProperty());
        titleLabel.setGraphic(fontIcon);
        titleLabel.getStyleClass().add("header-title");
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());
        titleLabel.visibleProperty().bind(titleProperty().isNotEmpty().or(ikonProperty().isNotNull()));

        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("header-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.textProperty().bind(descriptionProperty());
        descriptionLabel.managedProperty().bind(descriptionLabel.visibleProperty());
        descriptionLabel.visibleProperty().bind(descriptionProperty().isNotEmpty());

        VBox defaultContent = new VBox(titleLabel, descriptionLabel);
        defaultContent.getStyleClass().add("default-content");
        defaultContent.managedProperty().bind(defaultContent.visibleProperty());
        defaultContent.visibleProperty().bind(titleLabel.visibleProperty().or(descriptionLabel.visibleProperty()));

        Region overlay = new Region();
        overlay.getStyleClass().add("overlay");

        getChildren().setAll(overlay, defaultContent);

        activateModePseudoClass();
        modeProperty().addListener(it -> activateModePseudoClass());
        /*
         * Only one defaultContent and content can be displayed. If the content is not empty, the content will be displayed,
         * otherwise the defaultContent will be displayed
         */
        contentProperty().addListener((ob, ov, nv) -> getChildren().setAll(overlay, Objects.requireNonNullElse(getContent(), defaultContent)));
        sizeProperty().addListener((ob, ov, nv) -> getChildren().setAll(overlay, Objects.requireNonNullElse(getContent(), defaultContent)));


        if (!WebAPI.isBrowser()) {
            setOnMousePressed(event -> {
                xOffset = getScene().getWindow().getX() - event.getScreenX();
                yOffset = getScene().getWindow().getY() - event.getScreenY();
            });

            setOnMouseDragged(event -> {
                getScene().getWindow().setX(event.getScreenX() + xOffset);
                getScene().getWindow().setY(event.getScreenY() + yOffset);
            });
        }
    }

    private void activateModePseudoClass() {
        Mode mode = getMode();
        pseudoClassStateChanged(LIGHT_PSEUDOCLASS_STATE, mode == Mode.LIGHT);
        pseudoClassStateChanged(DARK_PSEUDOCLASS_STATE, mode == Mode.DARK);
    }

    private Background createImageBackground(Image image) {
        if (getMode() == Mode.LIGHT) {
            return null;
        }
        if (image == null) {
            switch (getSize()) {
                case LARGE -> {
                    if (defaultLargeBgImage == null) {
                        defaultLargeBgImage = new Image("/com/dlsc/jfxcentral2/components/headers/bg-lg.jpg");
                    }
                    image = defaultLargeBgImage;
                }
                case MEDIUM -> {
                    if (defaultMediumBgImg == null) {
                        defaultMediumBgImg = new Image("/com/dlsc/jfxcentral2/components/headers/bg-md.jpg");
                    }
                    image = defaultMediumBgImg;
                }
                case SMALL -> {
                    if (defaultSmallBgImg == null) {
                        defaultSmallBgImg = new Image("/com/dlsc/jfxcentral2/components/headers/bg-sm.jpg");
                    }
                    image = defaultSmallBgImg;
                }
            }

        }
        return new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)));
    }

    private final StringProperty title = new SimpleStringProperty(this, "title");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final ObjectProperty<Ikon> ikon = new SimpleObjectProperty<>(this, "ikon");

    public Ikon getIkon() {
        return ikon.get();
    }

    public ObjectProperty<Ikon> ikonProperty() {
        return ikon;
    }

    public void setIkon(Ikon ikon) {
        this.ikon.set(ikon);
    }

    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    private final ObjectProperty<Image> backgroundImage = new SimpleObjectProperty<>(this, "backgroundImage");

    public Image getBackgroundImage() {
        return backgroundImage.get();
    }

    public ObjectProperty<Image> backgroundImageProperty() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage.set(backgroundImage);
    }

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this, "mode", Mode.DARK);

    public Mode getMode() {
        return mode.get();
    }

    public ObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode.set(mode);
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
}
