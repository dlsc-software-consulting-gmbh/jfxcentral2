package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import org.kordamp.ikonli.Ikon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Badge: Champion ; Rockstar etc.
 */
public record Badge(String name, Ikon icon) {

    public static List<Badge> of(ModelObject mo) {
        List<Badge> badges = null;
        if (mo instanceof Person person) {
            badges = new ArrayList<>();
            if (person.isChampion()) {
                badges.add(new Badge("Champion", IkonUtil.champion));
            }
            if (person.isRockstar()) {
                badges.add(new Badge("Rockstar", IkonUtil.rockstar));
            }
        }
        if (badges != null) {
            return badges;
        }
        return Collections.emptyList();
    }
}
