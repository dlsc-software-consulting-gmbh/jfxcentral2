package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.utils.IkonUtil;

import java.util.Objects;

public class DetailHeader<T extends ModelObject> extends DetailHeaderBase {

    private T model;

    public DetailHeader(T model) {
        this.model = Objects.requireNonNull(model);
        setTitle(model.getName());
        setIkon(IkonUtil.getModelIkon(model));
    }

    public T getModel() {
        return model;
    }

}
