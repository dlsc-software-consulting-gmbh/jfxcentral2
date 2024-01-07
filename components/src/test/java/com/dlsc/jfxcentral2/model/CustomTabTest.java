package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomTabTest {
    /**
     * Test for the CustomTab class's methods.
     * It includes tests for constructor, setters and getters.
     */

    @Test
    public void testDefaultConstructor() {
        CustomTab customTab = new CustomTab();
        assertEquals(JFXCentralIcon.CHEVRON_TOP, customTab.getIcon());
    }

    @Test
    public void testConstructor() {
        Node node = new Group(new Text("test content"));
        CustomTab customTab = new CustomTab("Title", node);
        assertEquals("Title", customTab.getTitle());
        assertEquals(node, customTab.getContent());
        assertEquals(JFXCentralIcon.CHEVRON_TOP, customTab.getIcon());
    }

    @Test
    public void testConstructorWithIcon() {
        Node node = new Group(new Text("test content"));
        CustomTab customTab = new CustomTab("Title", node, JFXCentralIcon.CHEVRON_TOP);
        assertEquals("Title", customTab.getTitle());
        assertEquals(node, customTab.getContent());
        assertEquals(JFXCentralIcon.CHEVRON_TOP, customTab.getIcon());
    }

    @Test
    public void testGettersAndSetters() {
        Node initialNode = new Group(new Text("Initial Content"));
        Node updatedNode = new Group(new Text("Updated Content"));

        CustomTab customTab = new CustomTab("Initial Title", initialNode);
        assertEquals("Initial Title", customTab.getTitle());
        assertEquals(initialNode, customTab.getContent());

        customTab.setTitle("Updated Title");
        customTab.setContent(updatedNode);

        assertEquals("Updated Title", customTab.getTitle());
        assertEquals(updatedNode, customTab.getContent());
    }
}