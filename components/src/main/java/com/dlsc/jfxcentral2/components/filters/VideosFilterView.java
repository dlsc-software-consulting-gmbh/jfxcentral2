package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Video;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class VideosFilterView extends SimpleSearchFilterView<Video> {

    public VideosFilterView() {
        getStyleClass().add("videos-filter-view");
        setSortGroup(null);
        setSearchPromptText("Search for a video");

        getFilterGroups().setAll(
                new FilterGroup<>("Type", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Demo", item -> StringUtils.containsIgnoreCase(item.getType(), "Demo")),
                        new FilterItem<>("Keynote", item -> StringUtils.containsIgnoreCase(item.getType(), "Keynote")),
                        new FilterItem<>("Library", item -> StringUtils.containsIgnoreCase(item.getType(), "Library")),
                        new FilterItem<>("Presentation", item -> StringUtils.containsIgnoreCase(item.getType(), "Presentation")),
                        new FilterItem<>("Real World Application", item -> StringUtils.containsIgnoreCase(item.getType(), "Real World Application")),
                        new FilterItem<>("Roadmap", item -> StringUtils.containsIgnoreCase(item.getType(), "Roadmap")),
                        new FilterItem<>("Tips & Tricks", item -> StringUtils.containsIgnoreCase(item.getType(), "Tips & Tricks")),
                        new FilterItem<>("Tool", item -> StringUtils.containsIgnoreCase(item.getType(), "Tool")),
                        new FilterItem<>("Tutorial", item -> StringUtils.containsIgnoreCase(item.getType(), "Tutorial"))
                )),
                new FilterGroup<>("Event", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Devoxx", item -> StringUtils.containsIgnoreCase(item.getEvent(), "Devoxx")),
                        new FilterItem<>("JChampions Conf", item -> StringUtils.containsIgnoreCase(item.getEvent(), "JChampions Conf")),
                        new FilterItem<>("JFX Days 2018", item -> StringUtils.containsIgnoreCase(item.getEvent(), "JFX Days 2018")),
                        new FilterItem<>("JFX Days 2019", item -> StringUtils.containsIgnoreCase(item.getEvent(), "JFX Days 2019")),
                        new FilterItem<>("JFX Days 2020", item -> StringUtils.containsIgnoreCase(item.getEvent(), "JFX Days 2020")),
                        new FilterItem<>("JavaOne", item -> StringUtils.containsIgnoreCase(item.getEvent(), "JavaOne")),
                        new FilterItem<>("JavaOne 2014", item -> StringUtils.containsIgnoreCase(item.getEvent(), "JavaOne 2014")),
                        new FilterItem<>("Oracle CodeOne 2019", item -> StringUtils.containsIgnoreCase(item.getEvent(), "Oracle CodeOne 2019")),
                        new FilterItem<>("Oracle Learning", item -> StringUtils.containsIgnoreCase(item.getEvent(), "Oracle Learning"))
                )),
                new FilterGroup<>("Domain", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Aircraft design", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Aircraft design")),
                        new FilterItem<>("CAD", item -> StringUtils.containsIgnoreCase(item.getDomain(), "CAD")),
                        new FilterItem<>("CRM", item -> StringUtils.containsIgnoreCase(item.getDomain(), "CRM")),
                        new FilterItem<>("Charting", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Charting")),
                        new FilterItem<>("Charts", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Charts")),
                        new FilterItem<>("Custom Control", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Custom Control")),
                        new FilterItem<>("Demo", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Demo")),
                        new FilterItem<>("Desktop Search", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Desktop Search")),
                        new FilterItem<>("Education", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Education")),
                        new FilterItem<>("Energy", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Energy")),
                        new FilterItem<>("Gaming", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Gaming")),
                        new FilterItem<>("IDE", item -> StringUtils.containsIgnoreCase(item.getDomain(), "IDE")),
                        new FilterItem<>("Icons", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Icons")),
                        new FilterItem<>("Logistics", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Logistics")),
                        new FilterItem<>("Manufacturing", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Manufacturing")),
                        new FilterItem<>("Medical", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Medical")),
                        new FilterItem<>("Planning & Scheduling", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Planning & Scheduling")),
                        new FilterItem<>("Research", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Research")),
                        new FilterItem<>("Science", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Science")),
                        new FilterItem<>("Sports", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Sports")),
                        new FilterItem<>("Streaming", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Streaming")),
                        new FilterItem<>("Test Automation", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Test Automation")),
                        new FilterItem<>("Testing", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Testing")),
                        new FilterItem<>("Tool", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Tool")),
                        new FilterItem<>("Tourism", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Tourism"))


                ))
        );

        setOnSearch(text -> video -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(video.getName(), text)
                || StringUtils.containsIgnoreCase(video.getDescription(), text)
        );

    }

}
