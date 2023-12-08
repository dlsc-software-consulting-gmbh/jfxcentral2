package com.dlsc.jfxcentral2.components.topcontent;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

public class TopContentView<T extends ModelObject> extends PaneBase {

    private final VBox centerBox;
    private Label tipsLabel;

    public TopContentView() {
        getStyleClass().add("top-content-view");

        Header header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(iconProperty());

        tipsLabel = new Label(StringUtil.LOADING_TIPS);
        tipsLabel.getStyleClass().add("loading-label");
        tipsLabel.setGraphic(new FontIcon(AntDesignIconsOutlined.CLOUD_DOWNLOAD));

        centerBox = new VBox(tipsLabel);
        centerBox.getStyleClass().add("center-box");

        Button loadMoreButton = new Button("LOAD MORE");
        loadMoreButton.getStyleClass().addAll("fill-button", "load-more-button");
        loadMoreButton.setFocusTraversable(false);

        VBox contentBox = new VBox(header, centerBox, loadMoreButton);
        contentBox.getStyleClass().add("content-box");

        getChildren().setAll(contentBox);

        loadMoreButton.setOnAction(evt -> {
            getItems().stream().skip(centerBox.getChildren().size()).limit(getLoadIncrement()).forEach(item -> centerBox.getChildren().add(createItemCell(item)));
            evt.consume();
        });

        loadMoreButton.setFocusTraversable(false);
        loadMoreButton.textProperty().bind(Bindings.when(loadMoreButton.disableProperty()).then("NO MORE ITEMS").otherwise("LOAD MORE"));
        loadMoreButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> getItems().size() <= centerBox.getChildren().size(), itemsProperty(), centerBox.getChildren()));

        itemsProperty().addListener((InvalidationListener) it -> updateUI());
    }

    @Override
    protected void layoutBySize() {
        if (!isLgToMdOrMdToLg()) {
            updateUI();
        }
    }

    private void updateUI() {
        if (getItems().isEmpty()) {
            centerBox.getChildren().setAll(tipsLabel);
            return;
        }
        centerBox.getChildren().clear();

        // TODO: Sort the items by saveCount + likeCount
        // getItems().sort((o1, o2) ->));

        getItems().stream().limit(getInitCount()).forEach(item -> centerBox.getChildren().add(createItemCell(item)));
    }

    protected Node createItemCell(T item) {
        //image
        CustomImageView preview = new CustomImageView();
        preview.imageProperty().bind(ModelObjectTool.getModelPreviewImageProperty(item, false));

        //image wrapper (Wrapper is required to ensure that the space occupied by the picture is consistent)
        StackPane previewWrapper = new StackPane(preview);
        previewWrapper.getStyleClass().add("preview-wrapper");

        //title
        Label mainTitle = new Label();
        mainTitle.getStyleClass().add("main-title");
        mainTitle.setText(item.getName());

        //description
        Label description = new Label();
        description.getStyleClass().add("description-label");
        description.setWrapText(true);
        if (item instanceof Blog blog) {
            description.setText(blog.getSummary());
        }else {
            description.setText(item.getDescription());
        }

        //save and like button
        SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
        saveAndLikeButton.setShowCount(true);
        //TODO: Set the saveCount and likeCount from the item
        saveAndLikeButton.setSaveCount((int) (Math.random() * 100));
        saveAndLikeButton.setLikeCount((int) (Math.random() * 100));

        //link button
        Button linkButton = new Button();
        linkButton.setFocusTraversable(false);
        linkButton.getStyleClass().add("link-button");
        linkButton.setGraphic(new FontIcon(IkonUtil.link));
        LinkUtil.setLink(linkButton, ModelObjectTool.getModelLink(item));

        if (isSmall()) {
            HBox cellBox = new HBox();
            cellBox.getStyleClass().add("cell-box");

            HBox topBox = new HBox(mainTitle, new Spacer(), linkButton);
            topBox.getStyleClass().add("top-box");
            HBox.setHgrow(topBox, Priority.ALWAYS);

            HBox bottomBox = new HBox(saveAndLikeButton);
            bottomBox.getStyleClass().add("bottom-box");

            VBox rightBox = new VBox(topBox, description, bottomBox);
            rightBox.getStyleClass().add("right-box");
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            cellBox.getChildren().setAll(previewWrapper, rightBox);
            return cellBox;
        } else {
            VBox textContent = new VBox(mainTitle, description);
            textContent.getStyleClass().add("text-content");
            HBox.setHgrow(textContent, Priority.ALWAYS);

            HBox cellBox = new HBox(previewWrapper, textContent, saveAndLikeButton, linkButton);
            cellBox.getStyleClass().add("cell-box");
            return cellBox;
        }
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

    private final ObjectProperty<Ikon> icon = new SimpleObjectProperty<>(this, "icon");

    public Ikon getIcon() {
        return icon.get();
    }

    public ObjectProperty<Ikon> iconProperty() {
        return icon;
    }

    public void setIcon(Ikon icon) {
        this.icon.set(icon);
    }

    private final IntegerProperty initCount = new SimpleIntegerProperty(this, "initCount", 5);

    public int getInitCount() {
        return initCount.get();
    }

    public IntegerProperty initCountProperty() {
        return initCount;
    }

    public void setInitCount(int initCount) {
        this.initCount.set(initCount);
    }

    private final IntegerProperty loadIncrement = new SimpleIntegerProperty(this, "loadIncrement", 3);

    public int getLoadIncrement() {
        return loadIncrement.get();
    }

    public IntegerProperty loadIncrementProperty() {
        return loadIncrement;
    }

    public void setLoadIncrement(int loadIncrement) {
        this.loadIncrement.set(loadIncrement);
    }

    private final ListProperty<T> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<T> getItems() {
        return items.get();
    }

    public ListProperty<T> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<T> items) {
        this.items.set(items);
    }
}
