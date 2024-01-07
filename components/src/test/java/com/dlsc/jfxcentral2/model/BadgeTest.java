package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the 'of' method of class 'Badge'
 * 'of' method returns a list of badges based on the ModelObject passed
 */
public class BadgeTest {

    /**
     * Testing the 'of' method with a 'Person' that has both badges
     */
    @Test
    public void testOfPersonWithBothBadges() {
        Person person = new Person();
        person.setChampion(true);
        person.setRockstar(true);

        List<Badge> badges = Badge.of(person);
        
        assertEquals(2, badges.size());
        assertEquals(new Badge("Champion", IkonUtil.champion), badges.get(0));
        assertEquals(new Badge("Rockstar", IkonUtil.rockstar), badges.get(1));
    }

    /**
     * Testing the 'of' method with a 'Person' that has no badges
     */
    @Test
    public void testOfPersonWithNoBadges() {
        Person person = new Person();
        List<Badge> badges = Badge.of(person);
        assertTrue(badges.isEmpty());
    }
}