package com.dlsc.jfxcentral2.model;

import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.material.Material;

public class CustomTab {
    private String title;
    private Node content;
    private Ikon icon;

    public CustomTab() {
        this(null, null, Material.KEYBOARD_ARROW_UP);
    }

    public CustomTab(String title, Node content) {
        this(title, content, Material.KEYBOARD_ARROW_UP);
    }

    public CustomTab(String title, Node content, Ikon icon) {
        this.title = title;
        this.content = content;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Node getContent() {
        return content;
    }

    public void setContent(Node content) {
        this.content = content;
    }

    public Ikon getIcon() {
        return icon;
    }

    public void setIcon(Ikon icon) {
        this.icon = icon;
    }
}
