package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Image;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.function.Consumer;

public class LibrariesDetailsBox extends DetailsBoxBase<Library> {

    public LibrariesDetailsBox() {
        getStyleClass().add("libraries-details-box");
        setTitle("LIBRARIES");
        setIkon(IkonUtil.library);
        setMaxItemsPerPage(3);
        libraryInfoProperty().addListener(it -> layoutBySize());

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getName());
        });

    }

    @Override
    protected Node createPreviewsBox(Library library) {
        LibraryInfo info = getLibraryInfo();
        if (info != null && library != null) {
            List<Image> images = info.getImages();
            int max = isSmall() ? 2 : 7 - (isMedium() ? 1 : 0);
            HBox previewsBox = new HBox();
            for (int i = 0; i < images.size() && i < max; i++) {
                com.dlsc.jfxcentral.data.model.Image image = images.get(i);
                CustomImageView imageView = new CustomImageView();
                if (isSmall()) {
                    StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
                }
                StackPane imageWrapper = new StackPane(imageView);
                imageWrapper.getStyleClass().add("image-wrapper");
                imageView.imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library, image.getPath()));
                previewsBox.getChildren().add(imageWrapper);
            }

            if (images.size() > max) {
                Label moreLabel = new Label("+" + (images.size() - max));
                moreLabel.getStyleClass().add("more-label");
                previewsBox.getChildren().add(moreLabel);
            }
            return previewsBox;
        }
        return null;
    }

    @Override
    protected List<Node> createActionButtons(Library model) {
        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()));
    }

    private final ObjectProperty<Consumer<Library>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<Library> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<Library>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<Library> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

    private final ObjectProperty<LibraryInfo> libraryInfo = new SimpleObjectProperty<>(this, "libraryInfo");

    public LibraryInfo getLibraryInfo() {
        return libraryInfo.get();
    }

    public ObjectProperty<LibraryInfo> libraryInfoProperty() {
        return libraryInfo;
    }

    public void setLibraryInfo(LibraryInfo libraryInfo) {
        this.libraryInfo.set(libraryInfo);
    }
}
