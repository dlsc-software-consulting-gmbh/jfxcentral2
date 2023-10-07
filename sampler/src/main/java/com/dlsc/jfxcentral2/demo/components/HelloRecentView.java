package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.RecentView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.stream.IntStream;

public class HelloRecentView extends JFXCentralSampleBase {
    private RecentView<Integer> recentView;

    @Override
    protected Region createControl() {
        recentView = new RecentView<>();
        recentView.getItems().setAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        recentView.getItems().addAll(12, 13, 14, 15, 16, 17, 18, 19, 20);
        return recentView;
    }

    @Override
    public Node getControlPanel() {
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            recentView.setMaxItems(10);
            recentView.getItems().setAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
            recentView.setItemFactory(item -> new Label( item.toString()));
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            recentView.getItems().remove(0);
        });

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            recentView.getItems().addAll(IntStream.range(0, 1).map(i -> (int) (Math.random() * 100)).boxed().toArray(Integer[]::new));
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            recentView.getItems().clear();
        });

        Button selectFirstButton = new Button("Select First");
        selectFirstButton.setOnAction(e -> {
            recentView.setSelectedItem(recentView.getItems().get(0));
        });

        Button selectLastButton = new Button("Select Last");
        selectLastButton.setOnAction(e -> {
            recentView.setSelectedItem(recentView.getItems().get(recentView.getItems().size() - 1));
        });

        Button clearSelected = new Button("Clear Selected");
        clearSelected.setOnAction(e -> {
            recentView.setSelectedIndex(-1);
        });

        Button changeMaxItems = new Button("Change Max Items");
        changeMaxItems.setOnAction(e -> {
            recentView.setMaxItems((int) (Math.random() * 10) + 5);
        });

        Button changeItemFactory = new Button("Change Item Factory");
        changeItemFactory.setOnAction(e -> {
            recentView.setItemFactory(item -> new Label("Index" + item.toString(), new FontIcon(IkonUtil.timer)));
        });

        VBox vBox = new VBox(5, resetButton, removeButton, addButton, clearButton, selectFirstButton, selectLastButton, clearSelected, changeMaxItems, changeItemFactory);
        vBox.getChildren().forEach(node -> ((Button) node).setPrefWidth(160));
        return vBox;
    }

    @Override
    public String getSampleName() {
        return "RecentView";
    }
}
