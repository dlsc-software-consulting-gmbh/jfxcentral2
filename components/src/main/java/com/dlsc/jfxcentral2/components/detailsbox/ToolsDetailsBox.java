package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ToolsDetailsBox extends DetailsBoxBase<Tool> {
    public ToolsDetailsBox() {
        getStyleClass().add("tools-details-box");
        setTitle("TOOLS");
        setIkon(IkonUtil.getModelIkon(Tool.class));
        setMaxItemsPerPage(3);
        setHomepageUrlProvider(detailsObject -> {
            String url = detailsObject.getHomepage();
            if (StringUtils.isBlank(url)) {
                url = detailsObject.getRepository();
            }
            return url;
        });
    }

    @Override
    protected List<Node> createActionButtons(Tool model) {
        return List.of(createDetailsButton(model), createHomepageButton(model));
    }
}
