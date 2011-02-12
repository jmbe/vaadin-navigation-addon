package com.vaadin.addon.navigation.api;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Key and value for a parameter.
 */
public class HashParameterPair {

    private String key;
    private String value;

    public HashParameterPair(final String key, final String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key), "Key cannot be empty");

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean hasValue() {
        return !Strings.isNullOrEmpty(this.value);
    }

    @Override
    public String toString() {
        return this.key + "=" + this.value;
    }

}
