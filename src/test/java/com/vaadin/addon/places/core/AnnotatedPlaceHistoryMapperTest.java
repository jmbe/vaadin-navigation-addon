package com.vaadin.addon.places.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.addon.navigation.api.LocationHash;
import com.vaadin.addon.navigation.core.AnnotatedPlaceHistoryMapper;
import com.vaadin.addon.navigation.place.StatelessPlace;

public class AnnotatedPlaceHistoryMapperTest {

    private AnnotatedPlaceHistoryMapper mapper;

    @Before
    public void setup() {
        this.mapper = new AnnotatedPlaceHistoryMapper();
    }

    @Test
    public void hasPlaceFor() {
        mapper.register(WelcomePlace.class);

        assertTrue(mapper.hasPlaceFor(new LocationHash("WelcomePlace/params")));
    }

    public static class WelcomePlace extends StatelessPlace {

    }

}
