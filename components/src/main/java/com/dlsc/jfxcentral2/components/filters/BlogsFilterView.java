package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Blog;
import org.apache.commons.lang3.StringUtils;

public class BlogsFilterView extends SimpleSearchFilterView<Blog> {

    public BlogsFilterView() {
        getStyleClass().add("blogs-filter-view");
        setSearchPromptText("Search for a blog");

        setOnSearch(text -> blog -> StringUtils.isBlank(text) || StringUtils.containsIgnoreCase(blog.getName(), text));

    }
}
