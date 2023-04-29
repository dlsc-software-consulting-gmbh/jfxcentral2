package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.TipsAndTricks;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TipsAndTricksTileView extends TileView<TipsAndTricks> {

    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public TipsAndTricksTileView(TipsAndTricks tipsAndTricks) {
        this();
        setData(tipsAndTricks);
    }

    public TipsAndTricksTileView() {
        getStyleClass().add("tips-tile-view");

        setButton1Text("READ NOW");
        setButton1Graphic(new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        setButton1Action(() -> System.out.println("Read: " + (getData() != null ? getData().getUrl() : "..")));

        dataProperty().addListener((ob, ov, nv) -> {
            ZonedDateTime date = nv.getDate();
            setRemark(date != null ? date.format(DEFAULT_DATE_FORMAT) : null);
        });

    }
}
