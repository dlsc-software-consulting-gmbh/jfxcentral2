import com.dlsc.jfxcentral2.demo.JFXCentralSamplerProject;

open module com.dlsc.jfxcentral.sampler {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.web;

    requires fr.brouillard.oss.cssfx;

    requires jfxcentral.data;

    requires org.controlsfx.controls;
    requires org.controlsfx.fxsampler;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.material2;
    requires com.dlsc.jfxcentral2.components;
    requires com.sandec.mdfx;

    provides fxsampler.FXSamplerProject with JFXCentralSamplerProject;

    uses fxsampler.FXSamplerProject;
    uses fxsampler.FXSamplerConfiguration;
}