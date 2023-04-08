import com.dlsc.jfxcentral2.demo.JFXCentralSamplerProject;

open module com.dlsc.jfxcentral.sampler {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.controlsfx.fxsampler;

    requires org.kordamp.ikonli.javafx;
    requires com.dlsc.jfxcentral2.components;

    provides fxsampler.FXSamplerProject with JFXCentralSamplerProject;

    uses fxsampler.FXSamplerProject;
    uses fxsampler.FXSamplerConfiguration;
}