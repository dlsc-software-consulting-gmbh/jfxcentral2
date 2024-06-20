package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import org.apache.commons.lang3.StringUtils;

public class LibraryDetailHeader extends SimpleDetailHeader<Library>  {

    public LibraryDetailHeader(Library library) {
        super(library);
        getStyleClass().add("library-detail-header");

        imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));

        setWebsite(StringUtils.isNotBlank(getModel().getRepository()) ? getModel().getRepository() : getModel().getHomepage());
        setWebsiteButtonText(StringUtils.isNotBlank(getModel().getRepository()) ? "REPOSITORY" : "WEBSITE");
        setWebsiteButtonIcon(StringUtils.isNotBlank(getModel().getRepository()) ? IkonUtil.github : IkonUtil.website);
        setShareUrl("libraries/" + library.getId());
        setShareText("Found this library on @JFXCentral: " + library.getName());
        setShareTitle("Library: " + library.getName());
        setBackText("ALL LIBRARIES");
        setBackUrl(PagePath.LIBRARIES);
    }
}
