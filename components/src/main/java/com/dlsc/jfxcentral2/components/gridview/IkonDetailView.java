package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.*;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonDetailView extends DetailView<Ikon> {

    private final HBox detailContent = new HBox();
    private final StackPane previewPane = new StackPane();

    private record IkonInfo(String iconLiteral, String cssCode, String javaCode, String unicode, String mavenInfo,
                            String gradleInfo) {
    }

    private final IkonInfo ikonInfo;

    public IkonDetailView(Ikon item) {
        super(item);
        getStyleClass().add("ikon-detail-view");

        if (item.getClass().getSimpleName().equals("PaymentFont")) {
            getStyleClass().add("payment-font-detail-view");
        }

        FontIcon fontIcon = new FontIcon(item);
        ikonInfo = new IkonInfo(item.getDescription(),
                "-fx-icon-code: \"" + item.getDescription() + "\";",
                item.getClass().getSimpleName() + "." + fontIcon.getIconCode(),
                "\\u" + Integer.toHexString(item.getCode()),
                IkonliPackUtil.getInstance().getMavenDependency(item),
                IkonliPackUtil.getInstance().getGradleDependency(item));

        previewPane.getChildren().setAll(fontIcon);
        previewPane.getStyleClass().add("ikon-preview-wrapper");
        HBox.setHgrow(previewPane, Priority.ALWAYS);

        Node infoNode = WebAPI.isBrowser() ? createInfoNodeForWeb(previewPane) : createInfoNodeForFX();
        StackPane.setAlignment(infoNode, Pos.CENTER);
        detailContent.getChildren().setAll(previewPane, infoNode);
        detailContent.getStyleClass().add("detail-content");
        getChildren().setAll(detailContent);
    }

    @Override
    protected void layoutBySize() {
        if (WebAPI.isBrowser()) {
            detailContent.getChildren().set(1, createInfoNodeForWeb(previewPane));
        }
    }

    private FlowPane createInfoNodeForFX() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().add("ikon-info-grid-pane");
        HBox.setHgrow(flowPane, Priority.ALWAYS);

        addRow(flowPane, "Icon Literal:", ikonInfo.iconLiteral());
        addRow(flowPane, "CSS Code:", ikonInfo.cssCode());
        addRow(flowPane, "Java Code:", ikonInfo.javaCode());
        addRow(flowPane, "Unicode:", ikonInfo.unicode());
        addRow(flowPane, "Maven:", ikonInfo.mavenInfo());
        addRow(flowPane, "Gradle :", ikonInfo.gradleInfo());
        return flowPane;
    }

    public Node createInfoNodeForWeb(Pane previewPane) {
        HTMLView view = new HTMLView();

        String str = FilesUtil.readText("/com/dlsc/jfxcentral2/htmlviews/IkonDetailView.html");
        str = str.replace("${width}", getSize() == Size.SMALL ? "width: 100%" : "width: calc(50% - 10px)")
                .replace("${iconLiteral}", ikonInfo.iconLiteral())
                .replace("${cssCode}", ikonInfo.cssCode().replace("\"", "&#34;"))
                .replace("${javaCode}", ikonInfo.javaCode())
                .replace("${unicode}", ikonInfo.unicode())
                .replace("${mavenInfo}", ikonInfo.mavenInfo())
                .replace("${gradleInfo}", ikonInfo.gradleInfo());

        view.setContent(str);
        view.maxWidthProperty().bind(view.prefWidthProperty());
        view.minWidthProperty().bind(view.prefWidthProperty());
        view.maxHeightProperty().bind(view.prefHeightProperty());
        view.minHeightProperty().bind(view.prefHeightProperty());
        view.prefHeightProperty().bind(sizeProperty().map(size -> isSmall() ? 230 : 115));
        view.parentProperty().addListener(it -> {
            Region parent = (Region) view.getParent();
            if (parent != null) {
                DoubleBinding widthBinding = Bindings.createDoubleBinding(() -> parent.getWidth() - parent.getInsets().getLeft() - parent.getInsets().getRight(), parent.widthProperty(), parent.insetsProperty());
                view.prefWidthProperty().bind(widthBinding.subtract(previewPane.widthProperty()).subtract(detailContent.spacingProperty()));
            }
        });

        return view;
    }

    private void addRow(FlowPane flowPane, String title, String contentText) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().addAll("title");
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());

        TextField textField = new TextField(contentText);
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setContextMenu(null);
        textField.managedProperty().bind(textField.visibleProperty());

        Button button = new Button();
        button.setGraphic(new FontIcon(IkonUtil.copy));
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.managedProperty().bind(button.visibleProperty());

        button.setOnAction(event -> {
            event.consume();
            textField.selectAll();
            textField.requestFocus();
            FXUtil.copyToClipboard(contentText);
        });
        HBox box = new HBox(titleLabel, textField, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }
}
