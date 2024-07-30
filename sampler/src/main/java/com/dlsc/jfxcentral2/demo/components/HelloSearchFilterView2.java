package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

public class HelloSearchFilterView2 extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        SearchFilterView<Video> filterView = new SearchFilterView<>();

        //Add Filters
        filterView.getFilterGroups().addAll(
                new SearchFilterView.FilterGroup<>("Duration", List.of(
                        new SearchFilterView.FilterItem<>("All", video -> true),
                        new SearchFilterView.FilterItem<>("Short", video -> video.getMinutes() < 5),
                        new SearchFilterView.FilterItem<>("Medium", video -> video.getMinutes() >= 5 && video.getMinutes() < 10),
                        new SearchFilterView.FilterItem<>("Long", video -> video.getMinutes() >= 10)
                )),
                new SearchFilterView.FilterGroup<>("Platform", List.of(
                        new SearchFilterView.FilterItem<>("All", video -> true, true),
                        new SearchFilterView.FilterItem<>("Web", video -> video.getPlatform().toUpperCase().contains("Web".toUpperCase())),
                        new SearchFilterView.FilterItem<>("Desktop", video -> video.getPlatform().toUpperCase().contains("Desktop".toUpperCase())),
                        new SearchFilterView.FilterItem<>("Mobile", video -> video.getPlatform().toUpperCase().contains("Mobile".toUpperCase()))
                ))
        );

        //Add MobileSearchTextField
        filterView.setSearchPromptText("Search Videos");
        filterView.setOnSearch(text -> video -> StringUtils.isBlank(text) || StringUtils.containsIgnoreCase(video.getName(), text));

        //Add Sorter
        filterView.setSortGroup(new SearchFilterView.SortGroup<>("ORDER", List.of(
                new SearchFilterView.SortItem<>("Default", null),
                new SearchFilterView.SortItem<>("Duration ascending", Comparator.comparingInt(Video::getMinutes)),
                new SearchFilterView.SortItem<>("Duration descending", Comparator.comparingInt(Video::getMinutes).reversed()),
                new SearchFilterView.SortItem<>("Title from A to Z", Comparator.comparing(Video::getName)),
                new SearchFilterView.SortItem<>("Title from Z to A", Comparator.comparing(Video::getName).reversed())

        )));


        ObservableList<Video> items = initVideoItems();

        FilteredList<Video> filteredItems = new FilteredList<>(items);
        filteredItems.predicateProperty().bind(filterView.predicateProperty());

        SortedList<Video> sortedItems = new SortedList<>(filteredItems);
        sortedItems.comparatorProperty().bind(filterView.comparatorProperty());

        ListView<Video> listView = createListView();
        listView.setItems(sortedItems);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(filterView);
        borderPane.setCenter(listView);
        return new ScrollPane(borderPane);
    }

    private ListView<Video> createListView() {
        ListView<Video> listView = new ListView<>();
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Video item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label("Video Title:\t" + item.getName());
                    Label minutesLabel = new Label("Video Duration:\t" + item.getMinutes() + " minutes");
                    Label platformLabel = new Label("Video Platform:\t" + item.getPlatform());
                    setGraphic(new VBox(nameLabel, minutesLabel, platformLabel));
                }
            }
        });
        return listView;
    }

    private ObservableList<Video> initVideoItems() {
        ObservableList<Video> items = FXCollections.observableArrayList();
        Video video1 = new Video();
        video1.setName("JavaFX is awesome!");
        video1.setMinutes(12);
        video1.setPlatform("Web, Desktop");

        Video video2 = new Video();
        video2.setName("FXGL is fun!");
        video2.setMinutes(5);
        video2.setPlatform("Desktop");

        Video video3 = new Video();
        video3.setName("GemsFX | Dirk Lemmermann @ JFX Days 2020");
        video3.setMinutes(35);
        video3.setPlatform("Web, Desktop, Mobile");

        Video video4 = new Video();
        video4.setName("JavaFX real-world application: CoachFX | Dirk Lemmermann @ JFX Days 2020");
        video4.setMinutes(7);
        video4.setPlatform("Desktop");

        Video video5 = new Video();
        video5.setName("JavaFX real-world application: SenaptCRM | Dirk Lemmermann @ JFX Days 2020");
        video5.setMinutes(6);
        video5.setPlatform("Web");

        items.addAll(video1, video2, video3, video4, video5);
        return items;
    }

    @Override
    public String getSampleName() {
        return "Filter Test";
    }
}
