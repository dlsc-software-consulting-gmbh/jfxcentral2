/**
 * Copyright (C) 2014 - 2021 DLSC Software & Consulting GmbH (dlsc.com)
 *
 * This file is part of FlexGanttFX.
 */
package com.dlsc.jfxcentral2.demo;


import fxsampler.FXSamplerProject;
import fxsampler.model.WelcomePage;

public class JFXCentralSamplerProject implements FXSamplerProject {

	@Override
	public String getProjectName() {
		return "FlexGanttFX";
	}

	@Override
	public String getSampleBasePackage() {
		return "com.flexganttfx.demo";
	}

	@Override
	public WelcomePage getWelcomePage() {
		return null;
	}
}
