package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;

public class EffectPropperty {
    private String title;
    private Ikon ikon;
    private Node node;

    public EffectPropperty() {
    }

    public EffectPropperty(String title, Ikon ikon, Node node) {
        this.title = title;
        this.ikon = ikon;
        this.node = node;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Ikon getIkon() {
        return ikon;
    }

    public void setIkon(Ikon ikon) {
        this.ikon = ikon;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

}

