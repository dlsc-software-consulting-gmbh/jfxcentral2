package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;

public class LearnDetailHeader extends SimpleDetailHeader<Learn>  {

    public LearnDetailHeader(Learn learn) {
        super(learn);
        getStyleClass().addAll("learn-detail-header");

        String link = PageUtil.getViewOfObject(learn).toString();
        setShareUrl(link + "/" + learn.getId());
        setShareText("Found this article on @JFXCentral: " + learn.getName());
        setShareTitle("Learn: " + learn.getName());
        setBackText("Learn");
        setBackUrl("/" + link);

        String sourceCode = learn.getSourceCodeUrl();
        if (!StringUtils.isBlank(sourceCode)) {
            setWebsite(sourceCode);
            setWebsiteButtonText("Source Code");
            setWebsiteButtonIcon(IkonUtil.code);
        }
    }

}
