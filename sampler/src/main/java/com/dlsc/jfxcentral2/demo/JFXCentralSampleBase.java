/**
 * Copyright (C) 2014 - 2021 DLSC Software & Consulting GmbH (dlsc.com)
 *
 * This file is part of FlexGanttFX.
 */
package com.dlsc.jfxcentral2.demo;

import com.dlsc.jfxcentral2.utils.NodeUtil;
import fr.brouillard.oss.cssfx.CSSFX;
import fxsampler.SampleBase;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public abstract class JFXCentralSampleBase extends SampleBase {

    private boolean stylesheetsAdded;

    protected JFXCentralSampleBase() {
    }

    @Override
    public final Node getPanel(Stage stage) {
//        CSSFXLogger.setLoggerFactory((loggerName) -> (level, message, args) -> {
//            String logMessage = "CSS: " + String.format(message, args);
//            switch (level) {
//                case NONE -> LoggingDomain.CSS.trace(logMessage);
//                case INFO -> LoggingDomain.CSS.info(logMessage);
//                case ERROR -> LoggingDomain.CSS.error(logMessage);
//                case WARN -> LoggingDomain.CSS.warn(logMessage);
//                case DEBUG -> LoggingDomain.CSS.debug(logMessage);
//            }
//        });

        CSSFX.start();

        Region panel = createControl();

        panel.sceneProperty().addListener(it -> {
            Scene scene = panel.getScene();

            if (scene == null || stylesheetsAdded) {
                return;
            }

            scene.getStylesheets().add(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css").toExternalForm());
            scene.getStylesheets().add(JFXCentralSampleBase.class.getResource("/com/dlsc/jfxcentral2/demo/components/test.css").toExternalForm());

            stylesheetsAdded = true;
        });

        return panel;
    }

    protected abstract Region createControl();

    @Override
    public String getSampleSourceURL() {
        return getSampleSourceBase() + getClass().getSimpleName() + ".txt";
    }

    private String getSampleSourceBase() {
        return "";
    }

    @Override
    public final String getJavaDocURL() {
        return "";
    }

    @Override
    public final String getProjectName() {
        return "JFXCentral2";
    }

    @Override
    public final String getProjectVersion() {
        return "1.0.0";
    }

    @Override
    public final String getControlStylesheetURL() {
        return null;
    }

    @Override
    public double getControlPanelDividerPosition() {
        return .8;
    }
}
