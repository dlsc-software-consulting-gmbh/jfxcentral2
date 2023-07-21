package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PageUtil;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class DownloadsDetailsBox extends DetailsBoxBase<Download> {

    public DownloadsDetailsBox() {
        getStyleClass().add("downloads-details-box");
        setTitle("DOWNLOADS");
        setIkon(IkonUtil.getModelIkon(Download.class));
        setMaxItemsPerPage(3);
    }

    @Override
    protected List<Node> createActionButtons(Download download) {
        Button downloadButton = new Button("DOWNLOADS", new FontIcon(IkonUtil.link));
        downloadButton.setMinWidth(Region.USE_PREF_SIZE);
        LinkUtil.setLink(downloadButton, PageUtil.getLink(download));
        return List.of(createDetailsButton(download), createHomepageButton(download), downloadButton);
    }
}
