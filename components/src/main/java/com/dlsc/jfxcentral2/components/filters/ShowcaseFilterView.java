package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ShowcaseFilterView extends SimpleSearchFilterView<RealWorldApp> {

    public ShowcaseFilterView() {
        getStyleClass().add("showcases-filter-view");
        setSearchPromptText("Search for a JFX APP");

        getFilterGroups().setAll(
                //new FilterGroup<>("TYPE", List.of(
                //        new FilterItem<>("ALL", item -> true, true),
                //        new FilterItem<>("Tools", item -> true),
                //        new FilterItem<>("Business", item -> true),
                //        new FilterItem<>("Other", item -> true)
                //)),
                new FilterGroup<>("DOMAIN", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Agile Project Management", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Agile Project Management")),
                        new FilterItem<>("Aircraft design", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Aircraft design")),
                        new FilterItem<>("Architecture", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Architecture")),
                        new FilterItem<>("Aviation", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Aviation")),
                        new FilterItem<>("Business Registry", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Business Registry")),
                        new FilterItem<>("CRM", item -> StringUtils.containsIgnoreCase(item.getDomain(), "CRM")),
                        new FilterItem<>("Citation management", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Citation management")),
                        new FilterItem<>("Civil Engineering", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Civil Engineering")),
                        new FilterItem<>("Conferences", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Conferences")),
                        new FilterItem<>("Cryptocurrencies", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Cryptocurrencies")),
                        new FilterItem<>("Desktop Search Tool", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Desktop Search Tool")),
                        new FilterItem<>("E-Learning", item -> StringUtils.containsIgnoreCase(item.getDomain(), "E-Learning")),
                        new FilterItem<>("Education", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Education")),
                        new FilterItem<>("Engineering", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Engineering")),
                        new FilterItem<>("IT / Science / Software development", item -> StringUtils.containsIgnoreCase(item.getDomain(), "IT / Science / Software development")),
                        new FilterItem<>("Image management", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Image management")),
                        new FilterItem<>("Justice", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Justice")),
                        new FilterItem<>("Logistics", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Logistics")),
                        new FilterItem<>("MacroPads", item -> StringUtils.containsIgnoreCase(item.getDomain(), "MacroPads")),
                        new FilterItem<>("Manufacturing Execution Systems", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Manufacturing Execution Systems")),
                        new FilterItem<>("Medical", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Medical")),
                        new FilterItem<>("Music", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Music")),
                        new FilterItem<>("Office Integration", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Office Integration")),
                        new FilterItem<>("PDF editing", item -> StringUtils.containsIgnoreCase(item.getDomain(), "PDF editing")),
                        new FilterItem<>("Planning & Scheduling", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Planning & Scheduling")),
                        new FilterItem<>("Research", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Research")),
                        new FilterItem<>("Resource Scheduling", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Resource Scheduling")),
                        new FilterItem<>("Scheduling", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Scheduling")),
                        new FilterItem<>("Sports", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Sports")),
                        new FilterItem<>("System Administration", item -> StringUtils.containsIgnoreCase(item.getDomain(), "System Administration")),
                        new FilterItem<>("Team Management", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Team Management")),
                        new FilterItem<>("Television subtitling", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Television subtitling")),
                        new FilterItem<>("Tourism", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Tourism")),
                        new FilterItem<>("Trading", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Trading")),
                        new FilterItem<>("Transport Optimization", item -> StringUtils.containsIgnoreCase(item.getDomain(), "Transport Optimization"))
                ))
        );

        setOnSearch(text -> app -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(app.getName(), text)
                || StringUtils.containsIgnoreCase(app.getDescription(), text));

    }
}
