package com.vaadin.addon.places.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.vaadin.addon.navigation.api.HashParameterPair;
import com.vaadin.addon.navigation.api.UriState;

public class UriStateTest {

    @Test
    public void parse_hash_without_parameters_should_return_empty_parameter_collection() {
        List<HashParameterPair> parameters = new UriState("").getParameters();
        assertEquals(0, parameters.size());
    }

    @Test
    public void parse_hash_with_parameters() {
        List<HashParameterPair> parameters = new UriState("key=value&a=b").getParameters();
        assertEquals(2, parameters.size());

        HashParameterPair first = parameters.get(0);
        assertEquals("key", first.getKey());
        assertEquals("value", first.getValue());

        HashParameterPair second = parameters.get(1);
        assertEquals("a", second.getKey());
        assertEquals("b", second.getValue());
    }
}
