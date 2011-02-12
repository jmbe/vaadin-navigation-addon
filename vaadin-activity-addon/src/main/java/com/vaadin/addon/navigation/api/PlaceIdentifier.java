package com.vaadin.addon.navigation.api;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents the place name part of a hash in an URI. Does not include state.
 */
public class PlaceIdentifier {

    private String name;

    public PlaceIdentifier(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PlaceIdentifier)) {
            return false;
        }
        PlaceIdentifier castOther = (PlaceIdentifier) other;
        return new EqualsBuilder().append(name, castOther.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

}
