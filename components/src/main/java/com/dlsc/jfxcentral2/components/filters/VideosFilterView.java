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
                        new FilterItem<>("Demo", item -> StringUtils.equalsIgnoreCase(item.getType(), "Demo")),
                        new FilterItem<>("Keynote", item -> StringUtils.equalsIgnoreCase(item.getType(), "Keynote")),
                        new FilterItem<>("Library", item -> StringUtils.equalsIgnoreCase(item.getType(), "Library")),
                        new FilterItem<>("Presentation", item -> StringUtils.equalsIgnoreCase(item.getType(), "Presentation")),
                        new FilterItem<>("Real World Application", item -> StringUtils.equalsIgnoreCase(item.getType(), "Real World Application")),
                        new FilterItem<>("Roadmap", item -> StringUtils.equalsIgnoreCase(item.getType(), "Roadmap")),
                        new FilterItem<>("Tips & Tricks", item -> StringUtils.equalsIgnoreCase(item.getType(), "Tips & Tricks")),
                        new FilterItem<>("Tool", item -> StringUtils.equalsIgnoreCase(item.getType(), "Tool")),
                        new FilterItem<>("Tutorial", item -> StringUtils.equalsIgnoreCase(item.getType(), "Tutorial"))
                )),
                new FilterGroup<>("Event", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        //new FilterItem<>("", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "")),
                        new FilterItem<>("Devoxx", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "Devoxx")),
                        new FilterItem<>("JChampions Conf", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "JChampions Conf")),
                        new FilterItem<>("JFX Days 2018", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "JFX Days 2018")),
                        new FilterItem<>("JFX Days 2019", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "JFX Days 2019")),
                        new FilterItem<>("JFX Days 2020", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "JFX Days 2020")),
                        new FilterItem<>("JavaOne", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "JavaOne")),
                        new FilterItem<>("JavaOne 2014", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "JavaOne 2014")),
                        new FilterItem<>("Oracle CodeOne 2019", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "Oracle CodeOne 2019")),
                        new FilterItem<>("Oracle Learning", item -> StringUtils.equalsIgnoreCase(item.getEvent(), "Oracle Learning"))
                )),
                new FilterGroup<>("Domain", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        //new FilterItem<>("", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "")),
                        //new FilterItem<>("46", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "46")),
                        new FilterItem<>("Aircraft design", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Aircraft design")),
                        new FilterItem<>("CAD", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "CAD")),
                        new FilterItem<>("CRM", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "CRM")),
                        new FilterItem<>("Charting", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Charting")),
                        new FilterItem<>("Charts", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Charts")),
                        new FilterItem<>("Custom Control", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Custom Control")),
                        new FilterItem<>("Custom Controls", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Custom Controls")),
                        new FilterItem<>("Demo", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Demo")),
                        new FilterItem<>("Desktop Search", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Desktop Search")),
                        new FilterItem<>("Education", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Education")),
                        new FilterItem<>("Energy", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Energy")),
                        new FilterItem<>("Gaming", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Gaming")),
                        new FilterItem<>("IDE", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "IDE")),
                        new FilterItem<>("Icons", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Icons")),
                        new FilterItem<>("Logistics", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Logistics")),
                        new FilterItem<>("Manufacturing", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Manufacturing")),
                        new FilterItem<>("Medical", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Medical")),
                        new FilterItem<>("Planning & Scheduling", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Planning & Scheduling")),
                        new FilterItem<>("Research", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Research")),
                        new FilterItem<>("Science", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Science")),
                        new FilterItem<>("Sports", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Sports")),
                        new FilterItem<>("Streaming", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Streaming")),
                        new FilterItem<>("Test Automation", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Test Automation")),
                        new FilterItem<>("Testing", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Testing")),
                        new FilterItem<>("Tool", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Tool")),
                        new FilterItem<>("Tourism", item -> StringUtils.equalsIgnoreCase(item.getDomain(), "Tourism"))


                ))
        );

        setOnSearch(text -> video -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(video.getName(), text)
                || StringUtils.containsIgnoreCase(video.getDescription(), text)
        );

    }

}
