package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconInfoBuilder {

    private final Ikon ikon;
    private String ikonliPackName;
    private String ikonliPackId;

    public IconInfoBuilder(Ikon ikon) {
        this.ikon = ikon;
    }

    public IconInfoBuilder(Ikon ikon, String ikonliPackName, String ikonliPackId) {
        this.ikon = ikon;
        this.ikonliPackName = ikonliPackName;
        this.ikonliPackId = ikonliPackId;
    }

    public IconInfo build() {
        IconInfo iconInfo = new IconInfo();
        iconInfo.setIkon(ikon);

        // if the pack name is not provided, try to get it from the ikon
        if (ikonliPackName == null) {
            IkonliPack ikonliPack = IkonliPackUtil.getInstance().getIkonData(ikon).getIkonliPack();
            if (ikonliPack != null) {
                ikonliPackName = ikonliPack.getName();
            }
        }
        iconInfo.setIkonliPackName(ikonliPackName);

        // if the pack id is not provided, try to get it from the pack name
        if (ikonliPackId == null && ikonliPackName != null) {
            ikonliPackId = ikonliPackName.toLowerCase();
        }
        iconInfo.setIkonliPackId(ikonliPackId);

        // set the icon description
        String description = ikon.getDescription();
        iconInfo.setDescription(description);
        iconInfo.setIconLiteral(description);
        iconInfo.setCssCode("-fx-icon-code: \"" + description + "\";");
        iconInfo.setJavaCode(ikon.getClass().getSimpleName() + "." + new FontIcon(ikon).getIconCode());
        iconInfo.setUnicode("\\u" + Integer.toHexString(ikon.getCode()));
        iconInfo.setMavenInfo(IkonliPackUtil.getInstance().getMavenDependency(ikon));
        iconInfo.setGradleInfo(IkonliPackUtil.getInstance().getGradleDependency(ikon));
        return iconInfo;
    }

}
