package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;

public class ToolTileView extends SimpleTileView<Tool> {

    public ToolTileView(Tool tool) {
        this();
        setData(tool);
    }

    public ToolTileView() {
        getStyleClass().add("tool-tile-view");

        dataProperty().addListener((ob, ov, tool) -> {
            saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(tool));
            saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(tool));

            //add image for testing
            imageProperty().bind(Bindings.createObjectBinding(() -> new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/tool-tile-view.png").toExternalForm())));

            //imageProperty().bind(ImageManager.getInstance().toolImageProperty(tool));
            titleProperty().set(tool.getName());
            descriptionProperty().set(tool.getDescription());
        });
    }

}
