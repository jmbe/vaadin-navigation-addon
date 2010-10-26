package com.vaadin.addon.places.api;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vaadin.addon.navigation.api.PlaceIdentifier;

public class PlaceIdentifierTest {

    @Test
    public void toString_should_return_name() {
        assertEquals("place", new PlaceIdentifier("place").toString());
    }

}
