package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.filters.VideosFilterView;
import com.dlsc.jfxcentral2.components.gridview.VideoGridView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

public class VideosCategoryPage extends CategoryPageBase<Person> {

    public VideosCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Videos";
    }

    @Override
    public String description() {
        return "A curated list of videos covering JavaFX topics. Some videos might show tutorials, others present final applications or tips and tricks.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Videos", IkonUtil.getModelIkon(Video.class));

        // filter
        VideosFilterView filterView = new VideosFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // tile view
        VideoGridView videoGridView = new VideoGridView();
        videoGridView.sizeProperty().bind(sizeProperty());
        videoGridView.setItems(DataRepository.getInstance().getVideos());
        videoGridView.setDetailNodeProvider(this::createVideoNode);
        StripView stripView = new StripView(videoGridView);

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().addAll(filterView, stripView);

        return wrapContent(header, contentPane);
    }

    private Node createVideoNode(Video video) {
        if (WebAPI.isBrowser()) {
            HTMLView htmlView = new HTMLView();
            htmlView.setContent("<div width=\"100%\" \"height=100%\" background-color=\"powderblue\" class=\"yt\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + video.getId() + "\" allowfullscreen></iframe></div></body></html>\n");
            htmlView.parentProperty().addListener(it -> {
                Parent parent = htmlView.getParent();
                if (parent != null) {
                    htmlView.prefWidthProperty().bind((((Region) parent).widthProperty()));
                    htmlView.prefHeightProperty().bind(htmlView.prefWidthProperty().divide(16).multiply(9));

                    htmlView.minWidthProperty().bind((((Region) parent).widthProperty()));
                    htmlView.minHeightProperty().bind(htmlView.minWidthProperty().divide(16).multiply(9));

                    htmlView.maxWidthProperty().bind((((Region) parent).widthProperty()));
                    htmlView.maxHeightProperty().bind(htmlView.maxWidthProperty().divide(16).multiply(9));
                }
            });

            return htmlView;
        }

        WebView webView = new WebView();
        webView.setFocusTraversable(true);
        webView.parentProperty().addListener(it -> {
            Parent parent = webView.getParent();
            if (parent != null) {
                webView.prefWidthProperty().bind((((Region) parent).widthProperty()));
                webView.prefHeightProperty().bind(webView.prefWidthProperty().divide(16).multiply(9));

                webView.minWidthProperty().bind((((Region) parent).widthProperty()));
                webView.minHeightProperty().bind(webView.minWidthProperty().divide(16).multiply(9));

                webView.maxWidthProperty().bind((((Region) parent).widthProperty()));
                webView.maxHeightProperty().bind(webView.maxWidthProperty().divide(16).multiply(9));
            }
        });
        webView.getEngine().load("https://www.youtube.com/embed/" + video.getId());

        webView.sceneProperty().addListener(it -> {
            if (webView.getScene() == null) {
                webView.getEngine().loadContent("empty");
            }
        });

        return webView;
    }
}
