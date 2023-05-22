package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Consumer;

public class DownloadsDetailsBox extends DetailsBoxBase<Download> {

    public DownloadsDetailsBox() {
        getStyleClass().add("downloads-details-box");
        setTitle("DOWNLOADS");
        setIkon(IkonUtil.download);
        setMaxItemsPerPage(3);
    }

    @Override
    protected List<Node> createActionButtons(Download model) {
        Button downloadButton = new Button("DOWNLOADS", new FontIcon(IkonUtil.link));
        downloadButton.managedProperty().bind(downloadButton.visibleProperty());
        downloadButton.visibleProperty().bind(onDownloadProperty().isNotNull());
        downloadButton.setMinWidth(Region.USE_PREF_SIZE);
        downloadButton.setOnAction(evt -> {
            if (getOnDownload() != null) {
                getOnDownload().accept(model);
            }
        });

        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()), downloadButton);
    }

    private final ObjectProperty<Consumer<Download>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<Download> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<Download>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<Download> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

    private final ObjectProperty<Consumer<Download>> onDownload = new SimpleObjectProperty<>(this, "onDownload");

    public Consumer<Download> getOnDownload() {
        return onDownload.get();
    }

    public ObjectProperty<Consumer<Download>> onDownloadProperty() {
        return onDownload;
    }

    public void setOnDownload(Consumer<Download> onDownload) {
        this.onDownload.set(onDownload);
    }
}
