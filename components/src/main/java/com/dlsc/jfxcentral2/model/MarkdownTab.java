package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import javafx.scene.Node;
import org.kordamp.ikonli.javafx.FontIcon;

public class MarkdownTab {

    private String title;
    private String mdString;
    private Node graphic;

    public MarkdownTab(String title, String mdString) {
        this(title, mdString, new FontIcon(JFXCentralIcon.CHEVRON_TOP));
    }

    public MarkdownTab(String title, String mdString, Node graphic) {
        this.title = title;
        this.mdString = mdString;
        this.graphic = graphic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMdString() {
        return mdString;
    }

    public void setMdString(String mdString) {
        this.mdString = mdString;
    }

    public Node getGraphic() {
        return graphic;
    }

    public void setGraphic(Node graphic) {
        this.graphic = graphic;
    }
}
