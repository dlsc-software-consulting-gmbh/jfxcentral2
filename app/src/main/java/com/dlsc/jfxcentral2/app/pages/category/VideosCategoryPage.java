package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.filters.VideosFilterView;
import com.dlsc.jfxcentral2.components.gridview.VideoGridView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

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
        CategoryHeader header = createCategoryHeader("Videos", MaterialDesign.MDI_VIDEO);

        // filter
        VideosFilterView filterView = new VideosFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // tile view
        VideoGridView videoTileView = new VideoGridView();
        videoTileView.sizeProperty().bind(sizeProperty());
        videoTileView.setItems(DataRepository.getInstance().getVideos());

        StripView stripView = new StripView(videoTileView);

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().addAll(filterView, stripView);

        return wrapContent(header, contentPane);
    }
}
