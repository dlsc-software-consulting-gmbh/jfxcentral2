package com.dlsc.jfxcentral2.demo.others;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShowcaseFilterValues extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        return new TextArea(findFilterValues());
    }

    private String findFilterValues() {
        //load data
        DataRepository.getInstance().loadData();
        ObservableList<RealWorldApp> appList = DataRepository.getInstance().getRealWorldApps();

        StringBuilder output = new StringBuilder();
        output.append("----------------------Domain----------------------\n");
        String domains = appList.stream()
                .flatMap(app -> Optional.ofNullable(app.getDomain()).stream()
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
        return "Showcase Filter Values";
    }
}
