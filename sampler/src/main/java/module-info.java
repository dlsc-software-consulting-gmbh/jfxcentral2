import com.dlsc.jfxcentral2.demo.JFXCentralSamplerProject;

open module com.dlsc.jfxcentral.sampler {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.web;

    requires fr.brouillard.oss.cssfx;

    requires jfxcentral.data;

    requires org.controlsfx.controls;
    requires org.controlsfx.fxsampler;
    requires org.apache.commons.lang3;

    requires com.dlsc.jfxcentral2.components;
    requires com.sandec.mdfx;
    requires com.dlsc.jfxcentral2.iconfont;
    requires com.dlsc.jfxcentral2.devtools;

    provides fxsampler.FXSamplerProject with JFXCentralSamplerProject;

    uses fxsampler.FXSamplerProject;
    uses fxsampler.FXSamplerConfiguration;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    // ikonli icon packs START
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.ikonli.boxicons;
    requires org.kordamp.ikonli.bpmn;
    requires org.kordamp.ikonli.captainicon;
    requires org.kordamp.ikonli.carbonicons;
    requires org.kordamp.ikonli.codicons;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.dashicons;
    requires org.kordamp.ikonli.devicons;
    requires org.kordamp.ikonli.elusive;
    requires org.kordamp.ikonli.entypo;
    requires org.kordamp.ikonli.evaicons;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.fileicons;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.fontelico;
    requires org.kordamp.ikonli.foundation;
    requires org.kordamp.ikonli.hawcons;
    requires org.kordamp.ikonli.icomoon;
    requires org.kordamp.ikonli.ionicons;
    requires org.kordamp.ikonli.ionicons4;
    requires org.kordamp.ikonli.jam;
    requires org.kordamp.ikonli.ligaturesymbols;
    requires org.kordamp.ikonli.lineawesome;
    requires org.kordamp.ikonli.linecons;
    requires org.kordamp.ikonli.maki;
    requires org.kordamp.ikonli.maki2;
    requires org.kordamp.ikonli.mapicons;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.material2;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.medicons;
    requires org.kordamp.ikonli.metrizeicons;
    requires org.kordamp.ikonli.microns;
    requires org.kordamp.ikonli.ociicons;
    requires org.kordamp.ikonli.octicons;
    requires org.kordamp.ikonli.openiconic;
    requires org.kordamp.ikonli.paymentfont;
    requires org.kordamp.ikonli.prestashopicons;
    requires org.kordamp.ikonli.remixicon;
    requires org.kordamp.ikonli.runestroicons;
    requires org.kordamp.ikonli.simpleicons;
    requires org.kordamp.ikonli.simplelineicons;
    requires org.kordamp.ikonli.subway;
    requires org.kordamp.ikonli.swing;
    requires org.kordamp.ikonli.themify;
    requires org.kordamp.ikonli.typicons;
    requires org.kordamp.ikonli.unicons;
    requires org.kordamp.ikonli.weathericons;
    requires org.kordamp.ikonli.websymbols;
    requires org.kordamp.ikonli.whhg;
    requires org.kordamp.ikonli.win10;
    requires org.kordamp.ikonli.zondicons;
    requires one.jpro.platform.routing.core;
    // ikonli icon packs END

    uses org.kordamp.ikonli.IkonProvider;
}