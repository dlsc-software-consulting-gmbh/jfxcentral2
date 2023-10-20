package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import one.jpro.platform.routing.LinkUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SponsorsViewSkin extends ControlBaseSkin<SponsorsView> {
    private final GridPane gridPane;
    private final SponsorsView control;
    private final Text title;
    private final List<SponsorsView.Sponsor> showedSponsor = new ArrayList<>();

    public SponsorsViewSkin(SponsorsView control) {
        super(control);
        this.control = control;

        gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        getChildren().add(gridPane);

        title = new Text(control.getTitle());
        title.textProperty().bind(control.titleProperty());
        title.getStyleClass().add("title");

        // we have to initialize the showed sponsors list with all items at first or the
        // algorithm for picking the sponsors will not work properly the first time it runs
        //showedSponsor.addAll(control.getItems());
        initLogoNodes();

        control.itemsProperty().addListener((observable, oldValue, newValue) -> initLogoNodes());
        control.sizeProperty().addListener((observable, oldValue, newValue) -> initLogoNodes());
    }

    private void initLogoNodes() {
        ObservableList<SponsorsView.Sponsor> items = control.getItems();
        List<Node> nodes = new ArrayList<>();
        int logoCount = switch (control.getSize()) {
            case SMALL -> 2;
            case MEDIUM -> 3;
            case LARGE -> 4;
        };
        int itemSize = items.size();
        int realLogoCount = Math.min(itemSize, logoCount);

        List<SponsorsView.Sponsor> temp = new ArrayList<>(items);
        temp.removeAll(showedSponsor);
        showedSponsor.clear();

        Collections.shuffle(temp);
        showedSponsor.addAll(temp.subList(0, realLogoCount));

        //add divider and logo
        for (int i = 0; i < showedSponsor.size(); i++) {
            if (i != 0 || isLarge()) {
                Region divider = new Region();
                divider.getStyleClass().addAll("divider", "divider-" + i);
                divider.managedProperty().bind(divider.visibleProperty());
                divider.visibleProperty().bind(control.dividerVisibleProperty());
                nodes.add(divider);
            }

            SponsorsView.Sponsor sponsor = showedSponsor.get(i);
            ImageView logo = new ImageView(sponsor.logoUrl());
            logo.setPickOnBounds(true);
            logo.setPreserveRatio(true);
            logo.fitHeightProperty().bind(control.logoFitHeightProperty());
            logo.fitWidthProperty().bind(control.logoFitWidthProperty());
            logo.getStyleClass().addAll("logo", "logo-" + sponsor.name().toLowerCase(), "logo-" + i);
            ExternalLinkUtil.setExternalLink(logo, sponsor.url());
            nodes.add(logo);
        }
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        gridPane.getRowConstraints().add(getRowConstraints());
        if (showedSponsor.isEmpty()) {
            gridPane.getColumnConstraints().add(getColumnConstraints());
            gridPane.add(title, 0, 0, 1, 1);
            return;
        }
        switch (control.getSize()) {
            case LARGE -> {
                gridPane.add(title, 0, 0, 1, 1);
                int size = nodes.size();
                gridPane.getColumnConstraints().add(getColumnConstraints());
                for (int i = 0; i < size; i++) {
                    gridPane.getColumnConstraints().add(getColumnConstraints());
                    gridPane.add(nodes.get(i), i + 1, 0);
                }
            }
            case MEDIUM, SMALL -> {
                gridPane.getRowConstraints().add(getRowConstraints());
                int size = nodes.size();
                gridPane.add(title, 0, 0, size, 1);
                for (int i = 0; i < size; i++) {
                    gridPane.getColumnConstraints().add(getColumnConstraints());
                    gridPane.add(nodes.get(i), i, 1);
                }
            }
        }
    }

    private RowConstraints getRowConstraints() {
        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);
        return row1;
    }

    private ColumnConstraints getColumnConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setHgrow(Priority.ALWAYS);
        return columnConstraints;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return gridPane.prefHeight(Region.USE_PREF_SIZE);
    }

}
