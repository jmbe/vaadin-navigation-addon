package com.vaadin.addon.places.api;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vaadin.addon.navigation.api.LocationHash;

public class LocationHashTest {

    @Test
    public void place_part_should_be_normalized() {
        assertEquals("Link", new LocationHash("Link").getPlacePart().getName());
        assertEquals("Link", new LocationHash("Link/").getPlacePart().getName());
        assertEquals("Link", new LocationHash("Link/ ").getPlacePart().getName());
        assertEquals("Link", new LocationHash(" Link/ ").getPlacePart().getName());
    }

    @Test
    public void leading_hash_should_be_removed() {
        assertEquals("Link", new LocationHash("#Link").getPlacePart().getName());
    }

}
