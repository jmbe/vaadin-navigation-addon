package com.vaadin.addon.places.place;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vaadin.addon.navigation.api.HashParameterPair;
import com.vaadin.addon.navigation.api.UriState;
import com.vaadin.addon.navigation.place.BeanPlace;

public class BeanPlaceTest {

    @Test
    public void should_set_properties_in_place() {
        SearchPlace place = new SearchPlace();
        UriState state = new UriState("email=test");
        place.bind(state);

        assertEquals("test", place.getEmail());
    }

    @Test
    public void should_serialize_to_state() {
        SearchPlace place = new SearchPlace();
        place.setEmail("test");

        UriState state = place.serializeState();
        assertEquals(1, state.getParameters().size());
        HashParameterPair pair = state.getParameters().get(0);
        assertEquals("email", pair.getKey());
        assertEquals("test", pair.getValue());
    }

    public static class SearchPlace extends BeanPlace {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }
    }

}
