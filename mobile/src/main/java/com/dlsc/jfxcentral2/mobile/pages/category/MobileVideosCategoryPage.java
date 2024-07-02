package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.VideoTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileVideosCategoryPage extends MobileCategoryPageBase<Video> {

    public MobileVideosCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "Videos";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Video.class);
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a video";
    }

    @Override
    protected Callback<Video, Node> getDetailNodeProvider() {
        return video -> VideoViewFactory.createVideoViewNode(video, true);
    }

    @Override
    protected Callback<Video, TileViewBase<Video>> getTileViewProvider() {
        return video -> {
            VideoTileView tileView = new VideoTileView(video);
            tileView.getButton1().setOnAction(evt -> tileView.getOnShowDetailNode().run());
            return tileView;
        };
    }

    @Override
    protected ObservableList<Video> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getVideos());
    }
}
