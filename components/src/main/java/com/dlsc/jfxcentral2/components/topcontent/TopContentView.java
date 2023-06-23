package com.dlsc.jfxcentral2.components.topcontent;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
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
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class TopContentView<T extends ModelObject> extends PaneBase {

    private final VBox centerBox;

    public TopContentView() {
        getStyleClass().add("top-content-view");

        Header header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(iconProperty());

        centerBox = new VBox();
        centerBox.getStyleClass().add("center-box");

        Button loadMoreButton = new Button("LOAD MORE");
        loadMoreButton.getStyleClass().addAll("fill-button", "load-more-button");

        VBox contentBox = new VBox(header, centerBox, loadMoreButton);
        contentBox.getStyleClass().add("content-box");

        getChildren().setAll(contentBox);

        loadMoreButton.setOnAction(evt -> {
            getItems().stream().skip(centerBox.getChildren().size()).limit(getLoadIncrement()).forEach(item -> {
                centerBox.getChildren().add(createItemCell(item));
            });
            evt.consume();
        });

        loadMoreButton.textProperty().bind(Bindings.when(loadMoreButton.disableProperty()).then("NO MORE ITEMS").otherwise("LOAD MORE"));
        loadMoreButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> getItems().size() <= centerBox.getChildren().size(), itemsProperty(), centerBox.getChildren()));

        itemsProperty().addListener((InvalidationListener) it -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        centerBox.getChildren().clear();

        //TODO: Sort the items by saveCount + likeCount
        //getItems().sort((o1, o2) ->));

        getItems().stream().limit(getInitCount()).forEach(item -> {
            centerBox.getChildren().add(createItemCell(item));
        });

    }

    protected Node createItemCell(T item) {
        if (isSmall()) {
            HBox cellBox = new HBox();
            cellBox.getStyleClass().add("cell-box");

            CustomImageView preview = new CustomImageView();
            preview.imageProperty().bind(ModelObjectTool.getModelPreviewImageProperty(item, false));

            Label mainTitle = new Label();
            mainTitle.getStyleClass().add("main-title");
            mainTitle.setText(item.getName());

            Button linkButton = new Button();
            linkButton.getStyleClass().add("link-button");
            linkButton.setGraphic(new FontIcon(IkonUtil.link));

            HBox topBox = new HBox(mainTitle, new Spacer(), linkButton);
            topBox.getStyleClass().add("top-box");
            HBox.setHgrow(topBox, Priority.ALWAYS);

            Label description = new Label();
            description.getStyleClass().add("description-label");
            description.setWrapText(true);
            description.setText(item.getDescription());

            SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
            saveAndLikeButton.setShowCount(true);
            //TODO: Set the saveCount and likeCount from the item
            saveAndLikeButton.setSaveCount((int) (Math.random() * 100));
            saveAndLikeButton.setLikeCount((int) (Math.random() * 100));

            HBox bottomBox = new HBox(saveAndLikeButton);
            bottomBox.getStyleClass().add("bottom-box");

            VBox rightBox = new VBox(topBox, description, bottomBox);
            rightBox.getStyleClass().add("right-box");
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            cellBox.getChildren().setAll(preview, rightBox);
            return cellBox;
        } else {
            CustomImageView preview = new CustomImageView();
            preview.imageProperty().bind(ModelObjectTool.getModelPreviewImageProperty(item, false));

            Label mainTitle = new Label();
            mainTitle.getStyleClass().add("main-title");
            mainTitle.setText(item.getName());

            Label description = new Label();
            description.getStyleClass().add("description-label");
            description.setText(item.getDescription());

            SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
            saveAndLikeButton.setShowCount(true);
            //TODO: Set the saveCount and likeCount from the item
            saveAndLikeButton.setSaveCount((int) (Math.random() * 100));
            saveAndLikeButton.setLikeCount((int) (Math.random() * 100));

            Button linkButton = new Button();
            linkButton.getStyleClass().add("link-button");
            linkButton.setGraphic(new FontIcon(IkonUtil.link));
            LinkUtil.setLink(linkButton, ModelObjectTool.getModelLink(item));

            VBox textContent = new VBox(mainTitle, description);
            textContent.getStyleClass().add("text-content");
            HBox.setHgrow(textContent, Priority.ALWAYS);

            HBox cellBox = new HBox(preview, textContent, saveAndLikeButton, linkButton);
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
