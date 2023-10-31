package com.dlsc.jfxcentral2.utilities.cssplayground;

import com.dlsc.jfxcentral2.components.FileHandlerView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;


public class CssPlaygroundView extends PaneBase {

    private final Label tipsLabel;
    private final VBox container ;

    public CssPlaygroundView() {
        getStyleClass().add("css-playground-view");

        Label downloadTips = new Label("Works best in App; Install Locally");
        downloadTips.getStyleClass().add("tips-label");

        FileHandlerView fileHandlerView = new FileHandlerView(true, true, true);
        fileHandlerView.getSupportedExtensions().add(".css");
        fileHandlerView.setText("Click or drop CSS files here");
        fileHandlerView.managedProperty().bind(fileHandlerView.visibleProperty());
        fileHandlerView.setVisible(false);

        tipsLabel = new Label(StringUtil.LOADING_TIPS);
        tipsLabel.getStyleClass().add("tips-label");
        tipsLabel.setGraphic(new FontIcon(AntDesignIconsOutlined.CLOUD_DOWNLOAD));

        container = new VBox();
        container.getChildren().addAll(fileHandlerView, tipsLabel);
        container.getStyleClass().add("container");

        getChildren().setAll(container);

        CssShowcaseService service = new CssShowcaseService();
        service.setOnSucceeded(event -> {
            CssShowcaseView cssShowcaseView = service.getValue();
            fileHandlerView.setOnUploadedFile(file -> cssShowcaseView.cssFileHandler(List.of(file)));
            fileHandlerView.setVisible(true);

            container.getChildren().remove(tipsLabel);
            container.getChildren().add(cssShowcaseView);
        });

        service.start();
    }

    private static class CssShowcaseService extends Service<CssShowcaseView> {
        @Override
        protected Task<CssShowcaseView> createTask() {
            return new Task<>() {
                @Override
                protected CssShowcaseView call() {
                    return new CssShowcaseView();
                }
            };
        }
    }
}

