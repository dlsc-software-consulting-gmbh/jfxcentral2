package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.model.filter.VideoDate;
import com.dlsc.jfxcentral2.model.filter.VideoLength;
import com.dlsc.jfxcentral2.model.filter.VideoType;

import java.util.Comparator;

public class VideosFilterView extends SearchFilterView<Video>{

    public VideosFilterView() {
        getStyleClass().add("videos-filter-view");

        setComparator(Comparator.comparing(Video::getName));

        getFilterItems().setAll(
                new FilterItem<>("LENGTH", VideoLength.class, VideoLength.FIVE_TO_FIFTEEN),
                new FilterItem<>("DATE", VideoDate.class, VideoDate.ALL),
                new FilterItem<>("TYPE", VideoType.class, VideoType.INTERVIEW)
        );

//        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }

}
