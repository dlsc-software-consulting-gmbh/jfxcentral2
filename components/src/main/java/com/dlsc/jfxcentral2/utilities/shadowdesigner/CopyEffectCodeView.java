package com.dlsc.jfxcentral2.utilities.shadowdesigner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import one.jpro.platform.routing.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.boxicons.BoxiconsLogos;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.javafx.FontIcon;

public class CopyEffectCodeView extends HBox {

    public CopyEffectCodeView(Runnable onCopyCompleted) {
        getStyleClass().add("copy-effect-code-view");

        FontIcon javaIcon = new FontIcon(CarbonIcons.CODE);
        Button copyJavaCode = new Button("Java Code", javaIcon);
        copyJavaCode.getStyleClass().addAll("fill-button","java-button");
        copyJavaCode.setOnAction(event -> onCopyCompleted.run());
        copyJavaCode.disableProperty().bind(javaCode.isEmpty());
        javaCodeProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isEmpty(newValue)) {
                CopyUtil.setCopyOnClick(copyJavaCode, newValue);
            }
        });

        FontIcon cssIcon = new FontIcon(BoxiconsLogos.CSS3);
        Button copyCssCode = new Button("CSS Code", cssIcon);
        copyCssCode.getStyleClass().addAll("fill-button","css-button");
        copyCssCode.setOnAction(event -> onCopyCompleted.run());
        copyCssCode.disableProperty().bind(cssCode.isEmpty());
        cssCodeProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isEmpty(newValue)) {
                CopyUtil.setCopyOnClick(copyCssCode, newValue);
            }
        });

        getChildren().setAll(copyJavaCode, copyCssCode);
    }


    private final StringProperty javaCode = new SimpleStringProperty(this, "javaCode");

    public String getJavaCode() {
        return javaCode.get();
    }

    public StringProperty javaCodeProperty() {
        return javaCode;
    }

    public void setJavaCode(String javaCode) {
        this.javaCode.set(javaCode);
    }

    private final StringProperty cssCode = new SimpleStringProperty(this, "cssCode");

    public String getCssCode() {
        return cssCode.get();
    }

    public StringProperty cssCodeProperty() {
        return cssCode;
    }

    public void setCssCode(String cssCode) {
        this.cssCode.set(cssCode);
    }
}
