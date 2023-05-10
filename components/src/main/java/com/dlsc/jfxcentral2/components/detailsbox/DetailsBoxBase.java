package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.details.DetailsObject;
import com.sandec.mdfx.MarkdownView;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;
import java.util.function.Consumer;

public class DetailsBoxBase<T extends DetailsObject> extends PaneBase {
    private static final boolean DEFAULT_SHOW_MAIN_PREVIEW = true;

    public DetailsBoxBase() {
        getStyleClass().add("details-box");
        itemsProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        HBox headerBox = createHeaderBox();

        PaginationControl2 pagination = new PaginationControl2();
        pagination.getStyleClass().add("pagination2");
        //pagination.setMaxPageIndicatorCount(3);
        int pageCount = itemsProperty().size() / getMaxItemsPerPage();
        if (itemsProperty().size() % getMaxItemsPerPage() != 0) {
            pageCount++;
        }
        StackPane page = new StackPane();
        page.getStyleClass().add("page");
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(pageIndex -> {
            VBox pageBox = createPageBox(pageIndex);
            page.getChildren().setAll(pageBox);
            return null;
        });
        pagination.managedProperty().bind(pagination.visibleProperty());
        pagination.visibleProperty().bind(pagination.pageCountProperty().greaterThan(1));

        VBox contentBox = new VBox(headerBox, page, pagination);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    private VBox createPageBox(Integer pageIndex) {
        int fromIndex = pageIndex * getMaxItemsPerPage();
        int toIndex = Math.min(fromIndex + getMaxItemsPerPage(), getItems().size());
        VBox pageBox = new VBox();
        pageBox.getStyleClass().add("page-box");
        for (int i = fromIndex; i < toIndex; i++) {
            T model = getItems().get(i);
            Node cell = createDetailsCell(model);
            if (i == 0) {
                cell.getStyleClass().add("first-cell");
            }
            if (i == toIndex - 1) {
                cell.getStyleClass().add("last-cell");
            }
            pageBox.getChildren().add(cell);
        }
        pageBox.setMinHeight(Region.USE_PREF_SIZE);
        return pageBox;
    }

    private Node createDetailsCell(T model) {
        VBox cellRight = new VBox();
        cellRight.getStyleClass().add("cell-right");
        HBox.setHgrow(cellRight, Priority.ALWAYS);

        // title box (title and action buttons)
        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");

        Label titleLabel = new Label(model.getTitle());
        if (model.getTitleImage() != null) {
            CustomImageView graphic = new CustomImageView();
            graphic.setImage(model.getTitleImage());
            titleLabel.setGraphic(graphic);
        }
        titleLabel.getStyleClass().add("title-label");

        titleBox.getChildren().addAll(titleLabel, new Spacer());

        List<Node> actionButtons = createActionButtons(model);
        if (actionButtons != null && !actionButtons.isEmpty()) {
            Pane actionButtonsPane = isSmall() ? new FlowPane() : new HBox();
            actionButtonsPane.getStyleClass().add("action-buttons-pane");
            for (int i = 0; i < actionButtons.size(); i++) {
                Node actionButton = actionButtons.get(i);
                actionButton.getStyleClass().addAll("action-button", "action-button-" + i);
                actionButtonsPane.getChildren().add(actionButton);
            }
            if (isSmall()) {
                cellRight.getChildren().add(actionButtonsPane);
            } else {
                titleBox.getChildren().add(actionButtonsPane);
            }
        }

        cellRight.getChildren().addAll(titleBox);

        // description
        DetailsObject.Description description = model.getDescription();
        if (description == null || !description.isMarkdown()) {
            Label descLabel = new Label(description == null ? "" : description.text());
            descLabel.setWrapText(true);
            descLabel.getStyleClass().add("description-label");
            cellRight.getChildren().add(descLabel);
        } else {
            MarkdownView descMD = new MarkdownView(description.text());
            descMD.getStyleClass().add("description-markdown");
            cellRight.getChildren().add(descMD);
        }

        // previews
        List<Image> previews = model.getPreviews();
        if (previews != null) {
            HBox previewsBox = new HBox();
            previewsBox.getStyleClass().add("previews-box");
            int max = isSmall() ? 2 : (model.getMainPreview() == null ? 7 : 6) - (isMedium() ? 1 : 0);
            for (int i = 0; i < previews.size() && i < max; i++) {
                Image preview = previews.get(i);
                StackPane previewNode = createImageWrapper(preview, false);
                previewsBox.getChildren().add(previewNode);
            }
            if (previews.size() > max) {
                Label moreLabel = new Label("+" + (previews.size() - max));
                moreLabel.getStyleClass().add("more-label");
                previewsBox.getChildren().add(moreLabel);
            }
            cellRight.getChildren().add(previewsBox);
        }

        // save and like
        HBox cellBottom = new HBox();
        cellBottom.getStyleClass().addAll("save-like-box", "cell-bottom");
        SaveAndLikeButton saveButton = new SaveAndLikeButton();
        saveButton.setSaveButtonSelected(model.isSaved());
        saveButton.setLikeButtonSelected(model.isLiked());
        cellBottom.getChildren().add(saveButton);

        HBox cellCenter = new HBox();
        cellCenter.getStyleClass().add("cell-center");

        Image mainPreview = model.getMainPreview();
        if (mainPreview != null) {
            Node cellLeft = createMainPreView(model);
            cellLeft.getStyleClass().add("cell-left");
            if (!isSmall()) {
                cellCenter.getChildren().add(cellLeft);
            } else {
                cellRight.getChildren().add(0, cellLeft);
            }
        }

        cellCenter.getChildren().add(cellRight);

        VBox cellContent = new VBox();
        cellContent.getStyleClass().addAll("cell-content", "details-cell");
        cellContent.getChildren().addAll(cellCenter, cellBottom);
        return cellContent;
    }

    protected List<Node> createActionButtons(T model) {
        return null;
    }

    protected Node createMainPreView(T model) {
        Image mainPreview = model.getMainPreview();
        StackPane mainPreviewPane = null;
        if (mainPreview != null) {
            mainPreviewPane = createImageWrapper(mainPreview, true);
            mainPreviewPane.getStyleClass().add("main-preview-wrapper");
            mainPreviewPane.managedProperty().bind(mainPreviewPane.visibleProperty());
            if (model.getMainPreviewDescription() != null) {
                Label mainPreviewDesc = new Label(model.getMainPreviewDescription(), new FontIcon());
                mainPreviewDesc.getStyleClass().add("main-preview-desc");
                mainPreviewPane.getChildren().add(mainPreviewDesc);
                StackPane.setAlignment(mainPreviewDesc, Pos.TOP_RIGHT);
            }
        }
        return mainPreviewPane;
    }

    protected StackPane createImageWrapper(Image image, boolean isMainPreview) {
        CustomImageView imageView = new CustomImageView();
        imageView.setImage(image);
        if (isMainPreview && isSmall()) {
            StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
        }

        StackPane imageWrapper = new StackPane(imageView);
        imageWrapper.getStyleClass().add("image-wrapper");
        return imageWrapper;
    }

    private HBox createHeaderBox() {
        Label headerLabel = new Label();
        headerLabel.getStyleClass().add("header-title");
        headerLabel.textProperty().bind(titleProperty());
        headerLabel.managedProperty().bind(headerLabel.visibleProperty());
        headerLabel.visibleProperty().bind(titleProperty().isNotEmpty());

        FontIcon headerIcon = new FontIcon();
        headerIcon.getStyleClass().add("header-icon");
        headerIcon.iconCodeProperty().bind(ikonProperty());
        headerIcon.managedProperty().bind(headerIcon.visibleProperty());
        headerIcon.visibleProperty().bind(ikonProperty().isNotNull());

        HBox headerBox = new HBox(headerLabel, new Spacer(), headerIcon);
        headerBox.managedProperty().bind(headerBox.visibleProperty());
        headerBox.visibleProperty().bind(headerLabel.visibleProperty().or(headerIcon.visibleProperty()));
        headerBox.getStyleClass().add("header-box");
        return headerBox;
    }

    protected Button createDetailsButton(T model) {
        Button detailsButton = new Button("DETAILS", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        detailsButton.getStyleClass().add("details-button");
        detailsButton.managedProperty().bind(detailsButton.visibleProperty());
        detailsButton.visibleProperty().bind(onDetailsProperty().isNotNull());
        detailsButton.setOnAction(event -> {
            if (onDetailsProperty().get() != null) {
                onDetailsProperty().get().accept(model);
            }
        });
        return detailsButton;
    }

    protected Button createHomepageButton(T model, ObjectProperty<Consumer<T>> onHomepageProperty) {
        Button homepageButton = new Button("HOMEPAGE", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        homepageButton.managedProperty().bind(homepageButton.visibleProperty());
        homepageButton.visibleProperty().bind(onHomepageProperty.isNotNull());
        homepageButton.setOnAction(event -> {
            if (onHomepageProperty.get() != null) {
                onHomepageProperty.get().accept(model);
            }
        });
        return homepageButton;
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "Header");

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

    private final IntegerProperty maxItemsPerPage = new SimpleIntegerProperty(this, "maxItemsPerPage", 3);

    public int getMaxItemsPerPage() {
        return maxItemsPerPage.get();
    }

    public IntegerProperty maxItemsPerPageProperty() {
        return maxItemsPerPage;
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage.set(maxItemsPerPage);
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

    private final ObjectProperty<Consumer<T>> onDetails = new SimpleObjectProperty<>(this, "onDetails");

    public Consumer<T> getOnDetails() {
        return onDetails.get();
    }

    public ObjectProperty<Consumer<T>> onDetailsProperty() {
        return onDetails;
    }

    public void setOnDetails(Consumer<T> onDetails) {
        this.onDetails.set(onDetails);
    }

}
