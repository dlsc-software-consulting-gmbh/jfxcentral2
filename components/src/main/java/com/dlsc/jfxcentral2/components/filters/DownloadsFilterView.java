package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Download;
import org.apache.commons.lang3.StringUtils;

public class DownloadsFilterView extends SimpleSearchFilterView<Download> {

    public DownloadsFilterView() {
        getStyleClass().add("downloads-filter-view");
        setSearchPromptText("Search for a download");

        setOnSearch(text -> download -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(download.getName(), text)
                || StringUtils.containsIgnoreCase(download.getDescription(), text));

    }
}
