package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Comparator;

public class PeopleFilterView extends SimpleSearchFilterView<Person> {
    public PeopleFilterView() {
        getStyleClass().add("people-filter-view");
        setSearchPromptText("Search for a JFX person");
        setComparator(Comparator.comparing(Person::getName));

        Label keysLabel = new Label("*KEY");
        keysLabel.getStyleClass().add("keys-label");
        keysLabel.setMinWidth(Region.USE_PREF_SIZE);

        Label rockStartBadge = new Label("Rockstar", new FontIcon(IkonUtil.rockstar));
        rockStartBadge.getStyleClass().add("badge");
        rockStartBadge.setMinWidth(Region.USE_PREF_SIZE);

        Label championBadge = new Label("Champion", new FontIcon(IkonUtil.champion));
        championBadge.getStyleClass().add("badge");
        championBadge.setMinWidth(Region.USE_PREF_SIZE);

        HBox badgeBox = new HBox(keysLabel, rockStartBadge, championBadge);
        badgeBox.getStyleClass().add("badge-box");

        getExtraNodes().add(badgeBox);

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search people ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------People Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });

    }
}
