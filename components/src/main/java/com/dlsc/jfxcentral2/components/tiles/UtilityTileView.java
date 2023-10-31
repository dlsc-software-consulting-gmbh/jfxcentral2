package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.DevelopmentStatus;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import com.jpro.webapi.WebAPI;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.commons.lang3.StringUtils;

public class UtilityTileView extends SimpleTileView<Utility> {

    public UtilityTileView(Utility utility) {
        super(utility);
        DevelopmentStatus status = utility.getStatus();
        getStyleClass().addAll("utility-tile-view", status.toString().toLowerCase(), "tool-tile-view");

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(utility));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(utility));
        setAvatarType(AvatarView.Type.PLAIN);
        imageProperty().bind(ImageManager.getInstance().utilityImageProperty(utility));
        titleProperty().set(utility.getName());
        setDescription(utility.getDescription());

        updateLinkedObjectBadges();
    }

    protected void updateLinkedObjectBadges() {
        HBox developmentStatusBox = getLinkedObjectBox();
        developmentStatusBox.getChildren().clear();

        Utility data = getData();
        String strStatus = data.getStatus().toString().toLowerCase();
        Label label = new Label(StringUtils.capitalize(strStatus));
        label.getStyleClass().add("linked-badge");
        developmentStatusBox.getChildren().add(label);

        if (!data.isOnlineSupported()) {
            Label clientOnlyLabel = new Label("Client Only");
            clientOnlyLabel.getStyleClass().addAll("linked-badge", "client-only");
            developmentStatusBox.getChildren().add(clientOnlyLabel);
            if (WebAPI.isBrowser()) {
                setDisable(true);
            }
        }
    }

}
