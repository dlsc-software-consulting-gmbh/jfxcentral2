/**
 * Copyright (C) 2014 - 2021 DLSC Software & Consulting GmbH (dlsc.com)
 *
 * This file is part of FlexGanttFX.
 */
package com.dlsc.jfxcentral2.demo;

import fxsampler.SampleBase;

public abstract class JFXCentralSampleBase extends SampleBase {

    protected JFXCentralSampleBase() {
    }

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
