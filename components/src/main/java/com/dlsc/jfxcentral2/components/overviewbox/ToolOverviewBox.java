package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tool;
import javafx.beans.binding.Bindings;

public class ToolOverviewBox extends SimpleOverviewBox<Tool> {

    public ToolOverviewBox() {
        super();
    }

    public ToolOverviewBox(Tool data) {
        super(data);
        getStyleClass().add("tool-overview-box");
        baseURLProperty().bind(Bindings.createStringBinding(() -> DataRepository.getInstance().getRepositoryDirectoryURL() + "tools/" + getData().getId(), dataProperty()));
        markdownProperty().bind(DataRepository.getInstance().toolDescriptionProperty(data));
    }
}
