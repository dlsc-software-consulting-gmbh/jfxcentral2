package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.VideoDate;
import com.dlsc.jfxcentral2.model.VideoLength;
import com.dlsc.jfxcentral2.model.VideoType;

public class VideosFilterView extends SearchFilterView {

    public VideosFilterView() {
        getStyleClass().add("videos-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("LENGTH", VideoLength.class, VideoLength.FIVE_TO_FIFTEEN),
                new FilterItem<>("DATE", VideoDate.class, VideoDate.ALL),
                new FilterItem<>("TYPE", VideoType.class, VideoType.INTERVIEW)
        );

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }

}
