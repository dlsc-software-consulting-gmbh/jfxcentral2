package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LearnPagination<T extends Learn> extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "learn-pagination";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    private final PrettyScrollPane scrollPane = new PrettyScrollPane();

    public LearnPagination() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Label title = new Label();
        // title.getStyleClass().add("title");
        // title.setWrapText(true);
        // title.textProperty().bind(itemProperty().map(item -> item == null ? "" : item.getName()));

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("date-label");
        dateLabel.textProperty().bind(itemProperty().map(item -> item == null ? "" : DATE_FORMATTER.format(item.getCreatedOn())));

        Spacer spacer = new Spacer();

        HBox authorBox = new HBox();
        authorBox.getStyleClass().add("author-box");
        itemProperty().addListener((obs, oldItem, newItem) -> {
            // update author box
            authorBox.getChildren().clear();
            newItem.getPersonIds().forEach(id -> DataRepository2.getInstance().getPersonById(id)
                    .ifPresent(person -> {
                        AvatarView avatarView = new AvatarView();
                        avatarView.setTooltip(new Tooltip(person.getName()));
                        avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
                        MobileLinkUtil.setLink(avatarView, ModelObjectTool.getModelLink(person));
                        authorBox.getChildren().add(avatarView);
                    }));
            authorBox.getChildren().addAll(spacer, dateLabel);

            // scroll to top
            scrollPane.setVvalue(0);
        });

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.baseURLProperty().bind(baseURLProperty());
        markdownView.mdStringProperty().bind(Bindings.createStringBinding(() -> {
            T currentItem = getItem();
            if (getBaseURL() == null || currentItem == null) {
                return "";
            }

            if (currentItem instanceof LearnJavaFX data) {
                return DataRepository2.getInstance().getLearnJavaFXReadMe(data);
            } else if (currentItem instanceof LearnMobile data) {
                return DataRepository2.getInstance().getLearnMobileReadMe(data);
            } else if (currentItem instanceof LearnRaspberryPi data) {
                return DataRepository2.getInstance().getLearnRaspberryPiReadMe(data);
            }
            return "";
        }, itemProperty(), baseURLProperty()));

        Button previousButton = new Button();
        previousButton.getStyleClass().addAll("nav-button", "previous-button");
        previousButton.setGraphic(new FontIcon(MaterialDesign.MDI_CHEVRON_LEFT));
        previousButton.disableProperty().bind(index.isEqualTo(0));
        previousButton.setOnAction(e -> setIndex(getIndex() - 1));

        Button nextButton = new Button();
        nextButton.getStyleClass().addAll("nav-button", "next-button");
        nextButton.setGraphic(new FontIcon(MaterialDesign.MDI_CHEVRON_RIGHT));
        nextButton.disableProperty().bind(Bindings.createBooleanBinding(() -> getIndex() >= items.size() - 1, indexProperty(), getItems()));
        nextButton.setOnAction(e -> setIndex(getIndex() + 1));

        Label pageLabel = new Label();
        pageLabel.getStyleClass().add("page-label");
        pageLabel.textProperty().bind(Bindings.concat(indexProperty().add(1), " / ", Bindings.size(items)));

        HBox navBox = new HBox(previousButton, pageLabel, nextButton);
        navBox.getStyleClass().add("nav-box");
        pageLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pageLabel, Priority.ALWAYS);

        VBox contentBox = new VBox(authorBox, markdownView);
        contentBox.getStyleClass().add("content-box");

        scrollPane.setContent(contentBox);
        scrollPane.getStyleClass().add("mobile");
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(scrollPane, navBox);

        itemProperty().bind(Bindings.createObjectBinding(() -> {
            int currentIndex = getIndex();
            int size = getItems().size();
            if (currentIndex < 0 || currentIndex >= size) {
                return null;
            }

            return getItems().get(currentIndex);
        }, indexProperty(), getItems()));
    }

    public final void setSelectedItem(T item) {
        setIndex(getItems().indexOf(item));
    }

    // learn items

    private final ObservableList<T> items = FXCollections.observableArrayList();

    public ObservableList<T> getItems() {
        return items;
    }

    // index

    private final IntegerProperty index = new SimpleIntegerProperty(this, "index", -1);

    public final IntegerProperty indexProperty() {
        return index;
    }

    public final int getIndex() {
        return index.get();
    }

    public final void setIndex(int index) {
        this.index.set(index);
    }

    // item

    private final ReadOnlyObjectWrapper<T> item = new ReadOnlyObjectWrapper<>(this, "item");

    public final T getItem() {
        return item.get();
    }

    public final ReadOnlyObjectWrapper<T> itemProperty() {
        return item;
    }

    private void setItem(T item) {
        this.item.set(item);
    }

    // base url

    private final StringProperty baseURL = new SimpleStringProperty(this, "baseURL");

    public String getBaseURL() {
        return baseURL.get();
    }

    public StringProperty baseURLProperty() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL.set(baseURL);
    }

}