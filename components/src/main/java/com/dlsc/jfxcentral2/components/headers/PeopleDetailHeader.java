package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.utils.IkonUtil;

public class PeopleDetailHeader extends CategoryHeader<Person> {

    public PeopleDetailHeader() {
        setTitle("People");
        setIkon(IkonUtil.getModelIkon(Person.class));
    }
}
