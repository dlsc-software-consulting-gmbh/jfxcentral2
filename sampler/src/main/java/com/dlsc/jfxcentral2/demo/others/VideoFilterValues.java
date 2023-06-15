package com.dlsc.jfxcentral2.demo.others;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class VideoFilterValues extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        return new TextArea(findFilterValues());
    }

    private String findFilterValues() {
        //load data
        DataRepository.getInstance().loadData();
        ObservableList<Video> videoList = DataRepository.getInstance().getVideos();

        StringBuilder output = new StringBuilder();
        output.append("----------------------Types----------------------\n");
        String types = videoList.stream()
                .flatMap(video -> Optional.ofNullable(video.getType()).stream()
                        .flatMap(type -> Arrays.stream(type.split(","))))
                .map(String::trim)
                .sorted()
                .distinct()
                //.map(item -> String.format("new FilterItem<>(\"%s\", item -> StringUtils.equalsIgnoreCase(item.getType(), \"%s\"))", item, item))
                .map(item -> String.format("new FilterItem<>(\"%s\", item -> StringUtils.containsIgnoreCase(item.getType(), \"%s\"))", item, item))
                .collect(Collectors.joining(",\n"));
        output.append(types);

        output.append("\n\n\n----------------------Events----------------------\n");
        String events = videoList.stream()
                .flatMap(video -> Optional.ofNullable(video.getEvent()).stream()
                        .flatMap(event -> Arrays.stream(event.split(","))))
                .map(String::trim)
                .sorted()
                .distinct()
                //.map(item -> String.format("new FilterItem<>(\"%s\", item -> StringUtils.equalsIgnoreCase(item.getEvent(), \"%s\"))", item, item))
                .map(item -> String.format("new FilterItem<>(\"%s\", item -> StringUtils.containsIgnoreCase(item.getEvent(), \"%s\"))", item, item))
                .collect(Collectors.joining(",\n"));
        output.append(events);

        output.append("\n\n\n----------------------Domain----------------------\n");
        String domains = videoList.stream()
                .flatMap(video -> Optional.ofNullable(video.getDomain()).stream()
                        .flatMap(domain -> Arrays.stream(domain.split(","))))
                .map(String::trim)
                .sorted()
                .distinct()
                //.map(item -> String.format("new FilterItem<>(\"%s\", item -> StringUtils.equalsIgnoreCase(item.getDomain(), \"%s\"))", item, item))
                .map(item -> String.format("new FilterItem<>(\"%s\", item -> StringUtils.containsIgnoreCase(item.getDomain(), \"%s\"))", item, item))
                .collect(Collectors.joining(",\n"));
        output.append(domains).append("\n");


        return output.toString();
    }

    @Override
    public String getSampleName() {
        return "Video Filter Values";
    }
}
