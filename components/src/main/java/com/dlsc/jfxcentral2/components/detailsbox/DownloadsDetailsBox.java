package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.details.DownloadsDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;
import java.util.function.Consumer;

public class DownloadsDetailsBox extends DetailsBoxBase<DownloadsDetailsObject> {
    public DownloadsDetailsBox() {
        getStyleClass().add("downloads-details-box");
        setTitle("DOWNLOADS");
        setIkon(MaterialDesign.MDI_DOWNLOAD);
        setMaxItemsPerPage(3);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getTitle());
        });

        setOnDownload(detailsObject -> {
            System.out.println("On Download: " + detailsObject.getTitle());
        });
    }

    @Override
    protected List<Node> createActionButtons(DownloadsDetailsObject model) {
        Button downloadButton = new Button("DOWNLOADS", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        downloadButton.managedProperty().bind(downloadButton.visibleProperty());
        downloadButton.visibleProperty().bind(onDownloadProperty().isNotNull());
        downloadButton.setOnAction(evt -> {
            if (getOnDownload() != null) {
                getOnDownload().accept(model);
            }
        });

        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()), downloadButton);
    }

    private final ObjectProperty<Consumer<DownloadsDetailsObject>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<DownloadsDetailsObject> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<DownloadsDetailsObject>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<DownloadsDetailsObject> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

    private final ObjectProperty<Consumer<DownloadsDetailsObject>> onDownload = new SimpleObjectProperty<>(this, "onDownload");

    public Consumer<DownloadsDetailsObject> getOnDownload() {
        return onDownload.get();
    }

    public ObjectProperty<Consumer<DownloadsDetailsObject>> onDownloadProperty() {
        return onDownload;
    }

    public void setOnDownload(Consumer<DownloadsDetailsObject> onDownload) {
        this.onDownload.set(onDownload);
    }
}
