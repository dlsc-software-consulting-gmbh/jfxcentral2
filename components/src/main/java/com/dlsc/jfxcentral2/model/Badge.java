package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;

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
                badges.add(new Badge("Champion", Material2OutlinedMZ.VERIFIED));
            }
            if (person.isRockstar()) {
                badges.add(new Badge("Rockstar", MaterialDesignS.STAR_FOUR_POINTS_OUTLINE));
            }
        }
        if (badges != null) {
            return badges;
        }
        return Collections.emptyList();
    }
}
