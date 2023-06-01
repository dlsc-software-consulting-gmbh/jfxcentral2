package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import org.apache.commons.lang3.StringUtils;

public class LibraryDetailHeader extends SimpleDetailHeader<Library>  {

    public LibraryDetailHeader(Library library) {
        super(library);
        getStyleClass().add("library-detail-header");
        setWebsite(StringUtils.isNotBlank(getModel().getHomepage()) ? getModel().getHomepage() : getModel().getRepository());
        imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));
    }
}
