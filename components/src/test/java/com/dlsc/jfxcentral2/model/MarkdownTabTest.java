package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import javafx.scene.Node;
import org.junit.Test;
import org.kordamp.ikonli.javafx.FontIcon;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MarkdownTabTest {

    /**
     * Test the creation and manipulation of a MarkdownTab object.
     * The getTitle, getMdString, getGraphic, setTitle, setMdString and setGraphic methods are tested.
     */
    @Test
    public void markdownTabTest() {
        // Create a new MarkdownTab
        String title = "test_title";
        String mdString = "test_mdString";
        Node graphic = new FontIcon(JFXCentralIcon.CHEVRON_TOP);
        MarkdownTab markdownTab = new MarkdownTab(title, mdString, graphic);

        // Assert initial values are correct
        assertEquals(title, markdownTab.getTitle());
        assertEquals(mdString, markdownTab.getMdString());
        assertEquals(graphic, markdownTab.getGraphic());

        // Change the title
        String newTitle = "new_test_title";
        markdownTab.setTitle(newTitle);
        assertEquals(newTitle, markdownTab.getTitle());

        // Change the mdString
        String newMdString = "new_test_mdString";
        markdownTab.setMdString(newMdString);
        assertEquals(newMdString, markdownTab.getMdString());

        // Change the graphic
        Node newGraphic = new FontIcon(JFXCentralIcon.CHEVRON_TOP);
        markdownTab.setGraphic(newGraphic);
        assertEquals(newGraphic, markdownTab.getGraphic());
    }
}