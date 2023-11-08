package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;

import java.util.List;

public class DocumentationDetailsBox extends DetailsBoxBase<Documentation> {

    public DocumentationDetailsBox() {
        getStyleClass().add("documentation-details-box");
        setMaxItemsPerPage(3);
        setTitle("Documentation");
        setIkon(IkonUtil.getModelIkon(Documentation.class));
        setDetailUrlProvider(Documentation::getUrl);
    }

    @Override
    protected List<Node> createActionButtons(Documentation doc) {
        return List.of(createDetailsButton(doc));
    }

}
