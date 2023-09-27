package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.PaneBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LibraryEnsembleBox extends PaneBase {

    public LibraryEnsembleBox(Library library) {
        getStyleClass().addAll("overview-box", "library-ensemble-box");

        Header headerBox = new Header();
        headerBox.setTitle("ENSEMBLE / ONLINE DEMOS");
        headerBox.setIcon(MaterialDesign.MDI_EARTH);

        ImageView imageView = new ImageView(Objects.requireNonNull(LibraryEnsembleBox.class.getResource("jpro-logo.png")).toExternalForm());
        imageView.setFitHeight(48);
        imageView.setPreserveRatio(true);

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.setMinHeight(Region.USE_PREF_SIZE);

        HBox.setHgrow(markdownView, Priority.ALWAYS);

        String url = "https://www.jfx-ensemble.com/?page=project/" + URLEncoder.encode(library.getName(), StandardCharsets.UTF_8);
        markdownView.setMdString("Online demos are available for this library on the JFX-Ensemble website. These demos can be [run in the browser](" + url + ") via JPro (free for open source projects).");
        LinkUtil.setExternalLink(markdownView, url, library.getName());
        LinkUtil.setExternalLink(imageView, url, library.getName());

        HBox bodyBox = new HBox(imageView, markdownView);
        bodyBox.getStyleClass().add("body-box");

        VBox contentBox = new VBox(headerBox, bodyBox);
        contentBox.getStyleClass().add("content-box");

        getChildren().add(contentBox);
    }
}
