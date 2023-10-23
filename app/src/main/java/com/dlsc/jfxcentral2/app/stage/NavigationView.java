package com.dlsc.jfxcentral2.app.stage;

import com.dlsc.jfxcentral2.utils.OSUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import one.jpro.platform.routing.HistoryEntry;
import one.jpro.platform.routing.sessionmanager.SessionManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Objects;

public class NavigationView extends HBox {

    private final SessionManager sessionManager;

    public NavigationView(SessionManager sessionManager) {
        this.sessionManager = Objects.requireNonNull(sessionManager);

        getStyleClass().add("navigation-view");

        setFillHeight(true);
        setAlignment(Pos.CENTER);

        Button refreshButton = new Button();
        refreshButton.setFocusTraversable(false);
        refreshButton.setGraphic(new FontIcon(MaterialDesign.MDI_REFRESH));
        refreshButton.setTooltip(new Tooltip("Refresh data"));
        refreshButton.getStyleClass().add("refresh-button");
        refreshButton.setVisible(!WebAPI.isBrowser());
        refreshButton.setManaged(!WebAPI.isBrowser());
        refreshButton.setOnAction(evt -> sessionManager.gotoURL("/refresh"));

        Button back = new Button();
        back.setFocusTraversable(false);
        back.setGraphic(new FontIcon(Material.ARROW_BACK));
        back.setOnAction(evt -> sessionManager.goBack());
        back.setMaxWidth(Double.MAX_VALUE);

        Button forward = new Button();
        forward.setFocusTraversable(false);
        forward.setGraphic(new FontIcon(Material.ARROW_FORWARD));
        forward.setOnAction(evt -> sessionManager.goForward());
        forward.setMaxWidth(Double.MAX_VALUE);

        sceneProperty().addListener(it -> {
            if (getScene() != null) {
                back.disableProperty().bind(Bindings.isEmpty(sessionManager.getHistoryBackward()));
                forward.disableProperty().bind(Bindings.isEmpty(sessionManager.getHistoryForwards()));
            }
        });

        back.setOnContextMenuRequested(evt -> showMenu(back, sessionManager.getHistoryBackward()));
        forward.setOnContextMenuRequested(evt -> showMenu(forward, sessionManager.getHistoryForwards()));

        if (OSUtil.isAndroidOrIOS()) {
            getChildren().setAll(back, refreshButton, forward);
        } else {
            getChildren().setAll(refreshButton, back, forward);
        }
    }

    private void showMenu(Button back, ObservableList<HistoryEntry> historyBackward) {
        ContextMenu menu = new ContextMenu();
        for (int i = 0; i < Math.min(20, historyBackward.size()); i++) {
            HistoryEntry url = historyBackward.get(i);
            MenuItem item = new MenuItem(url.title());
            item.setOnAction(evt -> sessionManager.gotoURL(url.path()));
            menu.getItems().add(item);
        }
        menu.show(back, Side.BOTTOM, 0, 0);
    }
}
