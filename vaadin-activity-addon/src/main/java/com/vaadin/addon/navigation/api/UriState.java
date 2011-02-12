package com.vaadin.addon.navigation.api;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Represents state as a string in a URI. Includes the state, but not the name of the place.
 */
public class UriState {

    /**
     * Separates the key and value in a parameter pair.
     */
    private static final String DEFAULT_PARAMETER_VALUE_DELIMITER = "=";

    private String state;

    public UriState() {
        this("");
    }

    public UriState(final String state) {
        this.state = Strings.nullToEmpty(state);
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return getState();
    }

    public List<HashParameterPair> getParameters() {

        if (isEmpty()) {
            return Collections.emptyList();
        }

        LinkedList<HashParameterPair> parameters = Lists.newLinkedList();

        Iterable<String> pairs = Splitter.on(LocationHash.DEFAULT_PARAMETER_LIST_DELIMITER).omitEmptyStrings()
                .split(state);
        for (String parameter : pairs) {
            String[] tokens = parameter.split(DEFAULT_PARAMETER_VALUE_DELIMITER);
            HashParameterPair pair = new HashParameterPair(tokens[0], tokens[1]);
            parameters.add(pair);
        }

        return parameters;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(this.state);
    }

    public UriState put(final String key, final String value) {

        if (Strings.isNullOrEmpty(value)) {
            return this;
        }

        state = state + LocationHash.DEFAULT_PARAMETER_LIST_DELIMITER + key + DEFAULT_PARAMETER_VALUE_DELIMITER + value;

        return this;
    }
}
