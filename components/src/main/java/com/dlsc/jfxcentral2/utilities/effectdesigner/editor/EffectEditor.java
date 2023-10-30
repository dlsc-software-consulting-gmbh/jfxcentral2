package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.LOGGER;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public abstract class EffectEditor<T extends Effect> extends VBox {

    private final T effect;

    public EffectEditor(T effect, Runnable deleteAction) {
        this.effect = effect;
        getStyleClass().add("effect-editor");

        Label titleLabel = new Label(effect.getClass().getSimpleName());
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setGraphic(new FontIcon(FontAwesome.SLIDERS));
        getChildren().add(titleLabel);

        Button deleteButton = new Button();
        deleteButton.getStyleClass().addAll("fill-button", "delete-button");
        deleteButton.setGraphic(new FontIcon(IkonUtil.delete));
        deleteButton.setFocusTraversable(false);
        deleteButton.setOnAction(event -> {
            if (deleteAction != null) {
                deleteAction.run();
            }
        });

        HBox topBox = new HBox(titleLabel, new Spacer(), deleteButton);
        topBox.getStyleClass().add("top-box");
        getChildren().add(topBox);

        Label tipsLabel = new Label(StringUtil.LOADING_TIPS);
        tipsLabel.getStyleClass().add("tips-label");
        tipsLabel.setGraphic(new FontIcon(AntDesignIconsOutlined.CLOUD_DOWNLOAD));
        getChildren().add(tipsLabel);

        Service<GridPane> propertiesEditorService = createPropertiesEditorService();
        propertiesEditorService.setOnSucceeded(event -> {
            GridPane contentPane = propertiesEditorService.getValue();
            getChildren().remove(tipsLabel);
            getChildren().add(contentPane);
        });

        propertiesEditorService.setOnFailed(event -> {
            Throwable ex = event.getSource().getException();
            LOGGER.error("Properties editor service failed", ex);
        });

        propertiesEditorService.start();
    }

    public T getData() {
        return effect;
    }

    private Service<GridPane> createPropertiesEditorService() {
        return new Service<>() {
            @Override
            protected Task<GridPane> createTask() {
                return new Task<>() {
                    @Override
                    protected GridPane call() throws Exception {
                        GridPane contentPane = new GridPane();
                        contentPane.getStyleClass().add("content-pane");
                        AtomicBoolean containBlurType = new AtomicBoolean(false);
                        List<EffectPropperty> options = createOptions(effect);
                        IntStream.range(0, options.size())
                                .forEachOrdered(i -> {
                                    EffectPropperty option = options.get(i);
                                    String title = option.getTitle();
                                    if (StringUtils.equalsAnyIgnoreCase(title, "BlurType", "Blur Type")) {
                                        containBlurType.set(true);
                                    }
                                    Label propertyNameLabel = new Label(title);
                                    propertyNameLabel.getStyleClass().add("property-name-label");
                                    if (option.getIkon() != null) {
                                        propertyNameLabel.setGraphic(new FontIcon(option.getIkon()));
                                    }
                                    contentPane.add(propertyNameLabel, 0, i);
                                    contentPane.add(option.getNode(), 1, i);
                                    RowConstraints rowConstraints = new RowConstraints();
                                    rowConstraints.setValignment(VPos.TOP);
                                    contentPane.getRowConstraints().add(rowConstraints);
                                });
                        if (containBlurType.get()) {
                            ColumnConstraints col = new ColumnConstraints();
                            col.setPrefWidth(95);
                            contentPane.getColumnConstraints().add(col);
                        }
                        return contentPane;
                    }
                };
            }
        };
    }

    protected abstract List<EffectPropperty> createOptions(T effect);

}
