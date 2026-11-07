package com.dlsc.jfxcentral2.cn1;

import com.codename1.fxcompat.runtime.FxLifecycle;
import com.codename1.mcp.MCP;
import com.codename1.ui.Display;
import com.dlsc.jfxcentral2.app.JFXCentral2MobileApp;
import javafx.application.Application;

/// Starts the JFXCentral mobile app -- the same JavaFX `Application` the
/// mobile module defines -- on Codename One's JavaFX compatibility layer.
public class JFXCentralMobile extends FxLifecycle {

    @Override
    protected Application createApplication() {
        return new JFXCentral2MobileApp();
    }

    @Override
    public void runApp() {
        // The JavaFX layer takes a CSS pixel to be 1/96 inch, as desktop JavaFX
        // does. This app was written for a mobile runtime where a pixel is the
        // platform's own unit -- an Android dp, an iOS point, about 1/160 inch
        // -- so at 1/96 everything came out 1.67 times too large and a phone
        // was 235 pixels wide. Set before the first scene is laid out.
        // In a browser a CSS pixel is already a logical pixel, so the web
        // build keeps the layer's own scale.
        if (!Display.getInstance().isDesktop() && !"HTML5".equals(Display.getInstance().getPlatformName())) {
            double pixelsPerInch = Display.getInstance().convertToPixels(254, true) / 10.0;
            Display.getInstance().setProperty("desktopcompat.scale", String.valueOf(pixelsPerInch / 160.0));
        }
        // Lets an agent or a test drive the app over a loopback MCP socket, in
        // a debuggable build only: a release build never opens the port.
        if (Display.getInstance().isDebuggableBuild()) {
            MCP.startSocketServer(8799);
        }
        super.runApp();
    }
}
