package com.dlsc.jfxcentral2.iconfont;

import org.kordamp.ikonli.AbstractIkonHandler;
import org.kordamp.ikonli.Ikon;

import java.io.InputStream;
import java.net.URL;

public class JFXCentralIkonliHandler extends AbstractIkonHandler {

    @Override
    public boolean supports(String description) {
        return description != null && description.startsWith("jfxc-icon-");
    }

    @Override
    public Ikon resolve(String description) {
        return JFXCentralIcon.findByDescription(description);
    }

    @Override
    public URL getFontResource() {
        return JFXCentralIkonliHandler.class.getResource("jfxcentral2-icons.ttf");
    }

    @Override
    public InputStream getFontResourceAsStream() {
        return JFXCentralIkonliHandler.class.getResourceAsStream("jfxcentral2-icons.ttf");
    }

    @Override
    public String getFontFamily() {
        return "jfxcentral2-icons";
    }
}
