package com.vaadin.addon.navigation.place;

import com.vaadin.addon.navigation.api.UriState;

/**
 * Represents UI state.
 */
public interface Place {

    public UriState serializeState();

    public void bind(final UriState state);

}
