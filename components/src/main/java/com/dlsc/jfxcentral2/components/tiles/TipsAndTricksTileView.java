package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TipsAndTricksTileView extends TileView<Tip> {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public TipsAndTricksTileView(Tip tipsAndTricks) {
        this();
        setData(tipsAndTricks);
    }

    public TipsAndTricksTileView() {
        getStyleClass().add("tips-tile-view");

        setButton1Text("READ NOW");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton1Action(() -> System.out.println("Read: " + (getData() != null ? getData().getName() : "..")));

        dataProperty().addListener((ob, ov, nv) -> {
            LocalDate date = nv.getCreatedOn();
            String formattedDate = date.format(DEFAULT_DATE_FORMATTER);
            setRemark(formattedDate);
        });

    }
}
