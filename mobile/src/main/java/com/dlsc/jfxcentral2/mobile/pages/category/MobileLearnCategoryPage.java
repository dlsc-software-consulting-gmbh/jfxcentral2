package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import org.kordamp.ikonli.Ikon;

public abstract class MobileLearnCategoryPage extends MobileCategoryPageBase<Learn> {

    public MobileLearnCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Learn.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a lesson";
    }
}
