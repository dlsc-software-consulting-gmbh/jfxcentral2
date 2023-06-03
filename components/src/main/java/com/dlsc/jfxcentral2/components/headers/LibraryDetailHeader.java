package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import org.apache.commons.lang3.StringUtils;

public class LibraryDetailHeader extends SimpleDetailHeader<Library>  {

    public LibraryDetailHeader(Library library) {
        super(library);
        getStyleClass().add("library-detail-header");

        imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));

        setWebsite(StringUtils.isNotBlank(getModel().getHomepage()) ? getModel().getHomepage() : getModel().getRepository());
        setWebsiteButtonText(StringUtils.isNotBlank(getModel().getHomepage()) ? "WEBSITE" : "REPOSITORY");
        setShareUrl("libraries/" + library.getId());
        setShareText("Found this library on @JFXCentral: " + library.getName());
        setMailSubject("Library: " + library.getName());
        setBackText("ALL LIBRARIES");
        setBackUrl("/libraries");
    }
}
