package com.vaadin.addon.navigation.place;

import com.vaadin.addon.navigation.api.UriState;

public class StatelessPlace implements Place {

    @Override
    public UriState serializeState() {
        return new UriState();
    }

    @Override
    public void bind(final UriState state) {
    }

}
