package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Library;
import org.apache.commons.lang3.StringUtils;

public class LibrariesFilterView extends SimpleModelObjectSearchFilterView<Library> {

    public LibrariesFilterView() {
        getStyleClass().add("libraries-filter-view");
        setSearchPromptText("Search for a library ...");

        setOnSearch(text -> library -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(library.getName(), text)
                || StringUtils.containsIgnoreCase(library.getDescription(), text));

    }
}
